REM Load environment variables from config.bat
call config.bat
call development.bat

start /B docker-compose -f users-query.yml -p users-query build
start /B docker-compose -f users-command.yml -p users-command build