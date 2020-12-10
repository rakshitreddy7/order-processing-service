FROM openjdk:11
ADD target/order-processing-service.jar order-processing-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "order-processing-service.jar"]
