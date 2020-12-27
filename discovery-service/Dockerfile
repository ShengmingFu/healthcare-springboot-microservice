#Dockerfile-Discoveryservice
#========================================
FROM adoptopenjdk/openjdk14:ubi
VOLUME /tmp
EXPOSE 8761
ADD target/*.jar discovery.jar
ENV JAVA_OPTS=""

ENTRYPOINT ["java","-jar","/discovery.jar"]