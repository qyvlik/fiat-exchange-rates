package space.qyvlik.fait.exchange.rates.provider.currencylayer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

// http://apilayer.net/api/live
public class CurrencyLayerQuotes implements Serializable {
    private Boolean success;
    private String terms;
    private String privacy;
    private Long timestamp;
    private String source;
    private Map<String, BigDecimal> quotes;
    private String error;

    public CurrencyLayerQuotes() {

    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Map<String, BigDecimal> getQuotes() {
        return quotes;
    }

    public void setQuotes(Map<String, BigDecimal> quotes) {
        this.quotes = quotes;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
