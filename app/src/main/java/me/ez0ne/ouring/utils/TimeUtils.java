package me.ez0ne.ouring.utils;

import java.util.Date;

/**
 * Created by Ezreal.Wan on 2016/4/19.
 * 时间 从Unix时间戳到 年 月 日
 */
public class TimeUtils {
    public TimeUtils() {
    }

    public int getYear(Date unixdate) {
        return unixdate.getYear() + 1900;
    }

    public int getMonth(Date unixdate) {
        return unixdate.getMonth() + 1;
    }

    public int getDay(Date unixdate) {
        return unixdate.getDate();
    }


}
