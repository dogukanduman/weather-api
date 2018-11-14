package com.finleap.weather.forecast.server.service;

import java.util.Date;

/**
 * Created by Dogukan Duman on 13.11.2018.
 */

public interface DateService{

     /**
      * Given is date is in night
      * @param date
      * @return
      */
     Boolean isInNight(Date date);

     /**
      * Given is date is in day
      * @param date
      * @return
      */
     Boolean isInDay(Date date);

}