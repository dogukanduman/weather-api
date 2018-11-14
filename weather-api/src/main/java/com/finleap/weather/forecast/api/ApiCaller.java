package com.finleap.weather.forecast.api;

public interface ApiCaller {

     /**
      * Calls the weather api
      * @param city
      * @return
      */
     String call(String city);

     /**
      * Calls the weather api
      * @param city
      * @param url
      * @return
      */
    String call(String city, String url);

    /**
     * Build Weather Api url with required parameters
     * @param city
     * @return
     */
    String buildWeatherApiUrl(String city);
}
