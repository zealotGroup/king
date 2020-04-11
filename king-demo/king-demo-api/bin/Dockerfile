FROM java:8
EXPOSE 8080
VOLUME /data
ADD king-demo-api.jar king-demo-api.jar
RUN sh -c 'touch /king-demo-api.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /king-demo-api.jar" ]