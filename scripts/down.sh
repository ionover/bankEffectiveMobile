#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "${SCRIPT_DIR}/.." && pwd)"
COMPOSE_FILE="${PROJECT_ROOT}/docker-compose.yml"
ENV_FILE="${1:-${PROJECT_ROOT}/.env}"

docker compose --env-file "${ENV_FILE}" -f "${COMPOSE_FILE}" down
docker rmi -f bank2-backend:latest || true
