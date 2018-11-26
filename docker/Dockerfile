FROM openjdk:8-jdk-alpine

EXPOSE 8866 18866

ADD jerry-config.jar /data1/weibo/app.jar
CMD ["java","-Xdebug","-Xrunjdwp:server=y,transport=dt_socket,address=18866,suspend=n","-jar","/data1/weibo/app.jar"]
