package com.finleap.weather.forecast.api.dto.error;


/**
 * Created by Dogukan Duman on 9.11.2018.
 */
public class JsonParseException  extends RuntimeException{

    public JsonParseException() { }

    public JsonParseException(String message) {
        super(message);
    }

    public JsonParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public static JsonParseException build() {
        return new JsonParseException("Error occurred when parsing json data");
    }
}

