FROM java:8
COPY ./target/sort-service-0.1.0.jar /usr/app/
WORKDIR /usr/app
RUN sh -c 'touch sort-service-0.1.0.jar'
ENTRYPOINT ["java","-jar","sort-service-0.1.0.jar"]