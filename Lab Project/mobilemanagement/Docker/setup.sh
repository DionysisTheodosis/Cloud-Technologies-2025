set -euo pipefail

source "$(dirname "$0")/../scripts/env.sh"

DOCKERFILE_PATH="$(dirname "$0")/../Docker/Dockerfile"

# Build Docker image from Dockerfile
docker build -f "$DOCKERFILE_PATH" -t "$APP_IMAGE_NAME" .

# Create volume for MySQL if not already existing
docker volume create "$MYSQL_VOLUME_NAME" || true

# Create network for MySQL and APP if not already existing
docker network create "$NETWORK_NAME" || true

echo "Docker image built and volume/network created."
