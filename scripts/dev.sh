#!/bin/bash
# Levanta todo en local sin kubernetes: ./scripts/dev.sh
cd "$(dirname "$0")/.." || exit 1
source scripts/env.sh
mkdir -p .logs

export DB_USER=intranet
export COOKIE_SECURE=false

up() { nc -z localhost "$1" >/dev/null 2>&1; }

if ! up 5432; then
  echo "Levantando postgres..."
  docker run -d --name intranet-postgres -p 5432:5432 \
    -e POSTGRES_DB=intranetdb -e POSTGRES_USER=intranet -e POSTGRES_PASSWORD="$DB_PASSWORD" \
    -v intranet_pg:/var/lib/postgresql/data postgres:16-alpine >/dev/null 2>&1 || docker start intranet-postgres >/dev/null
fi

if ! up 6379; then
  echo "Levantando redis..."
  docker run -d --name intranet-redis -p 6379:6379 redis:7-alpine >/dev/null 2>&1 || docker start intranet-redis >/dev/null
fi

until up 5432 && up 6379; do sleep 1; done
sleep 2

# user primero porque crea la tabla users que lee auth
for svc in user auth member academic api-gateway; do
  echo "Levantando $svc..."
  (cd $svc && exec ./gradlew bootRun -q) > .logs/$svc.log 2>&1 &
  echo $! > .logs/$svc.pid
  [ "$svc" = "user" ] && until grep -q "Started UserApplication" .logs/user.log 2>/dev/null; do sleep 2; done
done

if [ ! -d frontend/node_modules ]; then
  (cd frontend && npm install)
fi
(cd frontend && exec npm run dev) > .logs/frontend.log 2>&1 &
echo $! > .logs/frontend.pid

echo "Esperando a los servicios..."
for port in 8081 8082 8083 8084 8080 5173; do
  until up $port; do sleep 2; done
done
echo "Listo: http://localhost:5173  (logs en .logs/)"
