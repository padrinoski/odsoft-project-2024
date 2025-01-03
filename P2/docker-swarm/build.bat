REM Load environment variables from config.bat
call config.bat

REM Build Docker images
start /B docker-compose -f boot-services.yml -p boot-services build
start /B docker-compose -f populate-databases.yml -p populate-databases build
start /B docker-compose -f books-query.yml -p books-query build
start /B docker-compose -f books-command.yml -p books-command build
REM todo add more services here
start /B docker-compose -f after-services.yml -p after-services build

REM Wait for all background processes to finish
REM Note: Windows batch files do not have a built-in 'wait' command. We will use a workaround.
:wait_loop
tasklist | find /I "docker-compose.exe" >nul 2>&1
if not errorlevel 1 (
    timeout /T 1 >nul
    goto wait_loop
)