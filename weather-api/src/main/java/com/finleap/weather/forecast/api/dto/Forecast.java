package com.finleap.weather.forecast.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Dogukan Duman on 9.11.2018.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "main")
public class Forecast {

    @JsonProperty("temp")
    private Float temp;

    @JsonProperty("temp_min")
    private Float tempMin;

    @JsonProperty("temp_max")
    private Float tempMax;

    @JsonProperty("pressure")
    private Float pressure;

    @JsonProperty("sea_level")
    private Float seaLevel;

    @JsonProperty("grnd_level")
    private Float grndLevel;

    @JsonProperty("humidity")
    private Float humidity;

    @JsonProperty("temp_kf")
    private Float tempKf;

    /**For testing*/
    public Forecast(float temp, float pressure) {
        this.temp = temp;
        this.pressure = pressure;
    }
}
