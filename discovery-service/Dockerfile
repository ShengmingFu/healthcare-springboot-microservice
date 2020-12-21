#Dockerfile-Discoveryservice
#========================================
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/discovery-service.jar discovery.jar
ENV JAVA_OPTS=""

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/discovery.jar"]
EXPOSE 8761