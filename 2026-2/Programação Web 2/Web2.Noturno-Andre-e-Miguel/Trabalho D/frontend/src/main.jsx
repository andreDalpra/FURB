import { StrictMode, useEffect, useState } from 'react'
import { createRoot } from 'react-dom/client'
import './styles.css'

const API = import.meta.env.VITE_API_URL || 'http://127.0.0.1:8000'
const request = async (path, options = {}) => {
  const response = await fetch(`${API}${path}`, { headers: { 'Content-Type': 'application/json' }, ...options })
  const data = await response.json().catch(() => null)
  if (!response.ok) throw new Error(data?.detail || 'Não foi possível concluir a operação.')
  return data
}
const artists = music => music.artists?.map(a => a.name).join(', ') || 'Artista desconhecido'
const cover = music => music.album?.images?.[0]?.url

function ScoreRing({ music, score }) {
  const percent = score / 5
  const color = `hsl(${Math.round(percent * 120)}, 76%, 46%)`
  return <div className="score-ring" style={{ '--progress': `${percent * 360}deg`, '--score-color': color }}>
    {cover(music) ? <img src={cover(music)} alt={`Capa de ${music.name}`} /> : <div className="cover-placeholder">♪</div>}
    <span>{score.toFixed(1)}</span>
  </div>
}

function Stars({ value, onChange, editable = false }) {
  return <div className={editable ? 'stars editable' : 'stars'} aria-label={`${value} de 5 estrelas`}>
    {[0.5, 1, 1.5, 2, 2.5, 3, 3.5, 4, 3.5, 4, 4.5, 5].filter((n, i, a) => a.indexOf(n) === i).map(n =>
      <button key={n} type="button" disabled={!editable} onClick={() => onChange?.(n)} className={n <= value ? 'on' : ''} title={`${n} estrelas`}>★</button>)}
  </div>
}

function Search({ onSelect }) {
  const [query, setQuery] = useState('')
  const [results, setResults] = useState([])
  const [error, setError] = useState('')
  useEffect(() => {
    if (query.trim().length < 2) return setResults([])
    const timer = setTimeout(async () => {
      try { setError(''); const data = await request(`/spotify/music/search?query=${encodeURIComponent(query)}&limit=6`); setResults(data.tracks?.items || []) }
      catch { setError('Não foi possível pesquisar músicas agora.') }
    }, 350)
    return () => clearTimeout(timer)
  }, [query])
  return <div className="search"><input value={query} onChange={e => setQuery(e.target.value)} placeholder="Busque uma música ou artista" aria-label="Buscar música" />
    {error && <p className="inline-error">{error}</p>}
    {results.length > 0 && <div className="results">{results.map(m => <button type="button" key={m.id} onClick={() => { setQuery(''); setResults([]); onSelect(m) }}><img src={cover(m)} alt=""/><span><b>{m.name}</b><small>{artists(m)}</small></span></button>)}</div>}
  </div>
}

function ReviewCard({ review, music, nickname, actions }) {
  return <article className="review-card"><ScoreRing music={music} score={review.rating}/><div className="review-content"><div className="review-title"><strong>{nickname || 'Usuário'}</strong><Stars value={review.rating}/></div><p>{review.description || 'Sem descrição.'}</p>{actions}</div></article>
}

