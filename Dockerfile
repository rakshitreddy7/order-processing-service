FROM openjdk:8
ADD target/order-processing-service.jar order-processing-service.jar
ENTRYPOINT ["java", "-jar", "order-processing-service.jar"]
