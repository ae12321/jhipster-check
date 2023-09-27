# README

```

# set enviromnent key to local pc (*value is any value you like)
# do the same setting for the deployment destination
MYAPP_DATABASE_HOST=localhost
MYAPP_DATABASE_DATABASE=postgres
MYAPP_DATABASE_USERNAME=sqluser
MYAPP_DATABASE_PASSWORD=abc123
MYAPP_DATABASE_PORT=5432


# for executing simple tools
npm install -g tsx
tsx .\scripts\xxx.ts


# setup local database
docker compose up -d
docker compose down
docker compose down --volumes


# into database
docker compose exec db bash
psql -U xxx
\l
\d
\i ./scripts/1u.sql
\i ./scripts/1d.sql


# startup backend
.\mvnw

# startup frontend
npm start

```
