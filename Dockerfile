FROM java:8
EXPOSE 8080
ARG JAR_DIR
ARG JAR_NAME
ADD ${JAR_DIR}/${JAR_NAME} ${JAR_NAME}
RUN sh -c 'touch /$JAVA_OPTS'
ENV JAVA_OPTS="-server"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /$JAVA_OPTS" ]
