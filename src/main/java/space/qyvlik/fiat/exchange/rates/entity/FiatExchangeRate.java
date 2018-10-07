package space.qyvlik.fiat.exchange.rates.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class FiatExchangeRate implements Serializable {
    private String quote;
    private String base;
    private BigDecimal rate;
    private Long ts;                // timestamp
    private String provider;

    public FiatExchangeRate() {

    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    public String toString() {
        return "(" + base + "/" + quote + ":" + rate + "," + ts + "," + provider + ")";
    }
}
