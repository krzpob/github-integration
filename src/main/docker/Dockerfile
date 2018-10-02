FROM java:8-jre-alpine
VOLUME /tmp
ADD github-integration.jar app.jar
ENV JAVA_OPTS=""
ENV SERVER_PORT="8080"
EXPOSE $SERVER_PORT
CMD ["touch","app.jar"]
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom -Dserver.port=$SERVER_PORT","-jar","/app.jar","--logging.file=/var/log/github-integration.jar/github-integration.log" ]