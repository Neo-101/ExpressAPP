package example.com.expressapp.history.model;

import android.util.Log;

/**
 * Created by xyj64 on 2016/8/11.
 */
public class HistoryInfoServiceHelper {
    static public DailyCount[] setData(String userInfo)
    {
        DailyCount[] weeklyInfo=new DailyCount[7];
        String data[]=userInfo.split("##");
        for(int i=0;i<data.length;i++) {
            String detailInfo[] = data[i].split("#");
            weeklyInfo[i]=new DailyCount();
            if(detailInfo.length<6) continue;
            weeklyInfo[i].setDailyNum(Integer.parseInt(detailInfo[0]));
            weeklyInfo[i].setDailyDelivered(Integer.parseInt(detailInfo[1]));
            weeklyInfo[i].setDailyUndelivered(Integer.parseInt(detailInfo[2]));
            if(detailInfo[3].equals("null"))
                weeklyInfo[i].setDailyWeight(0f);
            else
                weeklyInfo[i].setDailyWeight(Float.valueOf(detailInfo[3]));
            weeklyInfo[i].setDatetime(detailInfo[5]);
        }
        return weeklyInfo;
    }
}
