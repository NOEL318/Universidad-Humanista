#!/bin/bash
cd "$(dirname "$0")/.." || exit 1

for pid in .logs/*.pid; do
  [ -f "$pid" ] || continue
  pkill -P "$(cat "$pid")" 2>/dev/null
  kill "$(cat "$pid")" 2>/dev/null
  rm "$pid"
done
for port in 8080 8081 8082 8083 8084 5173; do
  lsof -ti tcp:$port | xargs kill 2>/dev/null
done

docker stop intranet-postgres intranet-redis >/dev/null 2>&1
echo "Todo detenido"
