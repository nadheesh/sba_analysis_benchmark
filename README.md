# SBA Analysis Benchmark

## Services 

### 1. Echo Service

Resend the same message 

### 2. Prime Service

Response whether the given number is a prime or not

## Apps

### 1. Sample APP 1

A sequential application that invoke the prime service and echo service respectively.

## Installation 

### Deploying using Maven

To deploy each service and app follow the below steps. For example lets deploy the prime service

1. First set the configurations (host names and ports) in the application.properties file

        gedit prime/src/main/resources/application.properties

2. Go to the module. 
        
        cd prime
3. Use maven to start spring-boot services

        sudo mvn spring-boot:run
        
### Deploying as dockerized containers 

1. First set the configurations (host names and ports) in the application.properties file. Notice that it is important to configure sample applications with the names of the service containers to bridge them.

        gedit sample_apps/src/main/resources/application.properties

2. Execute the script deploy_services.sh to deploy the services as docker containers. Each service will be provided a single core.

        sudo ./deploy_services.sh
        
3. Then execute the script deploy_app.sh to deploy the sample application.
        
        sudo ./deploy_app.sh # without bridging 
   or
        
        sudo ./deploy_app.sh echo_container_name prime_container_name # with bridging 
        
## Collecting Stats for service calls

Perform the load test on the the deployed applications. To get the statistics for each service use the following url request

    curl host:port/get_stats
    
To clear the stats while obtaining the current stats use the following command.

    curl host:port/pop_stats
    
Instead get_app_stats.py can be used to print the summarized report by automatically collecting the stats from the application server.