function App() {
  const [user, setUser] = useState(() => JSON.parse(localStorage.getItem('musicReviewsUser') || 'null'))
  const [page, setPage] = useState('feed')
  const [selected, setSelected] = useState(null)
  const [feed, setFeed] = useState([])
  const [musicCache, setMusicCache] = useState({})
  const [notice, setNotice] = useState('')
  const [error, setError] = useState('')
  const [authMode, setAuthMode] = useState('login')

  const loadMusic = async id => {
    if (musicCache[id]) return musicCache[id]
    const music = await request(`/spotify/music/id/${id}`)
    setMusicCache(old => ({ ...old, [id]: music })); return music
  }
  const loadFeed = async () => { try { setError(''); const data = await request('/feed'); setFeed(data); await Promise.all(data.map(r => loadMusic(r.music_id))); } catch { setError('Não foi possível carregar o feed.') } }
  useEffect(() => { if (user) loadFeed() }, [user])
  const chooseMusic = music => { setSelected(music); setPage('music') }
  const logout = () => { localStorage.removeItem('musicReviewsUser'); setUser(null); setPage('feed') }

  if (!user) return <Auth mode={authMode} setMode={setAuthMode} onLogin={u => { localStorage.setItem('musicReviewsUser', JSON.stringify(u)); setUser(u) }} />
  const nav = <header><button className="brand" onClick={() => setPage('feed')}>nota<span>musical</span></button><Search onSelect={chooseMusic}/><nav><button className="button ghost" onClick={() => setPage('create')}>+ Review</button><button className="profile" onClick={() => setPage('profile')}>{user.nickname.slice(0, 1).toUpperCase()}<span>Minha conta</span></button><button className="logout" onClick={logout}>Sair</button></nav></header>
  return <><div className="app">{nav}{notice && <div className="notice">{notice}<button onClick={() => setNotice('')}>×</button></div>}{error && <div className="error">{error}</div>}
    {page === 'feed' && <Feed feed={feed} cache={musicCache} onSelect={chooseMusic}/>} 
    {page === 'music' && <MusicPage music={selected} user={user} cache={musicCache} onCreate={() => setPage('create')} onError={setError}/>} 
    {page === 'create' && <ReviewForm user={user} chosen={selected} onChoose={setSelected} onDone={music => { setSelected(music); setNotice('Review publicada!'); setPage('music') }} onError={setError}/>} 
    {page === 'profile' && <Profile user={user} cache={musicCache} loadMusic={loadMusic} onSelect={chooseMusic} onChanged={() => { loadFeed(); setNotice('Sua review foi atualizada.') }} onError={setError}/>} 
  </div></>
}

function Auth({ mode, setMode, onLogin }) {
  const [form, setForm] = useState({ nickname: '', email: '', password: '', identifier: '' }); const [error, setError] = useState(''); const [busy, setBusy] = useState(false)
  const submit = async e => { e.preventDefault(); setBusy(true); setError(''); try { if (mode === 'register') { await request('/users', { method: 'POST', body: JSON.stringify({ nickname: form.nickname, email: form.email, password: form.password }) }); setMode('login'); setForm(f => ({ ...f, identifier: f.email })); } else onLogin(await request('/auth/login', { method: 'POST', body: JSON.stringify({ identifier: form.identifier, password: form.password }) })) } catch (e) { setError(e.message) } finally { setBusy(false) } }
  return <main className="auth"><section><div className="logo">nota<span>musical</span></div><h1>{mode === 'login' ? 'Que bom te ver.' : 'Crie sua conta.'}</h1><p>Descubra e compartilhe opiniões sobre as músicas que você ama.</p><form onSubmit={submit}>{mode === 'register' && <><input required minLength="2" placeholder="Nome de usuário" value={form.nickname} onChange={e => setForm({...form, nickname:e.target.value})}/><input required type="email" placeholder="E-mail" value={form.email} onChange={e => setForm({...form, email:e.target.value})}/></>} {mode === 'login' && <input required placeholder="E-mail ou usuário" value={form.identifier} onChange={e => setForm({...form, identifier:e.target.value})}/>}<input required minLength="4" type="password" placeholder="Senha" value={form.password} onChange={e => setForm({...form, password:e.target.value})}/>{error && <p className="inline-error">{error}</p>}<button className="button primary" disabled={busy}>{busy ? 'Aguarde...' : mode === 'login' ? 'Entrar' : 'Cadastrar'}</button></form><button className="link" onClick={() => { setMode(mode === 'login' ? 'register' : 'login'); setError('') }}>{mode === 'login' ? 'Ainda não tem conta? Cadastre-se' : 'Já possui uma conta? Entre'}</button></section></main>
}

function Feed({ feed, cache, onSelect }) { return <main><div className="page-heading"><p className="eyebrow">COMUNIDADE</p><h1>Reviews recentes</h1><p>O que as pessoas estão ouvindo e sentindo.</p></div><div className="review-list">{feed.map(r => cache[r.music_id] && <ReviewCard key={r.id} review={r} music={cache[r.music_id]} nickname={r.nickname} actions={<button className="link" onClick={() => onSelect(cache[r.music_id])}>Ver música →</button>}/>) }{feed.length === 0 && <Empty text="Ainda não há reviews. Seja a primeira pessoa a publicar uma!"/>}</div></main> }

