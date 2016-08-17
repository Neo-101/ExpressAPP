package example.com.expressapp.searchinformation.model;


import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by xyj64 on 2016/8/8.
 */

//根据你的信息，在初始化的时候往list中加入货物货物
public class ExpressInfoManager implements Serializable{
    private List<ExpressInfo> expressInfoList;

    public ExpressInfoManager()
    {
        expressInfoList=new LinkedList<>();
    }

    public void setExpressInfoList(String userInfo)
    {
        expressInfoList=DataServiceHelper.setData(userInfo,expressInfoList);
    }

    /**
     * 按照快递单号来排序
     * 调用这个函数两个参数的初始化值为0和expressInfoManager.getExpressInfoList().size()-1
     * @param start
     * @param end
     */
    public void quickSortbyIdNum(int start, int end)
    {

        if(start>=end)
            return;
        int i=start,j=end;
        ExpressInfo expressInfo=expressInfoList.get(start);
        //Log.d("test","ok1");
        while(i<j)
        {
/*按j--方向遍历目标数组，直到比key小的值为止*/
            while(j>i&&expressInfoList.get(j).getIdNum().compareTo(expressInfo.getIdNum())>=0)
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
            while(i<j&&expressInfoList.get(i).getIdNum().compareTo(expressInfo.getIdNum())<=0)
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
        this.quickSortbyIdNum(start,i-1);


/*递归调用，把key后面的完成排序*/
        this.quickSortbyIdNum(j+1,end);

    }

    /**
     * 按照接货时间来排序
     * 调用这个函数两个参数的初始化值为0和expressInfoManager.getExpressInfoList().size()-1
     * @param start
     * @param end
     */
    public void quickSortbyUpDataTime(int start, int end)
    {
        if(start>=end)
            return;
        int i=start,j=end;
        ExpressInfo expressInfo=expressInfoList.get(start);
        //Log.d("test","ok1");
        while(i<j)
        {
/*按j--方向遍历目标数组，直到比key小的值为止*/
            while(j>i&&expressInfoList.get(j).getUpDataTime().compareTo(expressInfo.getUpDataTime())>=0)
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
            while(i<j&&expressInfoList.get(i).getUpDataTime().compareTo(expressInfo.getUpDataTime())<=0)
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
        this.quickSortbyUpDataTime(start,i-1);


/*递归调用，把key后面的完成排序*/
        this.quickSortbyUpDataTime(j+1,end);

    }
    /**
     * 按照是否已经送达来排序，没送达的排在前面
     * 调用这个函数两个参数的初始化值为0和expressInfoManager.getExpressInfoList().size()-1
     * @param start
     * @param end
     */
    public void quickSortbyIsDelivered(int start, int end)
    {
        if(start>=end)
            return;
        int i=start,j=end;
        ExpressInfo expressInfo=expressInfoList.get(start);
        //Log.d("test","ok1");
        while(i<j)
        {
/*按j--方向遍历目标数组，直到比key小的值为止*/
            while(j>i&&booleanCompared(expressInfoList.get(j).getIsDelivered(),expressInfo.getIsDelivered()))
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
            while(i<j&&booleanCompared(expressInfo.getIsDelivered(),expressInfoList.get(i).getIsDelivered()))
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
        this.quickSortbyIsDelivered(start,i-1);


/*递归调用，把key后面的完成排序*/
        this.quickSortbyIsDelivered(j+1,end);

    }

    private boolean booleanCompared(boolean judge1,boolean judge2)//用于boolean值的比较大小，只有第一个是false，第二个是true的情况下才返回false，代表比较小
    {
        if(!judge1&&judge2)
            return false;
        else
            return true;
    }

    /**
     * 往队列里面加上一个属性，location代表在哪个位置加。
     * @param expressInfo
     */
    public void addExpressInfo(int location, ExpressInfo expressInfo)
    {
        expressInfoList.add(location,expressInfo);
    }

    /**
     * 往队列里面加上一个属性
     * @param expressInfo
     */
    public void addExpressInfo(ExpressInfo expressInfo)
    {
        expressInfoList.add(expressInfo);
    }

    public List<ExpressInfo> getExpressInfoList()//获得属性变量
    {
        return expressInfoList;
    }

    public ExpressInfo getExpressInfo(int position)
    {
        return expressInfoList.get(position);
    }

    public int getInfoNum()
    {
        return expressInfoList.size();
    }

    /**
     * 去除掉某个位置的元素
     * @param location
     */
    public void removeExpressInfo(int location)
    {
        expressInfoList.remove(location);
    }

    public void removeExpressInfo(ExpressInfo expressInfo)
    {
        if(expressInfoList.contains(expressInfo))
            expressInfoList.remove(expressInfo);
    }

    public void swapNearExpressInfo(int i,int j)
    {
        if(i<j){
            ExpressInfo temp=expressInfoList.get(j);
            expressInfoList.remove(j);
            expressInfoList.add(i,temp);
        }
        else {
            ExpressInfo temp=expressInfoList.get(i);
            expressInfoList.remove(i);
            expressInfoList.add(j,temp);
        }
    }

    public ExpressInfo getExpressInfoByAddress(String Address)
    {
        for(ExpressInfo info:expressInfoList)
        {
            if(info.getReceiverAddress().equals(Address))
                return info;
        }
        return null;
    }

    public void removeSendedData()
    {
        for(ExpressInfo info:expressInfoList){
            if(info.getIsDelivered())
                expressInfoList.remove(info);
        }
    }

}
