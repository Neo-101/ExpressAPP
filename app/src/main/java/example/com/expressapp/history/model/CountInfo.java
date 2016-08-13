package example.com.expressapp.history.model;

import org.eazegraph.lib.charts.BarChart;

/**
 * Created by lxs on 2016/5/17.
 */
public class CountInfo {
    private DailyCount[] weeklyCount=new DailyCount[7];

    public CountInfo()
    {
        for(int i=0;i<7;i++)
        {
            weeklyCount[i]=new DailyCount();
        }
    }

    public CountInfo(String userinfo)
    {
        weeklyCount=HistoryInfoServiceHelper.setData(userinfo);
    }

    public DailyCount getDailyCount(int i)
    {
        return weeklyCount[i];
    }

    public int getMaxDailyNum()
    {
        int maxDailyNum=0;
        for(int i=0;i<7;i++)
        {
            if(maxDailyNum<weeklyCount[i].getDailyNum())
                maxDailyNum=weeklyCount[i].getDailyNum();
        }
        return maxDailyNum;
    }
}
