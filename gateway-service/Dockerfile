#Dockerfile-Gatewayservice
#========================================
FROM adoptopenjdk/openjdk14:ubi
VOLUME /tmp
ADD target/*.jar gateway.jar
ENV JAVA_OPTS=""

ENTRYPOINT ["java","-jar","/gateway.jar"]
EXPOSE 9092