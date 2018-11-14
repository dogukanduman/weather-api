package com.finleap.weather.forecast.server.dto;

import lombok.Data;

/**
 * Created by Dogukan Duman on 8.11.2018.
 */
@Data
public class ErrorDetail {

    private String title;
    private int status;
    private String detail;
    private long timeStamp;
}