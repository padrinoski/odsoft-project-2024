REM Constants
set MONGO_DB_IMAGE=mongo:latest
set REDIS_DB_IMAGE=bitnami/redis:7.2.2
set MONGO_DB=1
set REDIS_DB=2
set MONGO_DB_PORT=27017
set REDIS_DB_PORT=6379
set ID_GENERATOR=1
SET RECOMMENDATION_ALGORITHM=1
SET RECOMMENDATION_X=1
SET RECOMMENDATION_Y=2
SET MINIMUM_AGE=10
SET MAXIMUM_AGE=18

REM ########################################################################################################################
REM BOOKS - COMMAND
set BOOKS_COMMAND_DATABASE_IMAGE=mongo:latest
set BOOKS_COMMAND_DATABASE_TO_USE=1
set BOOKS_COMMAND_DB_PORT=27017

set USERS_COMMAND_DATABASE_IMAGE=mongo:latest
set USERS_COMMAND_DATABASE_TO_USE=1
set USERS_COMMAND_DB_PORT=27017



REM todo add more services here

REM ########################################################################################################################
REM Export variables (not necessary in batch files, as variables are available globally within the script)