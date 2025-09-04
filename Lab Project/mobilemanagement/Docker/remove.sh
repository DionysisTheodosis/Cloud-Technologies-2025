#!/usr/bin/env bash
set -euo pipefail

# Load environment
source "$(dirname "$0")/../scripts/env.sh"

echo "Removing MobileManagement app container..."
docker rm -f "$APP_CONTAINER_NAME" 2>/dev/null || true

echo "Removing MySQL container..."
docker rm -f "$MYSQL_CONTAINER_NAME" 2>/dev/null || true

# Remove volume if FORCE_REMOVE=1
if [ "${FORCE_REMOVE:-0}" = "1" ]; then
  echo "Removing MySQL volume '$MYSQL_VOLUME_NAME'..."
  docker volume rm "$MYSQL_VOLUME_NAME" 2>/dev/null || true
fi

# Remove network if FORCE_REMOVE=1
if [ "${FORCE_REMOVE:-0}" = "1" ]; then
  echo "Removing network '$NETWORK_NAME'..."
  docker network rm "$NETWORK_NAME" 2>/dev/null || true
fi

echo "Containers, volumes, and network removed."
