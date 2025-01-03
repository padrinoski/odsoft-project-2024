REM Load environment variables from config.bat
call config.bat

REM Stop Docker containers
start /B docker-compose -f boot-services.yml -p boot-services stop
start /B docker-compose -f books-query.yml -p books-query stop
start /B docker-compose -f books-command.yml -p books-command stop
REM todo add more services here
start /B docker-compose -f after-services.yml -p after-services stop
start /B docker-compose -f populate-databases.yml -p populate-databases stop

REM Wait for all background processes to finish
:wait_loop_stop
tasklist | find /I "docker-compose.exe" >nul 2>&1
if not errorlevel 1 (
    timeout /T 1 >nul
    goto wait_loop_stop
)

REM Remove Docker containers
start /B docker-compose -f boot-services.yml -p boot-services rm --force
start /B docker-compose -f books-query.yml -p books-query rm --force
start /B docker-compose -f books-command.yml -p books-command rm --force
REM todo add more services here
start /B docker-compose -f after-services.yml -p after-services rm --force
start /B docker-compose -f populate-databases.yml -p populate-databases rm --force

REM Wait for all background processes to finish
:wait_loop_rm
tasklist | find /I "docker-compose.exe" >nul 2>&1
if not errorlevel 1 (
    timeout /T 1 >nul
    goto wait_loop_rm
)

REM Remove dangling Docker images
for /F "tokens=*" %%i IN ('docker images -f "dangling=true" -q') DO docker rmi -f %%i

REM Prune Docker volumes
docker volume prune -f

REM Remove Docker network
docker network rm network