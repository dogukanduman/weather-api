package com.finleap.weather.forecast.api.imp;


import com.finleap.weather.forecast.api.ApiCaller;
import com.finleap.weather.forecast.api.dto.error.RestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ApiCallerImp implements ApiCaller {

    private static final Logger logger = LoggerFactory.getLogger(ApiCallerImp.class);

    @Value("${openweather.appid:b6907d289e10d714a6e88b30761fae22}")
    private String appId;

    @Value("${openweather.url:https://openweathermap.org/data/2.5/forecast}")
    private String openWeatherMapUrl;

    @Value("${openweather.unit:metric}")
    private String unit;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){
        return restTemplateBuilder.build();
    }

    @Autowired
    private RestTemplate restTemplate;


    public String call(String city){
        String url = buildWeatherApiUrl(city);
        return call(city,url);
    }

    public String call(String city, String url) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> responseEntity = null;

        logger.debug("Calling url:"+url);

        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);

            if (responseEntity == null) {
                throw RestException.build(url, HttpStatus.NOT_ACCEPTABLE.value());
            }

        } catch (HttpStatusCodeException exception) {
            logger.error("Error occurred "+exception);
            throw RestException.build(url, responseEntity.getStatusCodeValue());
        }catch (IllegalArgumentException exception){
            logger.error("Error occurred "+exception);
            throw RestException.build(url, 500);
        }
        logger.debug("Request successfully completed..");
        return responseEntity.getBody().toString();
    }



    public String buildWeatherApiUrl(String city) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(openWeatherMapUrl)
                .queryParam("q", city)
                .queryParam("appid", appId)
                .queryParam("units", unit);
        return builder.toUriString();
    }

}
