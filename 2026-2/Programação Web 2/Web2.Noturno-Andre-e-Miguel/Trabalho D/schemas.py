from pydantic import BaseModel, Field


class UserCreate(BaseModel):
    nickname: str = Field(min_length=2, max_length=100)
    email: str = Field(min_length=3, max_length=100)
    password: str = Field(min_length=4, max_length=100)


class UserResponse(BaseModel):
    id: int
    nickname: str
    email: str
    class Config:
        from_attributes = True


class AuthUserResponse(BaseModel):
    id: int
    nickname: str
    email: str
    class Config:
        from_attributes = True


class LoginRequest(BaseModel):
    identifier: str
    password: str


class RatingCreate(BaseModel):
    user_id: int
    music_id: str
    rating: float = Field(ge=0.5, le=5)
    description: str = Field(max_length=255)


class RatingUpdate(BaseModel):
    rating: float = Field(ge=0.5, le=5)
    description: str = Field(max_length=255)


class RatingResponse(RatingCreate):
    id: int
    class Config:
        from_attributes = True


class FeedRatingResponse(RatingResponse):
    nickname: str
