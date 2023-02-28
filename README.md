# advanced_webapp

apache download link: https://archive.apache.org/dist/kafka/3.3.1/kafka_2.13-3.3.1.tgz
quick start: https://kafka.apache.org/quickstart

### to containerize application:

./mvnw package -DskipTests 

### build images and run container 
docker-compose down
docker-compose up --build

docker rmi -f $(docker images -a -q)
./mvnw package -DskipTests

docker build -t my-app:1 .
docker login

docker tag my-app:1 jinshuangniu/advanced1cloud1computing:v1.0
docker push jinshuangniu/advanced1cloud1computing:v1.0


