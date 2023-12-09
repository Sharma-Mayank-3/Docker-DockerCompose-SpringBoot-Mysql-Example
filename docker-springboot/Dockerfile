FROM openjdk
EXPOSE 8080
WORKDIR /opt
COPY target/*.jar /opt/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]