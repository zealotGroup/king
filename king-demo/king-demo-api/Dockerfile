FROM java:8
EXPOSE 8080
ARG JAR_FILE
ADD ${JAR_FILE} king-demo-api.jar
RUN sh -c 'touch /king-demo-api.jar'
ENV JAVA_OPTS="-server"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /king-demo-api.jar" ]
