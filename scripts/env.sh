#!/bin/bash
# Crea el .env con contraseñas aleatorias la primera vez
cd "$(dirname "$0")/.." || exit 1

if [ ! -f .env ]; then
  echo "Creando .env..."
  {
    echo "DB_PASSWORD=$(openssl rand -hex 16)"
    echo "ADMIN_EMAIL=admin@intranet.local"
    echo "ADMIN_PASSWORD=$(openssl rand -base64 12 | tr -d '/+=')"
    echo "WEBHOOK_SECRET=$(openssl rand -hex 20)"
  } > .env
  chmod 600 .env
  echo "Usuario admin: $(grep ADMIN_EMAIL .env | cut -d= -f2)"
  echo "Contraseña admin: $(grep ADMIN_PASSWORD .env | cut -d= -f2)"
fi

set -a
source .env
set +a
