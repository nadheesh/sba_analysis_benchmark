FROM java:8
COPY ./target/prime-service-0.1.0.jar /usr/app/
WORKDIR /usr/app
RUN sh -c 'touch prime-service-0.1.0.jar'
ENTRYPOINT ["java","-jar","prime-service-0.1.0.jar"]