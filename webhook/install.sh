#!/bin/bash
# Instala el webhook en el VPS (ubuntu/debian): sudo ./webhook/install.sh
cd "$(dirname "$0")/.." || exit 1
APP_DIR=$(pwd)
source scripts/env.sh

if ! command -v webhook >/dev/null; then
  apt-get update && apt-get install -y webhook
fi

sed "s|__APP_DIR__|$APP_DIR|g" webhook/webhook.service > /etc/systemd/system/intranet-webhook.service
systemctl daemon-reload
systemctl enable --now intranet-webhook
systemctl restart intranet-webhook

echo ""
echo "En GitHub > Settings > Webhooks > Add webhook:"
echo "  Payload URL:  http://$(curl -s ifconfig.me):9000/hooks/deploy"
echo "  Content type: application/json"
echo "  Secret:       $WEBHOOK_SECRET"
echo "  Events:       Just the push event"
