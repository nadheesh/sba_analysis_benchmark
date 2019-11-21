#!/usr/bin/env bash


JAR_NAME="sample_apps-0.1.0.jar"
IMAGE_NAME="sample_app"

mkdir stat
stat_dir=stat/$(date +%Y%m%d_%H%M%S)
mkdir ${stat_dir}

echo saving stats at ${stat_dir}

volume_path=$(realpath ${stat_dir})

export volume_path=${volume_path} && \
export work_path=/usr/app/temp && \

echo
cd sample_apps


echo stoping all the containers
docker stop $(docker ps -a -q)

echo building the jar
mvn clean install

echo Removing existing images and containers

docker rmi -f ${IMAGE_NAME}

echo build the docker images ${IMAGE_NAME}
docker build ./ -t ${IMAGE_NAME}

docker system prune -f

echo deploy the docker image ${IMAGE_NAME}

if [[ $# < 2 ]]
then
    docker run --volume "${volume_path}":${work_path} \
        -p 8090:8090 -d --cpus 2 --name ${IMAGE_NAME} ${IMAGE_NAME}
else
    docker run --volume "${volume_path}":${work_path} \
        -p 8090:8090 -d --cpus 2 --name ${IMAGE_NAME} --link $1 --link $2 ${IMAGE_NAME}
fi


