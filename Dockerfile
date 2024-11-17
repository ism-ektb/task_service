FROM amazoncorretto:17
WORKDIR /app
COPY target/*.jar task_service.jar
ENTRYPOINT ["java", "-jar", "task_service.jar"]