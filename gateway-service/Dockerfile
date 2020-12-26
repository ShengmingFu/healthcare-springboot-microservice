#Dockerfile-Gatewayservice
#========================================
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD out/gateway-service.jar gateway.jar
ENV JAVA_OPTS=""

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/gateway.jar"]
EXPOSE 8080