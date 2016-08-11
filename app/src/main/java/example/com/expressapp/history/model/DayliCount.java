package example.com.expressapp.history.model;

/**
 * Created by xyj64 on 2016/8/10.
 */
public class DayliCount {
    private int dailyNum;
    private float dailyWeight;
    private int dailyDelivered;
    private int dailyUndelivered;

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

    public void setDailyNum(int dailynum)
    {
        dailyNum=dailynum;
    }

    public void setDailyWeight(float dailyweight)
    {
        dailyWeight=dailyweight;
    }

    public void setDailyDeliverd(int dailydelivered)
    {
        dailyDelivered=dailydelivered;
    }

    public void setDailyUndeliverd(int dailyundelivered)
    {
        dailyUndelivered=dailyundelivered;
    }
}
