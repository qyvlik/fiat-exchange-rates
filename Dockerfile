# fiat-exchange-rates
FROM maven:alpine as builder
ADD ./pom.xml pom.xml
ADD ./src src/
VOLUME /var/maven/.m2
RUN mvn -DskipTests clean package

FROM library/openjdk:8-alpine
MAINTAINER "<qyvlik@qq.com>"
WORKDIR /home/www
RUN adduser -D -u 1000 www www \
    && chown www:www -R /home/www
COPY --from=builder target/*.jar app.jar
EXPOSE 8080
USER www
ENTRYPOINT ["java", "-jar", "/home/www/app.jar"]
