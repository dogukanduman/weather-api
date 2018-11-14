package com.finleap.weather.forecast.server;


import com.finleap.weather.forecast.server.service.DateService;
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
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DateServiceTest {


    @Autowired
    public DateService dateService;

    @Test
    public void isInNight() throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date night = sdf.parse("2018-11-15 19:00:00");
        Boolean isNight = dateService.isInNight(night);

        Assert.assertThat(true, Matchers.is(isNight));

        Date day = sdf.parse("2018-11-13 12:00:00");
        isNight = dateService.isInNight(day);
        Assert.assertThat(false, Matchers.is(isNight));

    }

    @Test
    public void isInDay() throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date day = sdf.parse("2018-11-13 11:00:00");
        Boolean isDay = dateService.isInDay(day);

        Assert.assertThat(true, Matchers.is(isDay));

        Date night = sdf.parse("2018-11-13 17:59:00");
        isDay = dateService.isInNight(night);
        Assert.assertThat(false, Matchers.is(isDay));
    }

}
