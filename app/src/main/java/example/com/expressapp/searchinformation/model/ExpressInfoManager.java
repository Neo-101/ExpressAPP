package example.com.expressapp.searchinformation.model;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by xyj64 on 2016/8/8.
 */

//根据你的信息，在初始化的时候往list中加入货物货物
public class ExpressInfoManager {
    private List<ExpressInfo> expressInfoList;

    public ExpressInfoManager()
    {
        expressInfoList=new LinkedList<>();
    }

    public ExpressInfoManager(String userInfo)
    {
        expressInfoList=DataServiceHelper.InitData(userInfo);
    }

    public void QuicksortbyExpressNo(int start, int end)//调用这个函数两个参数的初始化值为0和expressInfoList.size()
    {
        if(start>=end)
            return;
        int i=start,j=end;
        ExpressInfo expressInfo=expressInfoList.get(start);
        //Log.d("test","ok1");
        while(i<j)
        {
/*按j--方向遍历目标数组，直到比key小的值为止*/
            while(j>i&&expressInfoList.get(j).getExpressNo()>=expressInfo.getExpressNo())
            {
                j--;
            }
            if(i<j)
            {
                expressInfoList.add(i,expressInfoList.get(j));
                expressInfoList.remove(i+1);
/*targetArr[i]已经保存在key中，可将后面的数填入*/
                i++;
            }
/*按i++方向遍历目标数组，直到比key大的值为止*/
            while(i<j&&expressInfoList.get(i).getExpressNo()<=expressInfo.getExpressNo())
/*此处一定要小于等于零，假设数组之内有一亿个1，0交替出现的话，而key的值又恰巧是1的话，那么这个小于等于的作用就会使下面的if语句少执行一亿次。*/
            {
                i++;
            }
            if(i<j)
            {
                expressInfoList.add(j,expressInfoList.get(i));
                expressInfoList.remove(j+1);
/*targetArr[j]已保存在targetArr[i]中，可将前面的值填入*/
                j--;
            }
        }
/*此时i==j*/
        //Log.d("test","ok4");
        expressInfoList.add(i,expressInfo);
        expressInfoList.remove(i+1);

/*递归调用，把key前面的完成排序*/
        this.QuicksortbyExpressNo(start,i-1);


/*递归调用，把key后面的完成排序*/
        this.QuicksortbyExpressNo(j+1,end);

    }

    public void AddExpressInfo(int location,ExpressInfo expressInfo)
    {
        expressInfoList.add(location,expressInfo);
    }

    public void AddExpressInfo(ExpressInfo expressInfo)
    {
        expressInfoList.add(expressInfo);
    }

    public List<ExpressInfo> getExpressInfoList() {
        return expressInfoList;
    }

    public void RemoveExpressInfo(int location)
    {
        expressInfoList.remove(location);
    }

}
