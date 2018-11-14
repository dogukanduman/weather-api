package com.finleap.weather.forecast.server;

import com.finleap.weather.forecast.api.dto.Forecast;
import com.finleap.weather.forecast.api.dto.ForecastWithDate;
import com.finleap.weather.forecast.server.dto.AveragesDto;
import com.finleap.weather.forecast.server.service.AveragesService;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Dogukan Duman on 13.11.2018.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public  class AverageServiceTest {

    @Autowired
    public AveragesService averageService;

    @Test
    public void calculateAveragesTest() throws Exception {

        Integer[] tempList={10,15,20,15,10,5};
        Integer[] pressureList={1000,1200,1200,1200,1000,1000};

        float averageTempDay =average(tempList,0,3);
        float averageTempNight =average(tempList,3,6);
        float averagePressure = average(pressureList,0,6);

        /**
         *  Generate two days mock data
         *  3 entries for each day and night
         *  Total 12 entries
         */
        Map<Date, List<ForecastWithDate>>  forecastMapByDay  =  testDataGenerator(tempList,pressureList);

        List<AveragesDto> result =  averageService.calculateAverages(forecastMapByDay);

        Assert.assertThat(  result.get(0).getDaily(), Matchers.is(averageTempDay));
        Assert.assertThat(  result.get(0).getNightly(), Matchers.is(averageTempNight));
        Assert.assertThat(  result.get(1).getDaily(), Matchers.is(averageTempDay));
        Assert.assertThat(  result.get(1).getNightly(), Matchers.is(averageTempNight));
        Assert.assertThat(  result.get(0).getPressure(), Matchers.is(averagePressure));
        Assert.assertThat(  result.get(1).getPressure(), Matchers.is(averagePressure));
    }


    private  Map<Date, List<ForecastWithDate>> testDataGenerator(Integer[] tempList,Integer[] pressureList ) throws Exception{

        Map<Date, List<ForecastWithDate>> forecastMapByDay  = new HashMap<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date day1 = sdf.parse("2018-11-15 00:00:00");
        Date day2 = sdf.parse("2018-11-16 00:00:00");

        Date day1d1 = sdf.parse("2018-11-15 09:00:00");
        Date day1d2 = sdf.parse("2018-11-15 12:00:00");
        Date day1d3 = sdf.parse("2018-11-15 15:00:00");

        Date day1n1 = sdf.parse("2018-11-15 18:00:00");
        Date day1n2 = sdf.parse("2018-11-15 21:00:00");
        Date day1n3 = sdf.parse("2018-11-15 01:00:00");


        Date day2d1 = sdf.parse("2018-11-16 09:00:00");
        Date day2d2 = sdf.parse("2018-11-16 12:00:00");
        Date day2d3 = sdf.parse("2018-11-16 15:00:00");

        Date day2n1 = sdf.parse("2018-11-16 18:00:00");
        Date day2n2 = sdf.parse("2018-11-16 21:00:00");
        Date day2n3 = sdf.parse("2018-11-16 01:00:00");



        List<ForecastWithDate> day1List = new ArrayList<>();
        day1List.add(new ForecastWithDate(new Forecast(tempList[0],pressureList[0]),day1d1));
        day1List.add(new ForecastWithDate(new Forecast(tempList[1],pressureList[1]),day1d2));
        day1List.add(new ForecastWithDate(new Forecast(tempList[2],pressureList[2]),day1d3));
        day1List.add(new ForecastWithDate(new Forecast(tempList[3],pressureList[3]),day1n1));
        day1List.add(new ForecastWithDate(new Forecast(tempList[4],pressureList[4]),day1n2));
        day1List.add(new ForecastWithDate(new Forecast(tempList[5],pressureList[5]),day1n3));

        List<ForecastWithDate> day2List = new ArrayList<>();
        day2List.add(new ForecastWithDate(new Forecast(tempList[0],pressureList[0]),day2d1));
        day2List.add(new ForecastWithDate(new Forecast(tempList[1],pressureList[1]),day2d2));
        day2List.add(new ForecastWithDate(new Forecast(tempList[2],pressureList[2]),day2d3));
        day2List.add(new ForecastWithDate(new Forecast(tempList[3],pressureList[3]),day2n1));
        day2List.add(new ForecastWithDate(new Forecast(tempList[4],pressureList[4]),day2n2));
        day2List.add(new ForecastWithDate(new Forecast(tempList[5],pressureList[5]),day2n3));

        forecastMapByDay.put(day1,day1List);
        forecastMapByDay.put(day2,day2List);
        return forecastMapByDay;
    }
    private float average(Integer[] array,int start,int stop){
        Integer sum=0;
        for(int i=start;i<stop;i++){
            sum=sum+array[i];
        }
        return sum/(stop-start);
    }


}