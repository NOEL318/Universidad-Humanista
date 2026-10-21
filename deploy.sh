#!/bin/bash
# Lo ejecuta el webhook de github en cada push a master
cd "$(dirname "$0")" || exit 1
echo "Pulling latest changes from master..."
git fetch origin master
git reset --hard origin/master

echo "Reconstruyendo app..."
./scripts/k8s-up.sh

echo "¡Deploy terminado!"
