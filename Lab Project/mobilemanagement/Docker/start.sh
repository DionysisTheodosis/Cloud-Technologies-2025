#!/usr/bin/env bash
set -euo pipefail

# Absolute path to this script's folder
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# Load environment variables
source "$SCRIPT_DIR/../scripts/env.sh"

echo "Starting MySQL setup..."
# Call setup-mysql.sh using absolute path
"$SCRIPT_DIR/../database/setup-mysql.sh"

# Optional wait for MySQL to fully initialize
echo "Waiting 10s for MySQL to initialize..."
sleep 10

# --- Start Spring Boot app container ---
echo "Starting MobileManagement app container..."
docker run -d \
  --name "$APP_CONTAINER_NAME" \
  --network "$NETWORK_NAME" \
  -p "$APP_PORT:8080" \
  -e MYSQL_HOST="$MYSQL_HOST" \
  -e MYSQL_PORT="$MYSQL_PORT" \
  -e PROFILE="$PROFILE" \
  -v "$(pwd)/$SERVICE_ACCOUNT_KEY_FILE:/app/gcp-credentials.json:ro" \
  -e GOOGLE_APPLICATION_CREDENTIALS="/app/gcp-credentials.json" \
  --cpus="1.0" \
  --memory="256m" \
  --restart unless-stopped \
  "$APP_IMAGE_NAME"