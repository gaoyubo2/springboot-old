package cn.cestc.os.desktop.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils
{

    public static String dateToStr(Date date)
    {

        if (date == null) date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String datestrToStr(String datestr)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String returnstr = "";
        try
        {
            returnstr = sdf.format(sdf.parse(datestr));
        } catch (ParseException e)
        {
            System.out.println("日期格式转化失败！");
            e.printStackTrace();
        }
        return returnstr;
    }

    public static Date strToDate(String str)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        /*System.out.println("转换时间:"+str);*/
        Date date = null;
        if (str == null || "".equals(str))
        {
            return date;
        }
        try
        {
            date = sdf.parse(str);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        return date;
    }

    public static String getDateByFormae(String format)
    {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static void main(String[] args)
    {
        System.out.println(new Date().getTime());
        //System.out.println(getDateByFormae("yyyyMMdd"));
    }
}
