REM Load environment variables from config.bat
call config.bat

REM USERS COMMAND
docker stack deploy --with-registry-auth -c users-command.yml users-command

REM Wait for the USERS COMMAND service to be healthy
:wait_loop_users_command
docker service ls | findstr /I "users-command" | findstr /I "1/1" >nul 2>&1
if errorlevel 1 (
    timeout /T 5 >nul
    goto wait_loop_users_command
)