FROM openjdk:11
COPY target/backend*.jar /usr/src/backend.jar
COPY src/main/resources/application.properties /opt/conf/application.properties
CMD ["java", "-jar", "/usr/src/backend.jar", "--spring.config.location=file:/opt/conf/application.properties"]
