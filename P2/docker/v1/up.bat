REM Load environment variables from config.bat
call config.bat

REM NETWORK
docker network create network

REM BOOT SERVICES
docker-compose -f boot-services.yml -p boot-services up -d

REM BOOKS QUERY
start /B docker-compose -f books-query.yml -p books-query up -d

REM Wait for the BOOKS QUERY service to start
:wait_loop_books_query
tasklist | find /I "docker-compose.exe" >nul 2>&1
if not errorlevel 1 (
    timeout /T 1 >nul
    goto wait_loop_books_query
)

REM BOOKS COMMAND
start /B docker-compose -f books-command.yml -p books-command up -d

REM Wait for the BOOKS COMMAND service to start
:wait_loop_books_command
tasklist | find /I "docker-compose.exe" >nul 2>&1
if not errorlevel 1 (
    timeout /T 1 >nul
    goto wait_loop_books_command
)

REM Sleep for 30 seconds
timeout /T 30

REM AFTER SERVICES
REM Populate databases
start /B docker-compose -f after-services.yml -p after-services up -d
start /B docker-compose -f populate-databases.yml -p populate-databases up -d

REM Wait for all background processes to finish
:wait_loop_after_services
tasklist | find /I "docker-compose.exe" >nul 2>&1
if not errorlevel 1 (
    timeout /T 1 >nul
    goto wait_loop_after_services
)