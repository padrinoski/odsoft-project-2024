@echo off

REM Load environment variables from config.bat
call config.bat

REM Define environment file path
REM production.env / testing.env / development.env
set ENV_FILE=development.env

REM Build Docker images with environment variables
start /B docker-compose --env-file %ENV_FILE% -f boot-services.yml -p boot-services build
start /B docker-compose --env-file %ENV_FILE% -f populate-databases.yml -p populate-databases build
start /B docker-compose --env-file %ENV_FILE% -f books-query.yml -p books-query build
start /B docker-compose --env-file %ENV_FILE% -f books-command.yml -p books-command build
start /B docker-compose --env-file %ENV_FILE% -f users-query.yml -p users-query build
start /B docker-compose --env-file %ENV_FILE% -f users-command.yml -p users-command build
start /B docker-compose --env-file %ENV_FILE% -f lendings-query.yml -p lendings-query build
start /B docker-compose --env-file %ENV_FILE% -f lendings-command.yml -p lendings-command build
REM todo add more services here
start /B docker-compose --env-file %ENV_FILE% -f after-services.yml -p after-services build

REM Wait for all background processes to finish
:wait_loop
tasklist | find /I "docker-compose.exe" >nul 2>&1
if not errorlevel 1 (
    timeout /T 1 >nul
    goto wait_loop
)

@echo off
echo All Docker Compose builds are complete.
