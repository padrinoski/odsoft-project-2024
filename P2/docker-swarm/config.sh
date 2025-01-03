# Constants
MONGO_DB_IMAGE="mongo:latest"
REDIS_DB_IMAGE="bitnami/redis:7.2.2"
MONGO_DB=1
REDIS_DB=2
MONGO_DB_PORT=27017
REDIS_DB_PORT=6379
export ID_GENERATOR=1
export RECOMMENDATION_ALGORITHM=1
export RECOMMENDATION_X=1
export RECOMMENDATION_Y=2
export MINIMUM_AGE=10
export MAXIMUM_AGE=18


########################################################################################################################
# BOOKS - COMMAND
BOOKS_COMMAND_DATABASE_IMAGE=$MONGO_DB_IMAGE
BOOKS_COMMAND_DATABASE_TO_USE=$MONGO_DB
BOOKS_COMMAND_DB_PORT=$MONGO_DB_PORT
# BOOKS_COMMAND_SKU_ALGORITHM=1 # 1, 2 or 3 (Algorithm used from the P1)

# todo add more services here


########################################################################################################################
export BOOKS_COMMAND_DATABASE_IMAGE
export BOOKS_COMMAND_DATABASE_TO_USE
export BOOKS_COMMAND_DB_PORT
