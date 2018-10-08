# fiat-exchange-rates

fiat exchange rates

## docker

### docker pull and run

make sure you have a `application-prod.yml` file such as follow:

```
spring:
  redis:
    database: 0
    host: THE-REDIS-HOST
    port: 6379
    password:
    timeout: 10000
provider:
  CurrencyLayer:
    username: OPTIONAL
    key: KEY-OF-CurrencyLayer
    plan: free
  OneForge:
    username: OPTIONAL
    key: KEY-OF-1forge.com
    plan: free
```

startup the fiat-exchange-rates by special yml file.

```bash
docker run -d  --name rate \
-p 8080:8080 \
-v application-prod.yml:/home/www/application-prod.yml \
qyvlik/fiat-exchange-rates:latest
--spring.profiles.active=prod
```

### fiat-exchange-rates optional config

1. `startup.initRate`, type is `boolean`, if true, app will fetch the rate data when app startup.
2. `spring.profiles.active`: the value as follow: `scheduling`, `test`, `prod`, `dev`, the `scheduling` will fetch the rate every hour.

## api

### rates

```bash
curl localhost:8080/api/v1/pub/rate/rates
```

response as follow:

```json
{
  "result":[
    {
      "base": "USD",
      "provider": "CurrencyLayer",
      "quote": "EUR",
      "rate": 0.871405,
      "ts": 1538994546000
    },
    ...
  ]
}
```

### rate of base

```bash
curl localhost:8080/api/v1/pub/rate/usd
```

response as follow:

```json
{
  "result":[
    {
      "base": "USD",
      "provider": "CurrencyLayer",
      "quote": "EUR",
      "rate": 0.871405,
      "ts": 1538994546000
    },
    ...
  ]
}
```

### rate of base and quote

```bash
curl localhost:8080/api/v1/pub/rate/usd/cny
```

response as follow:

```json
{
  "result":[
    {
      "base": "USD",
      "provider": "CurrencyLayer",
      "quote": "CNY",
      "rate": 6.920981,
      "ts": 1538994546000
    },
    ...
  ]
}
```