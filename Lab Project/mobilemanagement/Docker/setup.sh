set -euo pipefail

source "$(dirname "$0")/../scripts/env.sh"

DOCKERFILE_PATH="$(dirname "$0")/../Docker/Dockerfile"

# Build Docker image from Dockerfile
docker build -f "$DOCKERFILE_PATH" -t "$APP_IMAGE_NAME" .

if ! docker volume ls --format '{{.Name}}' | grep -q "^$MYSQL_VOLUME_NAME$"; then
    echo "Creating MySQL volume '$MYSQL_VOLUME_NAME'..."
    docker volume create "$MYSQL_VOLUME_NAME"
fi

if ! docker network ls --format '{{.Name}}' | grep -q "^$NETWORK_NAME$"; then
    echo "Creating Docker network '$NETWORK_NAME'..."
    docker network create "$NETWORK_NAME"
fi

echo "Docker image built and volume/network created."
