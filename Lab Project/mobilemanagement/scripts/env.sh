# env.sh - Load environment variables from .env

set -euo pipefail

ENV_FILE="$(dirname "${BASH_SOURCE[0]}")/../.env"

if [ -f "$ENV_FILE" ]; then
    export $(grep -v '^#' "$ENV_FILE" | xargs)
else
    echo "Error: .env file not found at $ENV_FILE"
    exit 1
fi
