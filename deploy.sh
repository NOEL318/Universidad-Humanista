cd /Universidad-Humanista || exit
echo "Pulling latest changes from master..."
git pull origin master

echo "Reconstruyendo app..."
# Si usas Docker:
docker compose down
docker compose up -d --build

echo "¡Deploy terminado!"