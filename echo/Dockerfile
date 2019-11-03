FROM java:8
COPY ./target/echo-service-0.1.0.jar /usr/app/
WORKDIR /usr/app
RUN sh -c 'touch echo-service-0.1.0.jar'
ENTRYPOINT ["java","-jar","echo-service-0.1.0.jar"]