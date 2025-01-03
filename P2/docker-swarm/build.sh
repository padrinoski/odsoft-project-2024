source ./config.sh

docker-compose -f boot-services.yml -p boot-services build &
docker-compose -f populate-databases.yml -p populate-databases build &
docker-compose -f books-query.yml -p books-query build &
docker-compose -f books-command.yml -p books-command build &
# todo add more services here
docker-compose -f after-services.yml -p after-services build &
wait