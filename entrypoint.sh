#!/bin/sh

exec java -jar \
    -Dserver.port=${PORT} \
    -Ddb.addr=${DB_ARRD} \
    -Ddb.port=${DB_PORT} \
    -Ddb.name=${DB_NAME} \
    -Ddb.user=${DB_USER} \
    -Ddb.password=${DB_PASSWORD} \
    blog-backend-${VERSION}.jar