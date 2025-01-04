REM Load environment variables from config.bat
call config.bat

REM Leave the swarm (if part of one)
docker swarm leave --force

REM Initialize a new swarm
docker swarm init

REM Remove all stacks
docker stack rm jenkins
docker stack rm boot-services
docker stack rm books-query
docker stack rm books-command
docker stack rm users-query
docker stack rm users-command
docker stack rm after-services
docker stack rm populate-databases

REM Wait for all stacks to be removed
:wait_loop_stacks
docker stack ls | findstr /I "boot-services\|books-query\|books-command\|users-query\|users-command\|after-services\|populate-databases" >nul 2>&1
if not errorlevel 1 (
    timeout /T 5 >nul
    goto wait_loop_stacks
)

REM Remove dangling Docker images
for /F "tokens=*" %%i IN ('docker images -f "dangling=true" -q') DO docker rmi -f %%i

REM Prune Docker volumes
docker volume prune -f

REM Remove Docker network
docker network rm network