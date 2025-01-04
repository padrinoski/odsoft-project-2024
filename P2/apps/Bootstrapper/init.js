db = db.getSiblingDB('events')
db.createUser({
    user: 'user',
    pwd: 'pass',
    roles: [{
        role: 'readWrite',
        db: 'events'
    }]
})
