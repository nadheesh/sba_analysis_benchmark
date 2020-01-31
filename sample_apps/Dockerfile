FROM java:8
COPY ./target/sample_apps-0.1.0.jar /usr/app/
WORKDIR /usr/app
RUN sh -c 'touch sample_apps-0.1.0.jar'
ENTRYPOINT ["java","-jar","sample_apps-0.1.0.jar"]
