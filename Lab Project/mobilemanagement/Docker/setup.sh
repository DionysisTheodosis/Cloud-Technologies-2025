#!/usr/bin/env bash
set -euo pipefail

source "$(dirname "$0")/../scripts/env.sh"
echo "APP_IMAGE_NAME='$APP_IMAGE_NAME'"
echo "MYSQL_INIT_IMAGE='$MYSQL_INIT_IMAGE'"
DOCKERFILE_PATH="$(dirname "$0")/../docker/Dockerfile"
INIT_DOCKERFILE_PATH="$(dirname "$0")/../secret_manager/Dockerfile"

# Build App Docker image from Dockerfile
docker build -f "$DOCKERFILE_PATH" -t "$APP_IMAGE_NAME" .

if ! docker volume ls --format '{{.Name}}' | grep -q "^$MYSQL_VOLUME_NAME$"; then
  echo "Creating MySQL volume '$MYSQL_VOLUME_NAME'..."
  docker volume create "$MYSQL_VOLUME_NAME"
fi

if ! docker network ls --format '{{.Name}}' | grep -q "^$NETWORK_NAME$"; then
  echo "Creating Docker network '$NETWORK_NAME'..."
  docker network create "$NETWORK_NAME"
fi

# Build Init Image To Fetch Passwords From Google
docker build -f "$INIT_DOCKERFILE_PATH" -t "$MYSQL_INIT_IMAGE" "$(dirname "$INIT_DOCKERFILE_PATH")"
echo "Docker image built and volume/network created."
