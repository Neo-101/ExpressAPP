package example.com.expressapp.searchinformation.presenter;

import android.os.Handler;
import android.os.Message;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import example.com.expressapp.searchinformation.model.getLadingInfoBack;
import example.com.expressapp.searchinformation.model.iGetLadingBack;
import example.com.expressapp.searchinformation.view.iInformation;

/**
 * Created by xyj64 on 2016/8/9.
 */
public class GetInfoPresenterImpl implements iGetInfoPresenter {
    private iInformation iView;
    private iGetLadingBack iPostBack;
    @Override
    public void judgeGetLadingInfo(final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String getLaingInfoResult=new String();
                SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //Date curDate=new Date(System.currentTimeMillis());
                //Date beforeDate=getBeforeTime(curDate);
                Date beforeDate=new Date(116,4,15);
                String strDate=format.format(beforeDate);
                try {
                    getLaingInfoResult=iPostBack.getLadingInfo(iView.getGUID(),strDate);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                Message message=Message.obtain();
                message.obj=getLaingInfoResult;
                message.what=1;
                handler.sendMessage(message);
            }
        }).start();
    }

    public GetInfoPresenterImpl(iInformation iView)
    {
        this.iView=iView;
        iPostBack=new getLadingInfoBack();
    }

    private Date getBeforeTime(Date date)
    {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,-2);
        date=calendar.getTime();
        return date;
    }
}
