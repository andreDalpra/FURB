# Nota Musical

O projeto está dividido entre API Python e interface React. O navegador só chama a
API local; as chaves do Spotify permanecem no arquivo `.env` do back-end.

## Executar

Em um terminal, na raiz do projeto:

```powershell
uvicorn main:app --reload
```

Em outro terminal:

```powershell
cd frontend
npm install
npm run dev
```

Abra `http://localhost:5173`. Caso a API use outra porta, crie
`frontend/.env.local` com `VITE_API_URL=http://127.0.0.1:SUA_PORTA`.

## Estrutura

- `main.py`, `models.py`, `schemas.py`, `database.py` e `spotify.py`: API FastAPI.
- `frontend/`: aplicativo React/Vite, incluindo feed, busca, login/cadastro e reviews.
