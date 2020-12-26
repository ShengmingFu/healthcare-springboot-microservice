#Dockerfile-Configservice
#========================================
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD out/config-service.jar config.jar
ENV JAVA_OPTS=""

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/config.jar"]
EXPOSE 8088