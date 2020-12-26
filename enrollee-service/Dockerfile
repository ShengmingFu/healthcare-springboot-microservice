#Dockerfile-Enrolleeservice
#========================================
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD out/enrollee-service.jar enrollee.jar
ENV JAVA_OPTS=""

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/enrollee.jar"]
EXPOSE 8081