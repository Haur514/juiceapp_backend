mkdir dbdata
docker-compose up -d
docker exec -it postgresql bash /docker-entrypoint-initdb.d/00_init-script.sh 
./gradlew bootRun