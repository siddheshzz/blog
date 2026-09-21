```

docker compose up db adminer   # :5432 postgres, :8888 adminer
./mvnw spring-boot:run         # :8080 backend
cd frontend && npm run dev     # :5173, vite proxies /api -> :8080

```