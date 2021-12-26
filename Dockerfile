FROM openjdk:17-jdk-alpine3.14
WORKDIR /usr/app
COPY docker/app/docker-entrypoint.sh docker-entrypoint.sh
RUN chmod +x docker-entrypoint.sh
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["/usr/app/docker-entrypoint.sh"]
CMD ["java","-jar","/usr/app/app.jar"]