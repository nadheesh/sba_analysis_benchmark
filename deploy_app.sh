#!/usr/bin/env bash

cd sample_apps

JAR_NAME="sample_apps-0.1.0.jar"
IMAGE_NAME="sample_app"

ECHO_IMAGE="echo"
PRIME_IMAGE="prime"

echo building the jar
mvn clean install

echo Removing existing images and containers

docker rmi -f $IMAGE_NAME
docker system prune -f


echo build the docker images $IMAGE_NAME
docker build ./ -t $IMAGE_NAME

echo deploy the docker image $IMAGE_NAME

if [[ $# < 2 ]]
then
    docker run -p 8090:8090 --cpus 2 --name $IMAGE_NAME $IMAGE_NAME
else
    docker run -p 8090:8090 --cpus 2 --name $IMAGE_NAME --link $1 --link $2 $IMAGE_NAME
fi


