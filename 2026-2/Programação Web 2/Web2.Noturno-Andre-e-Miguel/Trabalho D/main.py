import os
from fastapi import Depends, FastAPI, HTTPException, Query, status
from fastapi.middleware.cors import CORSMiddleware
from sqlalchemy.orm import Session
from dotenv import load_dotenv

import models
import schemas
from database import SessionLocal, engine
from spotify import SpotifyAPI

load_dotenv()
models.Base.metadata.create_all(bind=engine)

app = FastAPI(title="Music Reviews API")
sp = SpotifyAPI(
    client_id=os.getenv("SPOTIFY_CLIENT_ID"),
    client_secret=os.getenv("SPOTIFY_CLIENT_SECRET"),
)

app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://127.0.0.1:5500", "http://localhost:5500", "http://localhost:5173"],
    # Vite picks the next free port (5174, 5175...) when 5173 is occupied.
    # Permit only local development origins, never arbitrary external sites.
    allow_origin_regex=r"https?://(localhost|127\.0\.0\.1):\d+$",
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)


def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()


def get_rating_or_404(rating_id: int, db: Session) -> models.Rating:
    rating = db.query(models.Rating).filter(models.Rating.id == rating_id).first()
    if not rating:
        raise HTTPException(status_code=404, detail="Rating not found")
    return rating


@app.post("/users", response_model=schemas.UserResponse, status_code=status.HTTP_201_CREATED)
def create_user(user: schemas.UserCreate, db: Session = Depends(get_db)):
    existing = db.query(models.User).filter(
        (models.User.nickname == user.nickname) | (models.User.email == user.email)
    ).first()
    if existing:
        raise HTTPException(status_code=409, detail="Nickname or email already registered")
    new_user = models.User(**user.model_dump())
    db.add(new_user)
    db.commit()
    db.refresh(new_user)
    return new_user


@app.get("/users", response_model=list[schemas.UserResponse])
def list_users(db: Session = Depends(get_db)):
    return db.query(models.User).all()


@app.get("/users/{user_id}", response_model=schemas.UserResponse)
def get_user(user_id: int, db: Session = Depends(get_db)):
    user = db.query(models.User).filter(models.User.id == user_id).first()
    if not user:
        raise HTTPException(status_code=404, detail="User not found")
    return user


@app.put("/users/{user_id}", response_model=schemas.UserResponse)
def update_user(user_id: int, data: schemas.UserCreate, db: Session = Depends(get_db)):
    user = get_user(user_id, db)
    user.nickname, user.email, user.password = data.nickname, data.email, data.password
    db.commit()
    db.refresh(user)
    return user


@app.delete("/users/{user_id}")
def delete_user(user_id: int, db: Session = Depends(get_db)):
    user = get_user(user_id, db)
    db.delete(user)
    db.commit()
    return {"message": "User deleted successfully"}


@app.post("/auth/login", response_model=schemas.AuthUserResponse)
def login(credentials: schemas.LoginRequest, db: Session = Depends(get_db)):
    user = db.query(models.User).filter(
        (models.User.email == credentials.identifier) | (models.User.nickname == credentials.identifier)
    ).first()
    # The legacy database stores passwords as plain text. This preserves compatibility.
    if not user or user.password != credentials.password:
        raise HTTPException(status_code=401, detail="Invalid credentials")
    return user


@app.post("/ratings", response_model=schemas.RatingResponse, status_code=status.HTTP_201_CREATED)
def create_rating(rating: schemas.RatingCreate, db: Session = Depends(get_db)):
    if round(rating.rating * 2) != rating.rating * 2:
        raise HTTPException(status_code=422, detail="Rating must be in 0.5 increments")
    if not db.query(models.User).filter(models.User.id == rating.user_id).first():
        raise HTTPException(status_code=404, detail="User not found")
    new_rating = models.Rating(**rating.model_dump())
    db.add(new_rating)
    db.commit()
    db.refresh(new_rating)
    return new_rating


@app.get("/ratings", response_model=list[schemas.RatingResponse])
def list_ratings(db: Session = Depends(get_db)):
    return db.query(models.Rating).all()


@app.get("/feed", response_model=list[schemas.FeedRatingResponse])
def feed(db: Session = Depends(get_db)):
    rows = db.query(models.Rating, models.User.nickname).join(models.User).order_by(models.Rating.id.desc()).all()
    return [{**schemas.RatingResponse.model_validate(rating).model_dump(), "nickname": nickname} for rating, nickname in rows]


@app.get("/ratings/{rating_id}", response_model=schemas.RatingResponse)
def get_rating(rating_id: int, db: Session = Depends(get_db)):
    return get_rating_or_404(rating_id, db)


@app.get("/ratings/user/id/{user_id}", response_model=list[schemas.RatingResponse])
def get_ratings_by_user_id(user_id: int, db: Session = Depends(get_db)):
    return db.query(models.Rating).filter(models.Rating.user_id == user_id).all()


@app.get("/ratings/user/nickname/{nickname}", response_model=list[schemas.RatingResponse])
def get_ratings_by_username(nickname: str, db: Session = Depends(get_db)):
    user = db.query(models.User).filter(models.User.nickname == nickname).first()
    if not user:
        raise HTTPException(status_code=404, detail="User not found")
    return db.query(models.Rating).filter(models.Rating.user_id == user.id).all()


@app.get("/ratings/music/{music_id}", response_model=list[schemas.RatingResponse])
def get_ratings_by_music(music_id: str, db: Session = Depends(get_db)):
    return db.query(models.Rating).filter(models.Rating.music_id == music_id).all()


@app.put("/ratings/{rating_id}", response_model=schemas.RatingResponse)
def update_rating(rating_id: int, data: schemas.RatingUpdate, db: Session = Depends(get_db)):
    rating = get_rating_or_404(rating_id, db)
    if round(data.rating * 2) != data.rating * 2:
        raise HTTPException(status_code=422, detail="Rating must be in 0.5 increments")
    rating.rating, rating.description = data.rating, data.description
    db.commit()
    db.refresh(rating)
    return rating


@app.delete("/ratings/{rating_id}")
def delete_rating(rating_id: int, db: Session = Depends(get_db)):
    rating = get_rating_or_404(rating_id, db)
    db.delete(rating)
    db.commit()
    return {"message": "Rating deleted successfully"}


@app.get("/spotify/music/search", response_model=dict)
def search_spotify_music(query: str = Query(min_length=1), limit: int = Query(default=10, ge=1, le=20)):
    return sp.get_music(query, limit)


@app.get("/spotify/music/id/{music_id}", response_model=dict)
def get_spotify_music(music_id: str):
    return sp.get_music_by_id(music_id)


@app.get("/spotify/music/artists/{music_id}", response_model=str)
def get_artists_spotify(music_id: str):
    return sp.get_artists_by_music(sp.get_music_by_id(music_id))
