package com.finleap.weather.forecast.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by Dogukan Duman on 9.11.2018.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown =true)
public class TemporalForecast {

    @JsonProperty(value="cod")
    private String code;

    @JsonProperty(value="message")
    private String message;

    @JsonProperty(value="list")
    private List<ForecastWithDate> forecastList;

    @JsonProperty(value="cnt")
    private Integer cnt;



}
