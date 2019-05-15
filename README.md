# fiat-exchange-rates

fiat exchange rates

## docker

### docker-compose

see [fiat-rate-docker-compose](./docker/fiat-rate/README.md)

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