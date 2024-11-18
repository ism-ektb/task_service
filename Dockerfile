  FROM amazoncorretto:17
    COPY target/*.jar task_service.jar
    ENTRYPOINT ["java", "-jar", "/task_service.jar"]