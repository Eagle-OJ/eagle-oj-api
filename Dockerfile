FROM ubuntu:16.04

LABEL "version"="1.0"
LABEL "maintainer"="chendingchao1@126.com"

WORKDIR /eagle-oj

RUN apt update && \
	apt install -y git && \
	apt install -y openjdk-8-jre && \
	apt install -y openjdk-8-jdk && \
	apt install -y maven && \
	apt install -y curl && \
	curl -sL https://deb.nodesource.com/setup_8.x | bash - && \
	apt install -y nodejs && \
	git clone https://github.com/Eagle-OJ/eagle-oj-web.git && \
	git clone https://github.com/Eagle-OJ/eagle-oj-api.git && \
	cd eagle-oj-web && \
	npm install && \
	npm run build && \
	mv dist/* ../eagle-oj-api/eagle-oj-web/src/main/resources/public/ && \
	cd ../eagle-oj-api && \
	mvn clean package && \
	mv eagle-oj-web/target/eagle-oj-web-1.0.jar /eagle-oj && \
	cd /eagle-oj && \
	apt clean && \
	rm -rf eagle-oj-api && \
	rm -rf eagle-oj-web && \
	apt remove -y git && \
	apt remove -y maven && \
	apt remove -y nodejs && \
	apt remove -y openjdk-8-jdk && \
	rm -rf /root/.m2 && \
	rm -rf /root/.npm

VOLUME ["/eagle-oj/data"]

# ENV MYSQL_URL=101.132.164.120:3306 MYSQL_DATABASE=eagle_oj MYSQL_USERNAME=root MYSQL_PASSWORD=eagle_oj

CMD ["java", "-Dspring.profiles.active=prod", "-jar", "eagle-oj-web-1.0.jar"]

EXPOSE 8080