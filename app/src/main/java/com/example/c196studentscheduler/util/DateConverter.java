package com.example.c196studentscheduler.util;

import androidx.room.TypeConverter;

import java.util.Date;
/**
 * Chris Richardson
 * C196
 * Student ID #000895452
 */
public class DateConverter {

    @TypeConverter
    public static Date toDate(Long timeStamp) {
        return timeStamp == null ? null : new Date(timeStamp);
    }

    @TypeConverter
    public static Long toTimeStamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
