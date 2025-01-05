REM Load environment variables from config.bat
call config.bat

REM NETWORK
docker network inspect network >nul 2>&1
if errorlevel 1 (
    docker network create --driver overlay network
)

REM JENKINS
docker stack deploy -c jenkins.yml jenkins

REM BOOT SERVICES
docker stack deploy -c boot-services.yml boot-services

REM Wait for the BOOT SERVICES service to be healthy
:wait_loop_books_query
docker service ls | findstr /I "boot-services" | findstr /I "1/1" >nul 2>&1
if errorlevel 1 (
    timeout /T 5 >nul
    goto wait_loop_books_query
)

:wait_loop_users_query
docker service ls | findstr /I "boot-services" | findstr /I "1/1" >nul 2>&1
if errorlevel 1 (
    timeout /T 5 >nul
    goto wait_loop_users_query
)

REM BOOKS QUERY
docker stack deploy -c books-query.yml books-query

REM Wait for the BOOKS QUERY service to be healthy
:wait_loop_books_query
docker service ls | findstr /I "books-query" | findstr /I "1/1" >nul 2>&1
if errorlevel 1 (
    timeout /T 5 >nul
    goto wait_loop_books_query
)

REM BOOKS COMMAND
docker stack deploy -c books-command.yml books-command

REM Wait for the BOOKS COMMAND service to be healthy
:wait_loop_books_command
docker service ls | findstr /I "books-command" | findstr /I "1/1" >nul 2>&1
if errorlevel 1 (
    timeout /T 5 >nul
    goto wait_loop_books_command
)

REM USERS QUERY
docker stack deploy -c users-query.yml users-query

REM Wait for the USERS QUERY service to be healthy
:wait_loop_users_query
docker service ls | findstr /I "users-query" | findstr /I "1/1" >nul 2>&1
if errorlevel 1 (
    timeout /T 5 >nul
    goto wait_loop_users_query
)

REM USERS COMMAND
docker stack deploy -c users-command.yml users-command

REM Wait for the USERS COMMAND service to be healthy
:wait_loop_users_command
docker service ls | findstr /I "users-command" | findstr /I "1/1" >nul 2>&1
if errorlevel 1 (
    timeout /T 5 >nul
    goto wait_loop_users_command
)

REM Sleep for 30 seconds
timeout /T 30

REM AFTER SERVICES
REM Populate databases
docker stack deploy -c after-services.yml after-services
docker stack deploy -c populate-databases.yml populate-databases

REM Wait for all services to be healthy
:wait_loop_after_services
docker service ls | findstr /I "after-services" | findstr /I "1/1" >nul 2>&1
if errorlevel 1 (
    timeout /T 5 >nul
    goto wait_loop_after_services
)
docker service ls | findstr /I "populate-databases" | findstr /I "1/1" >nul 2>&1
if errorlevel 1 (
    timeout /T 5 >nul
    goto wait_loop_after_services
)