package space.qyvlik.fait.exchange.rates.entity.response;

import java.io.Serializable;

public class ResponseError implements Serializable {
    private Integer code;
    private String message;

    public ResponseError() {

    }

    public ResponseError(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
