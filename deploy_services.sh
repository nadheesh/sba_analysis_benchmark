#!/usr/bin/env bash

SERVICE_NAMES=('echo' 'prime')
PORT_LIST=(9000 9002)

echo building the serivces
for SERVICE in ${SERVICE_NAMES[*]}; do

    cd ${SERVICE}

    JAR_NAME=${SERVICE}"-0.1.0.jar"
    IMAGE_NAME=${SERVICE}_service

    echo building the jar ${SERVICE}
    mvn clean install

    echo removing existing images of ${IMAGE_NAME}
    docker rmi -f ${IMAGE_NAME}

    echo build the docker image ${IMAGE_NAME}
    docker build ./ -t ${IMAGE_NAME}

    cd ..
    done

echo cleaning existing images and containers
docker system prune -f

echo deploying the serivces
for ((i=0;i<${#SERVICE_NAMES[@]};++i)); do

    IMAGE_NAME=${SERVICE_NAMES[i]}"_service"

    echo deploy the docker image ${IMAGE_NAME}
    docker run -p ${PORT_LIST[i]}:${PORT_LIST[i]} -d --cpus 1 --name ${SERVICE_NAMES[i]} ${IMAGE_NAME}
done