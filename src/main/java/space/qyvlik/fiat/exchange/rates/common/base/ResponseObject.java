package space.qyvlik.fiat.exchange.rates.common.base;

import java.io.Serializable;

public class ResponseObject<T> implements Serializable {
    private T result;
    private ResponseError error;

    public ResponseObject() {

    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public ResponseError getError() {
        return error;
    }

    public void setError(ResponseError error) {
        this.error = error;
    }
}
