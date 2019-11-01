FROM java:8
COPY ./target/gs-prime-service-0.1.0.jar /usr/app/
WORKDIR /usr/app
RUN sh -c 'touch gs-prime-service-0.1.0.jar'
ENTRYPOINT ["java","-jar","gs-prime-service-0.1.0.jar"]