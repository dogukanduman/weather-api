package com.finleap.weather.forecast.api.dto.error;

/**
 * Created by Dogukan Duman on 9.11.2018.
 */
public class RestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RestException() {
    }

    public RestException(String message) {
        super(message);
    }

    public RestException(String message, Throwable cause) {
        super(message, cause);
    }

    public static RestException build(String url,int statusCode) {
        return new RestException("Error occurred calling "+url +" statusCode:"+statusCode);
    }
}