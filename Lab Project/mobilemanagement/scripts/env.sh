# env.sh - Load environment variables from .env

set -euo pipefail

ENV_FILE="$(dirname "${BASH_SOURCE[0]}")/../.env"

if [ -f "$ENV_FILE" ]; then
    while IFS= read -r line; do
        # Ignore comments and blank lines
        [[ "$line" =~ ^[[:space:]]*#|^[[:space:]]*$ ]] && continue
        export "$line"
    done < "$ENV_FILE"
else
    echo "Error: .env file not found at $ENV_FILE"
    exit 1
fi