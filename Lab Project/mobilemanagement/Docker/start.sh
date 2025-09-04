#!/usr/bin/env bash
set -euo pipefail

# Absolute path to this script's folder
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# Load environment variables
source "$SCRIPT_DIR/../scripts/env.sh"

if [ "$(docker ps --filter "name=^${MYSQL_CONTAINER_NAME}$" -q)" ]; then
  echo "MySQL container is already running."
elif [ "$(docker ps -a --filter "name=^${MYSQL_CONTAINER_NAME}$" -q)" ]; then
  echo "Starting existing MySQL container..."
  docker start "$MYSQL_CONTAINER_NAME"
else
  echo "Running new MySQL container..."
  "$SCRIPT_DIR/../database/setup-mysql.sh"
fi

if [ "$(docker ps --filter "name=^${APP_CONTAINER_NAME}$" -q)" ]; then
  echo "APP container is already running."
elif [ "$(docker ps -a --filter "name=^${APP_CONTAINER_NAME}$" -q)" ]; then
  echo "Starting existing APP container..."
  docker start "$APP_CONTAINER_NAME"
else
  echo "Starting new MobileManagement app container..."
  docker run -d \
    --name "$APP_CONTAINER_NAME" \
    --network "$NETWORK_NAME" \
    -p "$APP_PORT:8080" \
    -e PROFILE="$PROFILE" \
    -v "$(pwd)/$SERVICE_ACCOUNT_KEY_FILE:/app/gcp-credentials.json:ro" \
    -e GOOGLE_APPLICATION_CREDENTIALS="/app/gcp-credentials.json" \
    --cpus="2.0" \
    --memory="1024m" \
    --restart unless-stopped \
    --security-opt no-new-privileges:true \
    "$APP_IMAGE_NAME"
fi
