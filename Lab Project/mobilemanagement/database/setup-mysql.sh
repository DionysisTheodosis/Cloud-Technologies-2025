#!/usr/bin/env bash
set -euo pipefail

# Absolute path to this script's folder
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# Load environment and helpers
source "$SCRIPT_DIR/../scripts/env.sh"
source "$SCRIPT_DIR/../scripts/gsm-utils.sh"


# Fetch secrets
ACCESS_TOKEN=$(get_access_token)
APP_DB=$(get_secret MYSQL_DATABASE "$ACCESS_TOKEN")
APP_USER=$(get_secret MYSQL_USER "$ACCESS_TOKEN")
APP_PASS=$(get_secret MYSQL_PASSWORD "$ACCESS_TOKEN")
ROOT_PASSWORD=$(get_secret MYSQL_ROOT_PASSWORD "$ACCESS_TOKEN")

# Start MySQL container with temporary password
docker run -d \
  --name "$MYSQL_CONTAINER_NAME" \
  --network "$NETWORK_NAME" \
  --network-alias mysql \
  -e MYSQL_DATABASE="$APP_DB" \
  -e MYSQL_ROOT_PASSWORD="toor" \
  -e MYSQL_ONETIME_PASSWORD="true" \
  -v "$MYSQL_VOLUME_NAME":/var/lib/mysql \
  --cpus="1.0" --memory="512m" \
  --restart unless-stopped \
  "$MYSQL_IMAGE"

echo "Waiting for MySQL to be ready..."
# Loop until MySQL accepts connections with temporary password
until docker exec "$MYSQL_CONTAINER_NAME" mysqladmin ping -uroot -ptoor --silent; do
    echo -n "."
    sleep 2
done

echo " MySQL is up!"


# Apply real root password and create app user/db
docker exec -i "$MYSQL_CONTAINER_NAME" mysql -uroot -ptoor --connect-expired-password <<SQL
FLUSH PRIVILEGES;
CREATE USER IF NOT EXISTS 'root'@'%' IDENTIFIED BY '${ROOT_PASSWORD}';
ALTER USER 'root'@'%' IDENTIFIED BY '${ROOT_PASSWORD}';
CREATE USER IF NOT EXISTS 'root'@'localhost' IDENTIFIED BY '${ROOT_PASSWORD}';
ALTER USER 'root'@'localhost' IDENTIFIED BY '${ROOT_PASSWORD}';
CREATE DATABASE IF NOT EXISTS \`${APP_DB}\`;
CREATE USER IF NOT EXISTS '${APP_USER}'@'%' IDENTIFIED BY '${APP_PASS}';
GRANT ALL PRIVILEGES ON \`${APP_DB}\`.* TO '${APP_USER}'@'%';
FLUSH PRIVILEGES;
SQL


echo "✅ MySQL setup complete. App user '${APP_USER}' created on DB '${APP_DB}'."
