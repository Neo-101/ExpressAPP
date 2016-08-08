package example.com.expressapp.searchinformation.model;

/**
 * Created by xyj64 on 2016/8/7.
 */
public class ExpressInfo {
    private String ladingName;//货物名称
    private float ladingWeight;//货物重量
    private String ladingSpecifications;//货物尺寸
    private String senderName;//发件者名字
    private String senderPhone;//发件者电话
    private String senderAddress;//发件者地址
    private String receiverName;//接受者名字
    private String receiverPhone;//接受者电话
    private String receiverAddress;//接受者地址
    private int expressNo;//快递单号

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
        expressNo=0;
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
        expressNo=expressInfo.getExpressNo();
    }

    public ExpressInfo(String ladingname,float ladingweight,String ladingspecifications,String sendername,String senderphone,String senderaddress,String receivername,String receiverphone,String receiveraddress,int expressno)
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
        expressNo=expressno;
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

    public int getExpressNo()
    {
        return expressNo;
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

    public void setExpressNo(int expressno)
    {
        expressNo=expressno;
    }
}
