# fiat-exchange-rates
FROM library/openjdk:8-alpine

MAINTAINER "<qyvlik@qq.com>"

WORKDIR /home/www

RUN adduser -D -u 1000 www www \
    && chown www:www -R /home/www

ADD target/*.jar /home/www/app.jar

EXPOSE 8080

USER www

ENTRYPOINT ["java", "-jar", "/home/www/app.jar"]
