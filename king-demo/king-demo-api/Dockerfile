FROM jdk:latest
EXPOSE 8080
ADD king-demo-api.jar /king-demo-api.jar
ENV JAVA_OPTS="-server"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /king-demo-api.jar" ]
