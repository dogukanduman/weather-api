package com.finleap.weather.forecast.server;

import com.finleap.weather.forecast.api.ApiCaller;
import com.finleap.weather.forecast.api.FiveDayWeatherForecastApi;
import com.finleap.weather.forecast.api.dto.TemporalForecast;
import com.finleap.weather.forecast.api.dto.error.JsonParseException;
import com.finleap.weather.forecast.api.dto.error.RestException;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Stream;

/**
 * Created by Dogukan Duman on 14.11.2018.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApiTest {


    @Autowired
    ApiCaller apiCaller;

    @Autowired
    FiveDayWeatherForecastApi fiveDayWeatherForecastApi;

    @Test(expected = JsonParseException.class)
    public void jsonParseExceptionWrongDateTest() {
        String unformattedData = "{\"name\":\"john\",\"lastName\":\"Doe\"}";
        fiveDayWeatherForecastApi.mapResponseToTemporalForecast(unformattedData);
    }

    @Test(expected = JsonParseException.class)
    public void jsonParseExceptionWrongFormatTest() {
        String unformattedData = "dummy:dummy";
        TemporalForecast TemporalForecast = fiveDayWeatherForecastApi.mapResponseToTemporalForecast(unformattedData);
    }

    @Test(expected = RestException.class)
    public void restExceptionExceptionTest() {

        apiCaller.call("Berlin", "http:dummy");
    }

    @Test
    public void apiCallerTest() {


        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity = new HttpEntity<Object>(headers);

        String url =  apiCaller.buildWeatherApiUrl("Berlin");
        ResponseEntity<String> responseEntity =  restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);

        Assert.assertThat(responseEntity.getStatusCode(), Matchers.is(HttpStatus.OK));

    }


}
