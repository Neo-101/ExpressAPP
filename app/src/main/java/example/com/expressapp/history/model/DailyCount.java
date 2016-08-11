package example.com.expressapp.history.model;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xyj64 on 2016/8/10.
 */
public class DailyCount {
    private int dailyNum;
    private float dailyWeight;
    private int dailyDelivered;
    private int dailyUndelivered;
    private Date dateTime;

    public DailyCount()
    {
        dailyNum=0;
        dailyWeight=0;
        dailyDelivered=0;
        dailyUndelivered=0;
        dateTime=new Date(0,0,0);
    }

    public DailyCount(int dailynum, int dailyweight, int dailydelivered, int dailyundelivered,Date datetime)
    {
        dailyNum=dailynum;
        dailyWeight=dailyweight;
        dailyDelivered=dailydelivered;
        dailyUndelivered=dailyundelivered;
        dateTime=datetime;
    }

    public int getDailyNum()
    {
        return dailyNum;
    }

    public float getDailyWeight()
    {
        return dailyWeight;
    }

    public int getDailyDelivered()
    {
        return dailyDelivered;
    }

    public int getDailyUndelivered()
    {
        return dailyUndelivered;
    }

    public String getDateTime()
    {
        SimpleDateFormat dateFormat=new SimpleDateFormat("MM/dd");
        return dateFormat.format(dateTime);
    }

    public void setDailyNum(int dailynum)
    {
        dailyNum=dailynum;
    }

    public void setDailyWeight(float dailyweight)
    {
        dailyWeight=dailyweight;
    }

    public void setDailyDelivered(int dailydeliverd)
    {
        dailyDelivered=dailydeliverd;
    }

    public void setDailyUndelivered(int dailyundeliverd)
    {
        dailyUndelivered=dailyundeliverd;
    }

    /**
     * 用Date数据对Date数据进行赋值，Date数据的初始化初始化方法有用3或者5或者6个int型的数据
     * （注意，月份8月初始化值（-1）用7，年2016年初始化的值用（-1900）116）
     * @param datetime
     */
    public void setDateTime(Date datetime)
    {
        dateTime =datetime;
    }

    /**
     * 用字符串对Date数据进行赋值，字符串的格式必须为yyyy-MM-dd HH:mm:ss
     * @param datetime
     */
    public void setDatetime(String datetime)
    {
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateTime = sdf.parse(datetime);
        }
        catch (ParseException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
