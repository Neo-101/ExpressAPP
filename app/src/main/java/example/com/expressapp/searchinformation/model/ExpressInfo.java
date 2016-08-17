package example.com.expressapp.searchinformation.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xyj64 on 2016/8/7.
 */
public class ExpressInfo implements Serializable{
    private String ladingName;//货物名称
    private float ladingWeight;//货物重量
    private String ladingSpecifications;//货物尺寸
    private String senderName;//发件者名字
    private String senderPhone;//发件者电话
    private String senderAddress;//发件者地址
    private String receiverName;//接受者名字
    private String receiverPhone;//接受者电话
    private String receiverAddress;//接受者地址
    private String idNum;//快递单号
    private Date upDataTime;//接受货物时间
    private boolean isDelivered;//是否送达


    public ExpressInfo()
    {
        ladingName=null;
        ladingWeight=0;
        ladingSpecifications=null;
        senderName=null;
        senderPhone=null;
        senderAddress=null;
        receiverName=null;
        receiverPhone=null;
        receiverAddress=null;
        idNum=null;
        upDataTime =new Date(0,0,0);
        isDelivered=false;
    }

    public  ExpressInfo(ExpressInfo expressInfo)
    {
        ladingName=expressInfo.getLadingName();
        ladingWeight=expressInfo.getLadingWeight();
        ladingName=expressInfo.getLadingName();
        senderName=expressInfo.getSenderName();
        senderAddress=expressInfo.getSenderAddress();
        senderPhone=expressInfo.getSenderPhone();
        receiverAddress=expressInfo.getReceiverAddress();
        receiverName=expressInfo.getReceiverName();
        receiverPhone=expressInfo.getSenderPhone();
        idNum=expressInfo.getIdNum();
        setUpDataTime(expressInfo.getUpDataTime());
        isDelivered=expressInfo.getIsDelivered();
    }

    public ExpressInfo(String ladingname,float ladingweight,String ladingspecifications,String sendername,String senderphone,String senderaddress,
                       String receivername, String receiverphone,String receiveraddress,String idnum,Date updatatime,boolean isdelivered)
    {
        ladingName=ladingname;
        ladingWeight=ladingweight;
        ladingSpecifications=ladingspecifications;
        senderName=sendername;
        senderPhone=senderphone;
        senderAddress=senderaddress;
        receiverName=receivername;
        receiverPhone=receiverphone;
        receiverAddress=receiveraddress;
        idNum=idnum;
        upDataTime =updatatime;
        isDelivered=isdelivered;
    }

    public String getLadingName()//获取货物名称
    {
        return ladingName;
    }

    public float getLadingWeight()//获取货物重量
    {
        return ladingWeight;
    }

    public String getLadingSpecifications()//获取货物尺寸
    {
        return ladingSpecifications;
    }

    public String getSenderName()//获取发件者名字
    {
        return senderName;
    }

    public String getSenderPhone()//获取发件者电话
    {
        return senderPhone;
    }
    public String getSenderAddress()//获取发件者地址
    {
        return senderAddress;
    }

    public String getReceiverName()//获取接受者名字
    {
        return receiverName;
    }

    public String getReceiverPhone()//获取接受者电话
    {
        return receiverPhone;
    }

    public String getReceiverAddress()//获取接受者地址
    {
        return receiverAddress;
    }

    public String getIdNum()//获取快递单号
    {
        return idNum;
    }

    /**
     * 返回值是一个日期的字符串，格式为yyyy-MM-dd HH:mm:ss
     * @return
     */
    public String getUpDataTime()
    {
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(upDataTime);

    }

    public boolean getIsDelivered()//返回是否送达，送达为true，否则为false
    {
        return isDelivered;
    }

    public void setLadingName(String ladingname)//设置货物名称
    {
        ladingName=ladingname;
    }

    public void setLadingWeight(float ladingweight)//设置货物重量
    {
        ladingWeight=ladingweight;
    }

    public void setLadingSpecifications(String ladingspecifications)//设置货物尺寸
    {
        ladingSpecifications=ladingspecifications;
    }

    public void setSenderName(String sendername)//设置发送者名字
    {
        senderName=sendername;
    }

    public void setSenderPhone(String senderphone)//设置发件者电话
    {
        senderPhone=senderphone;
    }

    public void setSenderAddress(String senderaddress)//设置发件者地址
    {
        senderAddress=senderaddress;
    }

    public void setReceiverName(String receivername)//设置接受者名字
    {
        receiverName=receivername;
    }

    public void setReceiverPhone(String receiverphone)//设置接受者电话
    {
        receiverPhone=receiverphone;
    }

    public void setReceiverAddress(String receiveraddress)//设置接受者地址
    {
        receiverAddress=receiveraddress;
    }

    public void setIdNum(String idnum)//设置快递单号
    {
        idNum=idnum;
    }

    /**
     * 用Date数据对Date数据进行赋值，Date数据的初始化初始化方法有用3或者5或者6个int型的数据
     * （注意，月份8月初始化值（-1）用7，年2016年初始化的值用（-1900）116）
     * @param updatatime
     */
    public void setUpDataTime(Date updatatime)
    {
        upDataTime =updatatime;
    }

    /**
     * 用字符串对Date数据进行赋值，字符串的格式必须为yyyy-MM-dd HH:mm:ss
     * @param updatatime
     */
    public void setUpDataTime(String updatatime)
    {
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            upDataTime = sdf.parse(updatatime);
        }
        catch (ParseException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public void setIsDelivered(boolean isdelivered)//设置是否送达，送达为true，否则为false
    {
        isDelivered=isdelivered;
    }

    /**
     * 用字符串来表示是否送达，“0”表示没有送的，“1”表示送达，跟彭锋相匹配。
     * @param isdeliverd
     */
    public void setIsDelivered(String isdeliverd)
    {
        if(isdeliverd.equals("0"))
            isDelivered=false;
        else
            isDelivered=true;
    }
}
