FROM jdk:latest
EXPOSE 8080
ADD king-demo-api.jar /king-demo-api.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java -server $JAVA_OPTS -jar /king-demo-api.jar" ]
