#!/usr/bin/env bash
set -euo pipefail

# Absolute path to this script's folder
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
INIT_DOCKERFILE_PATH="$(dirname "$0")/../secret_manager/Dockerfile"

# Load environment and helpers
source "$SCRIPT_DIR/../scripts/env.sh"

if ! docker image ls --format '{{.Name}}' | grep -q "^$MYSQL_INIT_IMAGE$"; then
  # Build Init Image To Fetch Passwords From Google
  echo "Creating GCP INIT IMAGE '$MYSQL_VOLUME_NAME'..."
  docker build -f "$INIT_DOCKERFILE_PATH" -t "$MYSQL_INIT_IMAGE" "$(dirname "$INIT_DOCKERFILE_PATH")"
fi

if ! docker volume ls --format '{{.Name}}' | grep -q "^$MYSQL_VOLUME_NAME$"; then
  echo "Creating MySQL volume '$MYSQL_VOLUME_NAME'..."
  docker volume create "$MYSQL_VOLUME_NAME"
fi

if ! docker network ls --format '{{.Name}}' | grep -q "^$NETWORK_NAME$"; then
  echo "Creating Docker network '$NETWORK_NAME'..."
  docker network create "$NETWORK_NAME"
fi

# Start MySQL container with temporary password
docker run -d \
  --name "$MYSQL_CONTAINER_NAME" \
  --network "$NETWORK_NAME" \
  --network-alias mysql \
  -e MYSQL_ROOT_PASSWORD="toor" \
  -e MYSQL_ONETIME_PASSWORD="true" \
  -v "$MYSQL_VOLUME_NAME":/var/lib/mysql \
  --cpus="2.0" --memory="4096m" \
  --restart unless-stopped \
  "$MYSQL_IMAGE"

echo "Waiting for MySQL to be ready with the correct root password..."
until docker exec "$MYSQL_CONTAINER_NAME" \
  mysql -u root -ptoor -e "SELECT 1;" >/dev/null 2>&1; do
  echo -n "."
  sleep 2
done

echo " MySQL is ready lets go to fetch pass!"

# Run the init container to set up MySQL
docker run --rm \
  --name "$MYSQL_INIT_NAME" \
  --network "$NETWORK_NAME" \
  -v "$(realpath "$SERVICE_ACCOUNT_KEY_FILE")":/secrets/service_account.json:ro \
  -e PROJECT_ID="$GCP_PROJECT_ID" \
  "$MYSQL_INIT_IMAGE"

echo "MySQL setup complete. App user created on DB."
