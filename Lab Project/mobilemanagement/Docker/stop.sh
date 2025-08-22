#!/usr/bin/env bash
set -euo pipefail

# Load environment
source "$(dirname "$0")/../scripts/env.sh"

echo "Stopping MobileManagement app container..."
docker stop "$APP_CONTAINER_NAME" 2>/dev/null || true

echo "Stopping MySQL container..."
docker stop "$MYSQL_CONTAINER_NAME" 2>/dev/null || true

echo "Containers stopped."
