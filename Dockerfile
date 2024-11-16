FROM amazoncorretto:17
COPY target/*.jar main-service.jar
ENTRYPOINT ["java", "-jar", "/task-service.jar"]