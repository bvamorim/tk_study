#FROM java:8
#EXPOSE 8080
#COPY src /home/app/src
#COPY pom.xml /home/app
#RUN mvn -f /home/app/pom.xml clean package
#ADD target/aggregate-1.0.0.jar aggregate-1.0.0.jar
#ENTRYPOINT ["java", "-jar", "aggregate-1.0.0.jar"]

#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip=true

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/aggregate-1.0.0.jar /usr/local/lib/aggregate-1.0.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/aggregate-1.0.0.jar"]