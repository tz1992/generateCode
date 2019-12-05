FROM hub.skyinno.com/common/jre8u121-debian:latest
MAINTAINER FAE Config Server "fae@fiberhome.com"
VOLUME /tmp
EXPOSE 8080
ADD ./target/${project.appName}.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]