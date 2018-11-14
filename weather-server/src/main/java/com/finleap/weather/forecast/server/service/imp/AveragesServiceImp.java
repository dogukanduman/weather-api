package com.finleap.weather.forecast.server.service.imp;

import com.finleap.weather.forecast.api.FiveDayWeatherForecastApi;
import com.finleap.weather.forecast.api.dto.ForecastWithDate;
import com.finleap.weather.forecast.server.dto.AveragesDto;
import com.finleap.weather.forecast.server.service.AveragesService;
import com.finleap.weather.forecast.server.service.DateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Dogukan Duman on 8.11.2018.
 */
@Service
public class AveragesServiceImp implements AveragesService {

    private static final Logger logger = LoggerFactory.getLogger(AveragesServiceImp.class);

    @Autowired
    private DateService dateService;

    @Autowired
    private FiveDayWeatherForecastApi fiveDayWeatherForecastApi;

    @Override
    public List<AveragesDto> calculateCityAverages(String city) {

        logger.debug("Calling fiveDayWeatherForecast of Weather Api");
        /** Get five days Weatherforecast from api grouped by date*/
        Map<Date, List<ForecastWithDate>> forecastMapByDay = fiveDayWeatherForecastApi.get(city);

        logger.debug("Calculating averages..");
        /** Calculate list of next three days averages*/
        return calculateAverages(forecastMapByDay).subList(0,3);
    }


    public List<AveragesDto> calculateAverages(Map<Date, List<ForecastWithDate>> forecastMapByDay){

        List<AveragesDto> listAveragesDtos = new ArrayList<>();

        for (Map.Entry<Date, List<ForecastWithDate>> entry : forecastMapByDay.entrySet())
        {
            /** get key as day*/
            Date date= entry.getKey();

            /** get forecast entries which belong that day */
            List<ForecastWithDate> forecastWithDates = entry.getValue();

            /** Calculate Pressure of that day*/
            Float averagePressure = calculateAveragePressure(forecastWithDates);

            /**Divide day into two 06:00-18:00 18:00-06:00 */
            Map<Boolean, List<ForecastWithDate>> dayAndNightMap = divideByDayAndNight(forecastWithDates);

            /**Get day */
            List<ForecastWithDate> dayList = dayAndNightMap.get(true);
            /**Get night */
            List<ForecastWithDate> nightList = dayAndNightMap.get(false);

            /** calculate day*/
            Float averageDay = calculateAverageTemp(dayList);

            /** calculate night*/
            Float averageNight = calculateAverageTemp(nightList);
            AveragesDto average = new AveragesDto(averageDay,averageNight,averagePressure,date);
            logger.debug("Calculated average:"+average);
            listAveragesDtos.add(average);
        }
        return listAveragesDtos;
    }

    /**
     * Divide day into two
     * @param forecastWithDates
     * @return Day and Night temperature entries
     */
    private Map<Boolean, List<ForecastWithDate>> divideByDayAndNight(List<ForecastWithDate> forecastWithDates) {

        Map<Boolean, List<ForecastWithDate>> dayAndNightMap = forecastWithDates.stream()
                .collect(Collectors.partitioningBy(forecastWithDate -> dateService.isInDay(forecastWithDate.getDate())));

        return dayAndNightMap;
    }

    /**
     * Calculate average of temperature using entry list
     * @param forecastWithDates
     * @return
     */
    private float calculateAverageTemp(List<ForecastWithDate> forecastWithDates) {

        double average = forecastWithDates.stream()
                .mapToDouble(s -> s.getForecast().getTemp())
                .average()
                .orElse(Float.NaN);

        return (float) average;
    }

    /**
     * Calculate average of pressure using entry list
     * @param forecastWithDates
     * @return
     */
    private float calculateAveragePressure(List<ForecastWithDate> forecastWithDates) {

        double average = forecastWithDates.stream()
                .mapToDouble(s -> s.getForecast().getPressure())
                .average()
                .orElse(Float.NaN);

        return (float) average;
    }




}