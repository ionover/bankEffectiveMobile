@echo off
setlocal

set "SCRIPT_DIR=%~dp0"
for %%I in ("%SCRIPT_DIR%..") do set "PROJECT_ROOT=%%~fI"
set "ACCEPTANCE_DIR=%PROJECT_ROOT%\asseptense-test"
set "DEV_ENV_FILE=%PROJECT_ROOT%\.dev.env"
set "COMPOSE_FILE=%PROJECT_ROOT%\docker-compose.yml"

docker compose --env-file "%DEV_ENV_FILE%" -f "%COMPOSE_FILE%" down
if errorlevel 1 exit /b %errorlevel%

docker rmi -f bank2-backend:latest >nul 2>&1

mvn -f "%PROJECT_ROOT%\pom.xml" clean install
if errorlevel 1 exit /b %errorlevel%

docker compose --env-file "%DEV_ENV_FILE%" -f "%COMPOSE_FILE%" up -d
if errorlevel 1 exit /b %errorlevel%

timeout /t 10 /nobreak >nul

mvn -f "%ACCEPTANCE_DIR%\pom.xml" -Dtest=RunCucumberTest test
exit /b %errorlevel%
