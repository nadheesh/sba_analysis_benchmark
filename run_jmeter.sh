#!/usr/bin/env bash

echo process id : $$

curl http://192.168.32.12:8090/pop_stats?filename=temp
sleep 5

if [[ $# == 2 ]]
then
    volume_path=$1
    sample_app=$2
else
    echo provide mount directory and .jmx path
fi

# exports
export timestamp=$(date +%Y%m%d_%H%M%S) && \
export volume_path=$(realpath ${volume_path}) && \
export jmeter_path=/mnt/jmeter && \

#.jmx files
#sample_app=${sample_app:=app1}

# user variables
arr_no_of_users=(100 200)
arr_message_size=(200 2000 4000)
arr_prime=(35171 1299827)

#arr_no_of_users=(100)
#arr_message_size=(200)
#arr_prime=(1299827)

run_time=200

# file paths
log_dir=${jmeter_path}/jtls/${sample_app}_${timestamp}

#mkdir ${log_dir}

# shellcheck disable=SC2068
for no_of_users in ${arr_no_of_users[@]} ; do
    for message_size in ${arr_message_size[@]}; do
        for prime_number in ${arr_prime[@]} ; do
            echo
            echo running performance test with \
                no. of users: ${no_of_users}, \
                message size: ${message_size}, \
                prime number: ${prime_number}

            file_name=u${no_of_users}_m${message_size}_p${prime_number}_${sample_app}
            summary_path=${log_dir}/${file_name}.csv

            sudo docker run --cpus 2 \
              --volume "${volume_path}":${jmeter_path} \
              vmarrazzo/jmeter \
              -JnoOfThreads=${no_of_users} \
              -JmessageSize=${message_size} \
              -JprimeNumber=${prime_number} \
              -JrunTime=${run_time} \
              -JthinkTime=50 \
              -n -X \
              -t ${jmeter_path}/${sample_app}.jmx \
              -l ${log_dir}/${file_name}.jtl \
              -j ${log_dir}/${file_name}.log

             sleep 5

             echo saving stats collected within app:${sample_app}
             echo calling http://192.168.32.12:8090/pop_stats?filename=${file_name}_${timestamp}
             curl http://192.168.32.12:8090/pop_stats?filename=${file_name}_${timestamp}

             sudo docker container prune -f

             sleep 25
        done
    done
done




