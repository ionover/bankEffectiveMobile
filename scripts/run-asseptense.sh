#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "${SCRIPT_DIR}/.." && pwd)"
ACCEPTANCE_DIR="${PROJECT_ROOT}/asseptense-test"

"${SCRIPT_DIR}/down.sh"
"${SCRIPT_DIR}/start.sh"

sleep 10

mvn -f "${ACCEPTANCE_DIR}/pom.xml" -Dtest=RunCucumberTest test
