package example.com.expressapp.searchinformation.model;

import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by xyj64 on 2016/8/8.
 */
public class DataServiceHelper {
    static public List<ExpressInfo> setData(String userInfo,List<ExpressInfo> expressInfoList)
    {
        expressInfoList.clear();
        String data[]=userInfo.split("##");
        for(int i=0;i<data.length;i++)
        {
            String detailInfo[]=data[i].split("#");
            ExpressInfo expressInfo=new ExpressInfo();
            expressInfo.setIdNum(detailInfo[0]);
            expressInfo.setUpDataTime(detailInfo[1]);
            expressInfo.setReceiverName(detailInfo[2]);
            expressInfo.setReceiverAddress(detailInfo[3]);
            expressInfo.setReceiverPhone(detailInfo[4]);
            expressInfo.setIsDelivered(detailInfo[5]);
            expressInfoList.add(expressInfo);
        }
        return expressInfoList;
    }
}
