package utils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by 傻不拉几 on 2017/11/30.
 */
public class Date {
    public static Map<String,Object> getDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        Map<String,Object> map = new HashMap<>();
        map.put("year",year);
        map.put("month",month);
        map.put("day",day);
        map.put("hour",hour);
        map.put("minute",minute);
        map.put("date",year+"-"+month+"-"+day);
        return map;
    }


    /**
     * 获取时间的毫秒值
     * @param hour
     * @param minute
     * @return
     */
    public static long getMillisecondValues(int hour,int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取日期的毫秒值
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static long getMillisecondValues(int year,int month,int day){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        calendar.set(year,month - 1,day);
        return calendar.getTimeInMillis();
    }

    /**
     * 返回当前时间的毫秒值
     * @return
     */
    public static long getNowMillisecondValues(){
        return getMillisecondValues((int)getDate().get("hour"),(int)getDate().get("minute"));
    }
    /**
     * 返回当前日期的毫秒值
     * @return
     */
    public static long getNowDateMillisecondValues(){
//        Log.e("当前日期", getDate().get("year") + "-" +getDate().get("month")+ "-" + getDate().get("day"));
        return getMillisecondValues((int) getDate().get("year"), (int) getDate().get("month"),(int) getDate().get("day"));
    }

}
