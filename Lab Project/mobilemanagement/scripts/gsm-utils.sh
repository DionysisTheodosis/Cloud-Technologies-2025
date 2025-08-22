#!/usr/bin/env bash
set -euo pipefail

# base64url encode
b64url() { openssl base64 -e -A | tr '+/' '-_' | tr -d '='; }

get_access_token() {
  local sa="$SERVICE_ACCOUNT_KEY_FILE"
  local client_email private_key token_uri header payload header_b64 payload_b64 signature jwt

  client_email=$(jq -r .client_email "$sa")
  private_key=$(jq -r .private_key "$sa")
  token_uri=$(jq -r .token_uri "$sa")

  header='{"alg":"RS256","typ":"JWT"}'
  now=$(date +%s)
  exp=$((now + 3600))
  payload=$(jq -n \
    --arg iss "$client_email" \
    --arg scope "https://www.googleapis.com/auth/cloud-platform" \
    --arg aud "$token_uri" \
    --argjson iat $now \
    --argjson exp $exp \
    '{iss:$iss, scope:$scope, aud:$aud, iat:$iat, exp:$exp}')

  header_b64=$(printf '%s' "$header" | b64url)
  payload_b64=$(printf '%s' "$payload" | b64url)
  signature=$(printf '%s' "${header_b64}.${payload_b64}" \
    | openssl dgst -sha256 -sign <(printf '%s' "$private_key") \
    | b64url)
  jwt="${header_b64}.${payload_b64}.${signature}"

  curl -s -X POST "$token_uri" \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "grant_type=urn:ietf:params:oauth:grant-type:jwt-bearer&assertion=$jwt" \
    | jq -r .access_token
}

get_secret() {
  local name="$1"; local token="$2"
  curl -s -H "Authorization: Bearer $token" \
    "https://secretmanager.googleapis.com/v1/projects/${GCP_PROJECT_ID}/secrets/${name}/versions/latest:access" \
    | jq -r '.payload.data' | base64 --decode
}
