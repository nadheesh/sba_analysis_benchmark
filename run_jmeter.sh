#!/usr/bin/env bash

echo process id : $$

if [[ $# == 2 ]]
then
    volume_path=$1
    sample_app=$2
else
    echo provide .jmx path
fi

# exports
export timestamp=$(date +%Y%m%d_%H%M%S) && \
export volume_path=$(realpath ${volume_path}) && \
export jmeter_path=/mnt/jmeter && \

#.jmx files
sample_app=${sample_app:=app1}

# user variables
arr_no_of_users=(1 10 100 500)
arr_message_size=(100 1000 5000)
arr_prime_size=(100000 1000000 10000000)

# file paths
log_dir=${jmeter_path}/client/${sample_app}_${timestamp}

mkdir log_dir

for no_of_users in ${arr_no_of_users[@]} ; do
    for message_size in ${arr_message_size[@]}; do
        for prime_size in ${arr_prime_size[@]} ; do
            echo
            echo running performance test with \
                no. of users: ${no_of_users}, \
                message size: ${message_size}, \
                prime size: ${prime_size}

            file_name=u${no_of_users}_m${message_size}_p${prime_size}
            summary_path=${log_dir}/${file_name}.csv

            sudo docker run --cpus 2 \
              --volume "${volume_path}":${jmeter_path} \
              vmarrazzo/jmeter \
              -JnoOfThreads=${no_of_users} \
              -JmessageSize=${message_size} \
              -JnumberSize=${prime_size} \
              -JresultPath=${summary_path} \
              -n -X \
              -t ${jmeter_path}/${sample_app}.jmx \
              -l ${log_dir}/${file_name}.jtl \
              -j ${log_dir}/${file_name}.log

             sleep 3

             echo saving stats collected within app:${sample_app}
             echo calling http://192.168.32.12:8090/pop_stats?filename=${file_name}_${timestamp}
             curl http://192.168.32.12:8090/pop_stats?filename=${file_name}_${timestamp}
        done
    done
done




