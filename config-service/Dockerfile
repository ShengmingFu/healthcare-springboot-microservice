#Dockerfile-Configservice
#========================================
FROM adoptopenjdk/openjdk14:ubi
VOLUME /tmp
EXPOSE 8088
ADD target/*.jar config.jar
ENV JAVA_OPTS=""

ENTRYPOINT ["java", "-jar", "config.jar"]