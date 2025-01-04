REM Load environment variables from config.bat
call config.bat


REM BOOKS QUERY
docker stack deploy -c books-query.yml books-query

REM Wait for the BOOKS QUERY service to be healthy
:wait_loop_books_query
docker service ls | findstr /I "books-query" | findstr /I "1/1" >nul 2>&1
if errorlevel 1 (
    timeout /T 5 >nul
    goto wait_loop_books_query
)