FROM openjdk:11

# Not exposing but for documentation
EXPOSE 8082
ADD /build/libs/employee_service.jar employee_service.jar
ENTRYPOINT ["java","-jar","employee_service.jar"]