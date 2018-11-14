package com.finleap.weather.forecast.api;

import com.finleap.weather.forecast.api.dto.ForecastWithDate;
import com.finleap.weather.forecast.api.dto.TemporalForecast;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Dogukan Duman on 8.11.2018.
 */
public interface FiveDayWeatherForecastApi {

    /**
     * Calls Weather Api with city name and group the results by date
     * @param city
     * @return
     */
    Map<Date, List<ForecastWithDate>> get(String city);

    TemporalForecast mapResponseToTemporalForecast(String response);
}
