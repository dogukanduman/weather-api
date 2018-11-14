package com.finleap.weather.forecast.api.imp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finleap.weather.forecast.api.ApiCaller;
import com.finleap.weather.forecast.api.FiveDayWeatherForecastApi;
import com.finleap.weather.forecast.api.dto.ForecastWithDate;
import com.finleap.weather.forecast.api.dto.TemporalForecast;
import com.finleap.weather.forecast.api.dto.error.JsonParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Dogukan Duman on 8.11.2018.
 */
@Service
public class FiveDayWeatherForecastApiImp implements FiveDayWeatherForecastApi {

    private static final Logger logger = LoggerFactory.getLogger(FiveDayWeatherForecastApiImp.class);

    @Autowired
    private ApiCaller apiCaller;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Override
    public Map<Date, List<ForecastWithDate>> get(String city) {



        /**Call Weather api */
        String response = apiCaller.call(city);

        /**Map it string to TemporalForecast*/
        TemporalForecast temporalForecast = mapResponseToTemporalForecast(response);

        /**Group the TemporalForecast by dates*/
        return groupByDay(temporalForecast);
    }

    /**Mapper */
    public TemporalForecast mapResponseToTemporalForecast(String response) {

        logger.debug("Request response is mapping to TemporalForecast..");
        TemporalForecast temporalForecast;
        try {
            temporalForecast =  objectMapper.readValue(response, TemporalForecast.class);

        } catch (IOException e) {
            logger.error("Error occurred "+e);
            throw JsonParseException.build();
        }
        if(temporalForecast==null){
            throw JsonParseException.build();
        }
        if(temporalForecast.getCode()==null){
            throw JsonParseException.build();
        }

        return temporalForecast;
    }
    /**Grouper */
    private Map<Date, List<ForecastWithDate>> groupByDay(TemporalForecast temporalForecast) {

        logger.debug("TemporalForecast is grouped by date");
        List<ForecastWithDate> forecastList = temporalForecast.getForecastList();

        Map<Date, List<ForecastWithDate>> forecastMapByDay = forecastList.stream()
                .sorted(Comparator.comparing(ForecastWithDate::getDate))
                .collect(Collectors.groupingBy(s -> getDayFromDate(s.getDate()),LinkedHashMap::new, Collectors.toList()));

        return forecastMapByDay;
    }

    /**Set related date's hour 00:00:00 */
    private Date getDayFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
}


