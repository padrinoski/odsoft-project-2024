REM Load environment variables from config.bat
call config.bat

REM NETWORK
docker network inspect network >nul 2>&1
if errorlevel 1 (
    docker network create --driver overlay network
)

REM Build Docker image
docker build -t custom-jenkins .

REM JENKINS
docker stack deploy -c jenkins.yml jenkins

REM BOOT SERVICES
docker stack deploy -c boot-services.yml boot-services

REM WEB SERVER
docker stack deploy -c after-services.yml after-services

