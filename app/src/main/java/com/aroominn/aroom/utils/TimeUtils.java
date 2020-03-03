package com.aroominn.aroom.utils;

import com.aroominn.aroom.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.rong.imkit.RongIM;

public class TimeUtils {
    public static TimeUtils getInstance() {


        return SingletonHolder.timeUtils;
    }

    static class SingletonHolder {
        static TimeUtils timeUtils = new TimeUtils();

        SingletonHolder() {
        }
    }


    public String formatTime(String time) {


        String result = "今天";
        if (time == null || time.equals("")) {
            return result;
        }
        Date date = null;
        /*当天日期显示当天几点
         * 否则 显示当年的 月份日期
         * 否则 显示哪一年*/

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            date = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();

        }
        Calendar calendar = Calendar.getInstance();
        Calendar now = Calendar.getInstance();

        calendar.setTime(date);


        /*是今年的 显示几月几日*/
        if (calendar.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
            result = calendar.get(Calendar.MONTH) + 1 + "月" + calendar.get(Calendar.DATE) + "日";
            if (calendar.get(Calendar.DATE) == now.get(Calendar.DATE)) {
                result = "今天" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
            }
            if (calendar.get(Calendar.DATE) + 1 == now.get(Calendar.DATE)) {
                result = "昨天" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
            }
        } else {
            result = calendar.get(Calendar.YEAR) + "年";
        }

        return result;
    }

}
