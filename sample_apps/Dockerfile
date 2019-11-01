FROM java:8
COPY ./target/performance_simulator-1.0-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
RUN sh -c 'touch performance_simulator-1.0-SNAPSHOT.jar'
ENTRYPOINT ["java","-jar","performance_simulator-1.0-SNAPSHOT.jar"]