#!/bin/bash
# Borra todo menos el volumen de la bd
cd "$(dirname "$0")/.." || exit 1
kubectl delete -f k8s/ --ignore-not-found --wait=false
