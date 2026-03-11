#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "${SCRIPT_DIR}/.." && pwd)"
ACCEPTANCE_DIR="${PROJECT_ROOT}/asseptense-test"
COMPOSE_FILE="${PROJECT_ROOT}/docker-compose.yml"

docker compose -f "${COMPOSE_FILE}" down
docker rmi -f bank2-backend:latest || true

mvn -f "${PROJECT_ROOT}/pom.xml" clean install
docker compose -f "${COMPOSE_FILE}" up -d

sleep 10

mvn -f "${ACCEPTANCE_DIR}/pom.xml" -Dtest=RunCucumberTest test
