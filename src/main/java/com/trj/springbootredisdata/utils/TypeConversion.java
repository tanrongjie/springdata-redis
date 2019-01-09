package com.trj.springbootredisdata.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: sms
 * @BelongsPackage: com.bxzt.sms.utils
 * @Author: 谭荣杰
 * @CreateTime: 2018-09-29 15:29
 * @Description: 时间转换工具类
 */
public class TypeConversion {

    private static SimpleDateFormat simpleDateFormat;

    /**
     * 返回当前时间字符串
     *
     * @return
     */
    public static String getNowTimeStr() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //精确到毫秒
        return fmt.format(new Date());
    }

    /**
     * String类型转换成date类型
     *
     * @param date
     * @return
     */
    public static Date conversionStringToDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            System.out.println("日期转换错误！" + e.getMessage());
        }
        return null;
    }

    /**
     * String类型转换成date类型,只返回年月
     *
     * @param date
     * @return
     */
    public static Date conversionStringToDateRemoveMS(String date) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            System.out.println("日期转换错误！");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Date类型转换成String类型
     *
     * @param date
     * @return
     */
    public static String conversionDateToString(Date date) {
        if (null == date) {
            return "";
        }
        try {
            if (null == simpleDateFormat) {
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            }
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            System.out.println("Date类型转换成字符串异常");
        }
        return "";
    }

    /**
     * sqlDate转换成utilDate
     *
     * @param date
     * @return
     */
    public static Date conversionSqlDateToDate(java.sql.Date date) {
        return new Date(date.getTime());
    }

    /**
     * 截取日期字符串，获取前六位数字，只保留数字
     *
     * @param time
     * @return
     */
    public static String subDate(String time) {
        int i = time.lastIndexOf(" ");
        if (i > 0) {
            try {
                time = time.substring(0, i).replaceAll("-", "");
            } catch (Exception e) {
                System.out.println(time);
            }
        } else {
            try {
                time = time.replaceAll("-", "");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return time;
    }

    /**
     * 校验字符串是否为时间格式
     *
     * @param strDate 时间字符串
     * @return
     */
    public static boolean isValidDate(String strDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            format.setLenient(false);
            format.parse(strDate);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 校验字符串是否为时间格式,包含到秒级的
     *
     * @param strDate 时间字符串
     * @return
     */
    public static boolean isValidDateFull(String strDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            format.setLenient(false);
            format.parse(strDate);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 校验excel里面日期格式
     * @param strDate
     * @return
     */
    public static boolean isValidDateExcel(String strDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        try {
            format.setLenient(false);
            format.parse(strDate);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    /**
     * minute参数为正整数时,返回的时间为当前时间之后的时间字符串,
     * minute参数为负整数时,返回的时间为当期那时间之前的时间字符串。
     *
     * @param minute 分钟
     * @return 时间字符串
     */
    public static String backAfterTime(Integer minute) {
        if (null == minute) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar beforeTime = Calendar.getInstance();
        beforeTime.add(Calendar.MINUTE, minute);// 3分钟之前的时间
        Date beforeD = beforeTime.getTime();
        return sdf.format(beforeD);
    }


    /**
     * 计算两个时间的天数差
     * 两个时间必须不同天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1, Date date2) {
        if (null == date1 || null == date2) {
            return 0;
        }
        return (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
    }

    /**
     * 计算相同天时间的秒差
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentSecondByMillisecond(Date date1, Date date2) {
        if (null == date1 || null == date2) {
            return 0;
        }
        return (int) ((date2.getTime() - date1.getTime()) / 1000);
    }

    /**
     * 获取一周时间集合
     * @param dBegin
     * @param dEnd
     * @return
     */
    public static List<Date> findDates(Date dBegin, Date dEnd) {
        List<Date> lDate = new ArrayList<>();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }

    /**
     * 获取当前周的第一天和最后一天
     * @param date
     * @return
     */
    public static String getTimeInterval(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {     // 按照国外的日期,1是星期天
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }

        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);

        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);

        // 设置当前时间与这周第一天的时间差
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String imptimeBegin = sdf.format(cal.getTime());
        cal.add(Calendar.DATE, 6);
        String imptimeEnd = sdf.format(cal.getTime());
        return imptimeBegin + "," + imptimeEnd;
    }
}
