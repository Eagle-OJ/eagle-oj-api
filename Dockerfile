FROM openjdk:8u151-jre-alpine3.7

WORKDIR /eagle-oj

COPY ./eagle-oj-web/target/eagle-oj-web-1.0.jar /eagle-oj

# ENV MYSQL_URL=101.132.164.120:3306 MYSQL_DATABASE=eagle_oj MYSQL_USERNAME=root MYSQL_PASSWORD=eagle_oj

CMD ["java", "-Dspring.profiles.active=prod", "-jar", "eagle-oj-web-1.0.jar"]

EXPOSE 8080