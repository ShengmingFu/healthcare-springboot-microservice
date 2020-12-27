#Dockerfile-Enrolleeservice
#========================================
FROM adoptopenjdk/openjdk14:ubi
VOLUME /tmp
ADD target/*.jar enrollee.jar
ENV JAVA_OPTS=""

ENTRYPOINT ["java","-jar","/enrollee.jar"]
EXPOSE 8081