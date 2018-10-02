FROM openjdk:8-slim
VOLUME /tmp
ARG JAR_FILE
ADD target/${JAR_FILE} app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS="-XX:MaxRAM=400m -XX:+UseSerialGC -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap"
ENV SERVER_PORT 8080
EXPOSE $SERVER_PORT
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Dserver.port=$SERVER_PORT"]