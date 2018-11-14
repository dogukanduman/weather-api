package com.finleap.weather.forecast.server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * Created by Dogukan Duman on 9.11.2018.
 */
@Data
@AllArgsConstructor
public class AveragesDto {

    private Float daily;
    private Float nightly;
    private Float pressure;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;


}
