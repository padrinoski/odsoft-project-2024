source ./config.sh

# NETWORK
docker network create network

# BOOT SERVICES
docker-compose -f boot-services.yml -p boot-services up -d

# BOOKS QUERY
docker-compose -f books-query.yml -p books-query up -d &
wait

# BOOKS COMMAND
docker-compose -f books-command.yml -p books-command up -d &
wait

sleep 30s

# AFTER SERVICES
# Populate databases
docker-compose -f after-services.yml -p after-services up -d &
docker-compose -f populate-databases.yml -p populate-databases up -d &
wait