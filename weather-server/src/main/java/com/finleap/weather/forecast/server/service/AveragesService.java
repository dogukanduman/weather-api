package com.finleap.weather.forecast.server.service;

import com.finleap.weather.forecast.api.dto.ForecastWithDate;
import com.finleap.weather.forecast.server.dto.AveragesDto;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Dogukan Duman on 8.11.2018.
 */
public interface AveragesService {

    /**
     * Average of daily (06:00 – 18:00) and nightly (18:00 – 06:00) temperatures in
     * Celsius for the next 3 days from today’s date.
     * Average of daily pressure for the next 3 days from today’s date.
     * @param city
     * @return
     */
    List<AveragesDto> calculateCityAverages(String city) ;

    /**
     * Calculate averages if forecastMapByDay is given
     * @param forecastMapByDay
     * @return
     */
    List<AveragesDto> calculateAverages(Map<Date, List<ForecastWithDate>> forecastMapByDay);


}
