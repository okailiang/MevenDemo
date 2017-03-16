package com.ele.demo;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;

/**
 * Java8时间处理实例
 *
 * @author oukailiang
 * @create 2017-01-03 下午2:02
 */

public class DateUtils {

    private static void date() {

        Clock clock = Clock.systemUTC();
        LocalDate date = LocalDate.now();
        LocalDate dateFromClock = LocalDate.now(clock);
        LocalTime time = LocalTime.now();
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println("clock = " + clock.instant());
        System.out.println("clock = " + clock.getZone());
        System.out.println("clock = " + clock.millis());
        System.out.println("localdate = " + date);
        System.out.println("dateFromClock = " + dateFromClock);
        System.out.println("localtime = " + time);
        System.out.println("localdateTime = " + dateTime);


    }

    public static void main(String[] args) {
        date();

    }
}
