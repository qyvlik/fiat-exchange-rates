package space.qyvlik.fait.exchange.rates.rest;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import space.qyvlik.fait.exchange.rates.base.BaseService;
import space.qyvlik.fait.exchange.rates.data.FiatExchangeRateService;
import space.qyvlik.fait.exchange.rates.entity.FiatExchangeRate;
import space.qyvlik.fait.exchange.rates.entity.response.RateListResponse;
import space.qyvlik.fait.exchange.rates.entity.response.ResponseError;

import java.util.List;

@RestController
public class FiatExchangeRateController extends BaseService {

    @Autowired
    private FiatExchangeRateService fiatExchangeRateService;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "api/v1/pub/rate/rates",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String rateList() {
        RateListResponse response = new RateListResponse();
        try {
            List<FiatExchangeRate> rateList = fiatExchangeRateService.findFiatExchangeRateList();
            response.setResult(rateList);
        } catch (Exception e) {
            response.setError(new ResponseError(500, e.getMessage()));
        }

        return JSON.toJSONString(response);
    }
}
