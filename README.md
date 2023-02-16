# advanced_webapp

apache download link: https://archive.apache.org/dist/kafka/3.3.1/kafka_2.13-3.3.1.tgz

mysql -h 127.0.0.1 -P 3306 -u root -p

to containerize application:

# build artifact without tests

./mvnw package -DskipTests 

# build images and run container 
docker-compose down
docker-compose up --build

