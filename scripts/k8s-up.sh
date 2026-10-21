#!/bin/bash
# Construye las imagenes y despliega todo en kubernetes: ./scripts/k8s-up.sh
cd "$(dirname "$0")/.." || exit 1
source scripts/env.sh
set -e

SERVICES="api-gateway auth user member academic frontend"

for svc in $SERVICES; do
  echo "Construyendo intranet/$svc..."
  docker build -q -t intranet/$svc:latest ./$svc
done

# k3s y minikube no ven las imagenes de docker, hay que pasarlas
if command -v k3s >/dev/null; then
  for svc in $SERVICES; do docker save intranet/$svc:latest | sudo k3s ctr images import - >/dev/null; done
elif command -v minikube >/dev/null && minikube status >/dev/null 2>&1; then
  for svc in $SERVICES; do minikube image load intranet/$svc:latest; done
fi

kubectl apply -f k8s/namespace.yml
kubectl -n intranet create secret generic intranet-secret \
  --from-literal=DB_PASSWORD="$DB_PASSWORD" \
  --from-literal=ADMIN_EMAIL="$ADMIN_EMAIL" \
  --from-literal=ADMIN_PASSWORD="$ADMIN_PASSWORD" \
  --dry-run=client -o yaml | kubectl apply -f -
kubectl apply -f k8s/

for svc in $SERVICES; do
  kubectl -n intranet rollout restart deployment/$svc
done
for svc in $SERVICES; do
  kubectl -n intranet rollout status deployment/$svc --timeout=300s
done

kubectl -n intranet get pods
echo "Listo, el front esta en el puerto 80 del servidor (ingress)"
