# fiat-exchange-rates

fiat exchange rates

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
      "base":"BCH",
      "provider":"OneForge",
      "quote":"HKD",
      "rate":4000.4569,
      "ts":1538924537000
    },
    ...
  ]
}
```
