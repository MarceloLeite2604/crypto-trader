FROM openjdk:17-jdk-alpine3.14
WORKDIR /usr/app
COPY docker/app/docker-entrypoint.sh docker-entrypoint.sh
RUN chmod +x docker-entrypoint.sh
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["/usr/app/docker-entrypoint.sh"]
CMD ["/bin/sh",  "-c", "java $JAVA_OPTIONS -jar /usr/app/app.jar"]

# Use the following to analyse it through VisualVM.
# EXPOSE 9010
# ENV JAVA_ARGS="-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9010 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"
# CMD ["/bin/sh",  "-c", "java $JAVA_ARGS $JAVA_OPTIONS -jar /usr/app/app.jar"]