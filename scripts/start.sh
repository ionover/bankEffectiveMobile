#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "${SCRIPT_DIR}/.." && pwd)"
COMPOSE_FILE="${PROJECT_ROOT}/docker-compose.yml"
ENV_FILE="${1:-${PROJECT_ROOT}/.env}"

mvn -f "${PROJECT_ROOT}/pom.xml" clean install
docker compose --env-file "${ENV_FILE}" -f "${COMPOSE_FILE}" up -d
