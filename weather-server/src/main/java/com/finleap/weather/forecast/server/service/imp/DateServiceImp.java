package com.finleap.weather.forecast.server.service.imp;


import com.finleap.weather.forecast.server.service.DateService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Dogukan Duman on 13.11.2018.
 */
@Service
public class DateServiceImp implements DateService {


    @Value("#{'${openweather.averages.daily:6,18}'.split(',')}")
    private List<String> dailyTemporalRange;

    @Value("#{'${openweather.averages.nightly:18,6}'.split(',')}")
    private List<String> nightlyTemporalRange;


    public Boolean isInNight(Date date) {

        return isIn(date, nightlyTemporalRange.get(0), nightlyTemporalRange.get(1));
    }


    public Boolean isInDay(Date date) {

        return isIn( date, dailyTemporalRange.get(0), dailyTemporalRange.get(1));
    }

    private Boolean isIn(Date date, String min, String max ) {

        int minInt = Integer.parseInt(min);
        int maxInt = Integer.parseInt(max);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);

            if( maxInt>minInt){
                if((hour>minInt)&&(hour<maxInt)){
                    return true;
                }else{
                    return false;
                }
            }else{
                if((hour>=minInt)||(hour<maxInt)){
                    return true;
                }else{
                    return false;
                }
            }
    }

}
