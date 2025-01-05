REM Load environment variables from config.bat
call config.bat
call development.bat

start /B docker-compose -f books-query.yml -p books-query build
start /B docker-compose -f books-command.yml -p books-command build