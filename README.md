# Blog Platform

A full-stack blogging platform with JWT authentication, post management (drafts + publishing),
categories, tags, and a rich-text editor. Built as a learning projectcovering
Spring Boot + React + Docker end to end.

## What it can do

- **Auth** — register, login (JWT, 24h expiry), protected routes. Seeded dev user included.
- **Posts** — create / edit / delete with a TipTap rich-text editor, `DRAFT` or `PUBLISHED`
  status, automatic reading-time calculation, filter by category or tag.
- **Drafts** — private per-user draft list (`GET /posts/drafts`, authenticated).
- **Categories** — create, list (with published-post counts), delete (blocked if posts exist).
- **Tags** — create (bulk, max 10), list (with counts), delete (blocked if posts exist).
- **Public reading** — published posts, categories, and tags are readable without login.

## Tech stack

| Layer | Technologies |
|---|---|
| Backend | Java 17, Spring Boot, Spring Security (JWT via JJWT), Spring Data JPA / Hibernate, PostgreSQL, MapStruct, Lombok, Maven, Jakarta Validation |
| Frontend | React 18, TypeScript, Vite, Tailwind CSS, NextUI, TipTap editor, Axios, React Router, DOMPurify |
| Infra | Docker + Docker Compose, Nginx (static hosting + `/api` reverse proxy), Adminer (DB UI) |

## Quick start (recommended): full Docker stack

```bash
docker compose up --build
```

| Service | URL | Notes |
|---|---|---|
| App (frontend) | http://localhost:3000 | Register at `/register`, or login with `user@test.com` / `password` |
| Backend API | http://localhost:8080 | e.g. `POST /api/v1/auth/login` |
| Database | localhost:5433 | user `postgres`, password `a`, db `postgres` (host port is 5433 — macOS already uses 5432) |
| Adminer (DB UI) | http://localhost:8888 | server `db`, user `postgres`, password `a` |

```bash
docker compose down        # stop (data persists in the pgdata volume)
docker compose down -v     # stop AND wipe database data
```

## Local development (without Docker images)

Run only the database in Docker, backend + frontend natively:

```bash
docker compose up db adminer   # postgres :5433, adminer :8888
./mvnw spring-boot:run         # backend :8080 (uses localhost:5433, see application.yaml)
cd frontend && npm install && npm run dev   # frontend :5173, vite proxies /api -> :8080
```

> Run **either** the Docker frontend (`:3000`) **or** `npm run dev` (`:5173`), not both —
> they are two ways of serving the same app.

## API overview

Base path: `/api/v1`

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| POST | `/auth/register` | public | `{name, email, password}` → `{token, expiresIn}` |
| POST | `/auth/login` | public | `{email, password}` → `{token, expiresIn}` |
| GET | `/posts?categoryId=&tagId=` | public | List published posts, optional filters |
| GET | `/posts/{id}` | public | Single post |
| GET | `/posts/drafts` | JWT | Logged-in user's drafts |
| POST | `/posts` | JWT | Create post |
| PUT | `/posts/{id}` | JWT | Update post |
| DELETE | `/posts/{id}` | JWT | Delete post |
| GET/POST | `/categories` | GET public, POST JWT | List / create categories |
| DELETE | `/categories/{id}` | JWT | Delete (fails if it has posts) |
| GET/POST | `/tags` | GET public, POST JWT | List / create tags (bulk `{names: [...]}`) |
| DELETE | `/tags/{id}` | JWT | Delete (fails if it has posts) |

Authenticated requests need `Authorization: Bearer <token>`.

## Project structure

```
├── Dockerfile                  # backend: maven build -> slim jre run
├── docker-compose.yml          # db + backend + frontend + adminer
├── src/main/java/com/siddhesh/blog/
│   ├── config/                 # SecurityConfig, DataInitializer (dev user seed)
│   ├── controller/             # Auth, Post, Category, Tag, Error controllers
│   ├── domain/                 # entities, DTOs
│   ├── mappers/                # MapStruct mappers
│   ├── repositories/           # Spring Data JPA
│   ├── security/               # JWT filter, UserDetails
│   └── services/               # business logic
├── src/main/resources/application.yaml
└── frontend/
    ├── Dockerfile              # node build -> nginx serve + /api proxy
    ├── nginx.conf
    └── src/
        ├── pages/              # Home, Post, EditPost, Drafts, Categories, Tags, Login, Register
        ├── components/         # AuthContext, NavBar, PostForm, PostList
        └── services/apiService.ts  # typed axios client (baseURL /api/v1)
```

## Configuration

Backend reads `src/main/resources/application.yaml`. In Docker these are overridden
by environment variables (see `docker-compose.yml`):

| Env var | Replaces | Example |
|---|---|---|
| `SPRING_DATASOURCE_URL` | `spring.datasource.url` | `jdbc:postgresql://db:5432/postgres` |
| `SPRING_DATASOURCE_USERNAME` / `..._PASSWORD` | db user/pass | `postgres` / `a` |
| `JWT_SECRET` | `jwt.secret` | dev default in compose — **set a strong random value in production** |

## Contribute

1. Fork / clone, then create a feature branch: `git checkout -b feat/my-change`
2. For backend changes: `./mvnw compile` must pass. For frontend: `cd frontend && npx tsc -b`.
3. Verify with the Docker stack (`docker compose up --build`) and smoke-test
   register → login → create post in the UI.
4. Commit with conventional messages (`feat:`, `fix:`, `chore:`) and open a PR
   against `main` describing what and why.

Good first issues: ownership checks on post edit/delete, proper 404/409 error codes,
user profile endpoint, pagination on post lists.
