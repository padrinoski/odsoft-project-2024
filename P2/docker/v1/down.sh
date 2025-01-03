source ./config.sh

docker-compose -f boot-services.yml -p boot-services stop &
docker-compose -f books-query.yml -p books-query stop &
docker-compose -f books-command.yml -p books-command stop &
# todo add more services here
docker-compose -f after-services.yml -p after-services stop &
docker-compose -f populate-databases.yml -p populate-databases stop &
wait

docker-compose -f boot-services.yml -p boot-services rm --force &
docker-compose -f books-query.yml -p books-query rm --force &
docker-compose -f books-command.yml -p books-command rm --force &
# todo add more services here
docker-compose -f after-services.yml -p after-services rm --force &
docker-compose -f populate-databases.yml -p populate-databases rm --force &
docker rmi $(docker images -f "dangling=true" -q) --force &
docker volume prune -f &
docker network rm network