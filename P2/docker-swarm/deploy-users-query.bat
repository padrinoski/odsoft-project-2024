REM Load environment variables from config.bat
call config.bat


REM USERS QUERY
docker stack deploy --with-registry-auth -c users-query.yml users-query

REM Wait for the USERS QUERY service to be healthy
:wait_loop_users_query
docker service ls | findstr /I "users-query" | findstr /I "1/1" >nul 2>&1
if errorlevel 1 (
    timeout /T 5 >nul
    goto wait_loop_users_query
)