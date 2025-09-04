#!/bin/bash
set -euo pipefail

SERVICE_ACCOUNT_PATH="/secrets/service_account.json"

echo "Authenticating with Google Cloud..."
gcloud auth activate-service-account --key-file="$SERVICE_ACCOUNT_PATH"
echo "Authenticated successfully."

# Set project for gcloud commands
gcloud config set project "$PROJECT_ID"

# Run SQL directly with secrets from Secret Manager
mysql -h mysql -uroot -ptoor --connect-expired-password <<SQL
ALTER USER 'root'@'%' IDENTIFIED BY '$(gcloud secrets versions access latest --secret="MYSQL_ROOT_PASSWORD")';
CREATE USER IF NOT EXISTS 'root'@'localhost' IDENTIFIED BY '$(gcloud secrets versions access latest --secret="MYSQL_ROOT_PASSWORD")';
ALTER USER 'root'@'localhost' IDENTIFIED BY '$(gcloud secrets versions access latest --secret="MYSQL_ROOT_PASSWORD")';
CREATE DATABASE IF NOT EXISTS \`$(gcloud secrets versions access latest --secret="MYSQL_DATABASE")\`;
CREATE USER IF NOT EXISTS '$(gcloud secrets versions access latest --secret="MYSQL_USER")'@'%' IDENTIFIED BY '$(gcloud secrets versions access latest --secret="MYSQL_PASSWORD")';
GRANT ALL PRIVILEGES ON \`$(gcloud secrets versions access latest --secret="MYSQL_DATABASE")\`.* TO '$(gcloud secrets versions access latest --secret="MYSQL_USER")'@'%';
FLUSH PRIVILEGES;
SQL

echo "Secrets applied and db initialised."
