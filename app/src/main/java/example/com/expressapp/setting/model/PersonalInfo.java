package example.com.expressapp.setting.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xyj64 on 2016/8/12.
 */
public class PersonalInfo  {
    private String nickName;
    private String personalNum;
    private String manageArea;
    private String telephone;
    private String email;
    private String leadingManName;
    private Date registerTime;
    private String registerNum;
    //以“#”分割 依次为“昵称”、“身份证号码”、“管理区域”、“手机号”、“邮箱”、“负责人姓名”、“注册时间”、“注册码”；

    public PersonalInfo()
    {
        nickName=null;
        personalNum=null;
        manageArea=null;
        telephone=null;
        email=null;
        leadingManName=null;
        registerTime=new Date(0,0,0);
        registerNum=null;
    }

    public PersonalInfo(String gettedInfo)
    {
        String []data=gettedInfo.split("#");
        if(data.length>3)
        {
            setNickName(data[0]);
            setPersonalNum(data[1]);
            setManageArea(data[2]);
            setTelephone(data[3]);
            setEmail(data[4]);
            setLeadingManName(data[5]);
            setRegisterTime(data[6]);
            setRegisterNum(data[7]);
        }
    }

    public String getNickName()
    {
        return nickName;
    }

    public String getPersonalNum()
    {
        return personalNum;
    }

    public String getManageArea()
    {
        return manageArea;
    }

    public String getTelephone()
    {
        return telephone;
    }

    public String getEmail()
    {
        return email;
    }

    public String getLeadingManName()
    {
        return leadingManName;
    }
    /**
     * 返回值是一个日期的字符串，格式为yyyy-MM-dd HH:mm:ss
     * @return
     */
    public String getRegisterTime()
    {
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(registerTime);
    }

    public String getRegisterNum()
    {
        return registerNum;
    }

    public void setNickName(String nickname)
    {
        nickName=nickname;
    }

    public void setPersonalNum(String personalnum)
    {
        personalNum=personalnum;
    }

    public void setManageArea(String managearea)
    {
        manageArea=managearea;
    }

    public void setTelephone(String telephone)
    {
        this.telephone=telephone;
    }

    public void setEmail(String email)
    {
        this.email=email;
    }

    public void setLeadingManName(String leadingmanname)
    {
        leadingManName=leadingmanname;
    }

    /**
     * 用Date数据对Date数据进行赋值，Date数据的初始化初始化方法有用3或者5或者6个int型的数据
     * （注意，月份8月初始化值（-1）用7，年2016年初始化的值用（-1900）116）
     * @param registertime
     */
    public void setRegisterTime(Date registertime)
    {
        registerTime =registertime;
    }

    /**
     * 用字符串对Date数据进行赋值，字符串的格式必须为yyyy-MM-dd HH:mm:ss
     * @param registertime
     */
    public void setRegisterTime(String registertime)
    {
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            registerTime = sdf.parse(registertime);
        }
        catch (ParseException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void setRegisterNum(String registernum)
    {
        registerNum=registernum;
    }
}
