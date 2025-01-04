REM Load environment variables from config.bat
call config.bat

REM BOOKS COMMAND
docker stack deploy -c books-command.yml books-command

REM Wait for the BOOKS COMMAND service to be healthy
:wait_loop_books_command
docker service ls | findstr /I "books-command" | findstr /I "1/1" >nul 2>&1
if errorlevel 1 (
    timeout /T 5 >nul
    goto wait_loop_books_command
)