function MusicPage({ music, user, cache, onCreate, onError }) { const [reviews, setReviews] = useState([]); const [users, setUsers] = useState({}); useEffect(() => { if (!music) return; Promise.all([request(`/ratings/music/${music.id}`), request('/users')]).then(([r, u]) => { setReviews(r); setUsers(Object.fromEntries(u.map(x => [x.id, x.nickname]))) }).catch(() => onError('Não foi possível carregar as reviews desta música.')) }, [music?.id]); if (!music) return <Empty text="Escolha uma música pela busca para ver suas reviews."/>; return <main><section className="music-hero"><img src={cover(music)} alt={`Capa de ${music.name}`}/><div><p className="eyebrow">MÚSICA</p><h1>{music.name}</h1><p>{artists(music)} · {music.album?.name}</p><button className="button primary" onClick={onCreate}>Avaliar esta música</button></div></section><h2>Reviews</h2><div className="review-list">{reviews.map(r => <ReviewCard key={r.id} review={r} music={music} nickname={users[r.user_id] || (r.user_id === user.id ? user.nickname : 'Usuário')}/>) }{reviews.length === 0 && <Empty text="Nenhuma Review Feita Ainda"/>}</div></main> }

function ReviewForm({ user, chosen, onChoose, onDone, onError }) { const [music, setMusic] = useState(chosen); const [rating, setRating] = useState(3); const [description, setDescription] = useState(''); const [busy, setBusy] = useState(false); const submit = async e => { e.preventDefault(); if (!music) return onError('Escolha uma música antes de publicar.'); setBusy(true); try { await request('/ratings', { method:'POST', body: JSON.stringify({ user_id:user.id, music_id:music.id, rating, description }) }); onDone(music) } catch(e) { onError(e.message) } finally { setBusy(false) } }; return <main><div className="page-heading"><p className="eyebrow">NOVA REVIEW</p><h1>Compartilhe sua opinião</h1></div><div className="review-editor"><section><h2>1. Escolha uma música</h2><Search onSelect={m => {setMusic(m); onChoose(m)}}/>{music && <div className="chosen"><img src={cover(music)} alt=""/><span><b>{music.name}</b><small>{artists(music)} · {music.album?.name}</small></span></div>}</section><form onSubmit={submit}><h2>2. Sua review</h2><label>Sua nota</label><Stars value={rating} onChange={setRating} editable/><b className="score-value">{rating.toFixed(1)} / 5.0</b><label htmlFor="description">O que você achou?</label><textarea id="description" required maxLength="255" value={description} onChange={e => setDescription(e.target.value)} placeholder="Conte um pouco sobre essa música..."/><button className="button primary" disabled={busy}>{busy ? 'Publicando...' : 'Publicar review'}</button></form></div></main> }

function Profile({ user, cache, loadMusic, onSelect, onChanged, onError }) { const [reviews, setReviews] = useState([]); const [editing, setEditing] = useState(null); useEffect(() => { request(`/ratings/user/id/${user.id}`).then(async r => { setReviews(r); await Promise.all(r.map(x => loadMusic(x.music_id))) }).catch(() => onError('Não foi possível carregar suas reviews.')) }, []); const remove = async id => { if (!confirm('Excluir esta review?')) return; try { await request(`/ratings/${id}`, {method:'DELETE'}); setReviews(old => old.filter(r => r.id !== id)); onChanged() } catch(e) { onError(e.message) } }; return <main><div className="page-heading"><p className="eyebrow">SEU PERFIL</p><h1>{user.nickname}</h1><p>Suas opiniões musicais.</p></div><div className="review-list">{reviews.map(r => cache[r.music_id] && <ReviewCard key={r.id} review={r} music={cache[r.music_id]} nickname="Você" actions={<div className="review-actions"><button className="link" onClick={() => onSelect(cache[r.music_id])}>Ver música</button><button className="link" onClick={() => setEditing(r)}>Editar</button><button className="link danger" onClick={() => remove(r.id)}>Excluir</button></div>}/>) }{reviews.length === 0 && <Empty text="Você ainda não publicou nenhuma review."/>}</div>{editing && <EditModal review={editing} onClose={() => setEditing(null)} onSave={async data => { try { const updated = await request(`/ratings/${editing.id}`, {method:'PUT', body:JSON.stringify(data)}); setReviews(old => old.map(r => r.id === updated.id ? updated : r)); setEditing(null); onChanged() } catch(e) { onError(e.message) } }}/>}</main> }

function EditModal({ review, onClose, onSave }) { const [rating,setRating]=useState(review.rating); const [description,setDescription]=useState(review.description); return <div className="modal-backdrop"><form className="modal" onSubmit={e => {e.preventDefault(); onSave({rating, description})}}><button type="button" className="close" onClick={onClose}>×</button><h2>Editar review</h2><Stars value={rating} onChange={setRating} editable/><textarea required maxLength="255" value={description} onChange={e=>setDescription(e.target.value)}/><button className="button primary">Salvar alterações</button></form></div> }
function Empty({ text }) { return <div className="empty">{text}</div> }
createRoot(document.getElementById('root')).render(<StrictMode><App/></StrictMode>)
