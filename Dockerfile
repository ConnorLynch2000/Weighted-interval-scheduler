FROM openjdk:17-alpine
ADD target/scheduler-engine*.jar /opt/scheduler-engine.jar
ENTRYPOINT ["java", "-jar", "scheduler-engine.jar"]