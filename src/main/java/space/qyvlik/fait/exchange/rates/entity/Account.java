package space.qyvlik.fait.exchange.rates.entity;

import java.io.Serializable;

public class Account implements Serializable {
    private String provider;
    private String userName;
    private String accessKey;
    private String plan;                // free, or other

    public Account() {

    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
}
