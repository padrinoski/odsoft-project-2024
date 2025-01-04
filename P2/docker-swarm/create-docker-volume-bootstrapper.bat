docker volume create mongo-init-scripts

echo ^
db = db.getSiblingDB('events')^
db.createUser({^
    user: 'user',^
    pwd: 'pass',^
    roles: [{^
        role: 'readWrite',^
        db: 'events'^
    }]^
}) > init.js

docker run --rm -v mongo-init-scripts:/data --mount type=bind,source=%cd%/init.js,target=/init.js,ro alpine sh -c "mkdir -p /data && cp /init.js /data"