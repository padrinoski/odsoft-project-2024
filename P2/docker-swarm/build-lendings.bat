REM Load environment variables from config.bat
call config.bat
call development.bat

start /B docker-compose -f lendings-query.yml -p lendings-query build
start /B docker-compose -f lendings-command.yml -p lendings-command build