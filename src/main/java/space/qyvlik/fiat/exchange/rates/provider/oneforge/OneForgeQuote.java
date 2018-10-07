package space.qyvlik.fiat.exchange.rates.provider.oneforge;

import java.io.Serializable;
import java.math.BigDecimal;

public class OneForgeQuote implements Serializable {
    private String symbol;
    private BigDecimal bid;
    private BigDecimal ask;
    private BigDecimal price;
    private Long timestamp;

    public OneForgeQuote() {
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public void setAsk(BigDecimal ask) {
        this.ask = ask;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
