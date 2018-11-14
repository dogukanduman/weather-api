package com.finleap.weather.forecast.server.controller;


import com.finleap.weather.forecast.server.dto.AveragesDto;
import com.finleap.weather.forecast.server.service.AveragesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Dogukan Duman on 8.11.2018.
 */

@RestController("averagesControllerV1")
@RequestMapping("/v1/averages/{city}")
public class AveragesController {

    private static final Logger logger = LoggerFactory.getLogger(AveragesController.class);

    @Autowired
    private AveragesService averagesService;

    /**
     * Endpoint for average of next three days day,night and pressure
     * @param city
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> averages(@PathVariable String city) {

        logger.debug("Averages has called with city:{}",city);

        List<AveragesDto> averagesDtoList = averagesService.calculateCityAverages(city);

        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<>(averagesDtoList,responseHeaders, HttpStatus.OK);
    }
}


