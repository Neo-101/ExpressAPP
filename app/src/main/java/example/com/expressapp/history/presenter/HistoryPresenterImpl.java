package example.com.expressapp.history.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import example.com.expressapp.history.model.HistoryInfoPostBack;
import example.com.expressapp.history.model.iHistoryInfoPostBack;
import example.com.expressapp.history.view.iHistory;


/**
 * Created by xyj64 on 2016/8/11.
 */
public class HistoryPresenterImpl implements iHistoryPresenter {
    private iHistory iView;
    private iHistoryInfoPostBack iPostBack;
    @Override
    public void historyJudge(final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String loginResult=new String();
                loginResult="";
                SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //Date curDate=new Date(System.currentTimeMillis());
                //Date beforeDate=getBeforeTime(curDate);
                Date beforeDate=new Date(116,5,7);
                Date afterDate=getNearbyDay(beforeDate,1);
                try {
                    for(int i=0;i<7;i++) {
                        loginResult += iPostBack.getHistoryInfo(iView.getGUID(), format.format(beforeDate), format.format(afterDate))+"#"+format.format(beforeDate)+"##";
                        beforeDate=afterDate;
                        afterDate=getNearbyDay(beforeDate,1);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                Message message=Message.obtain();
                message.obj=loginResult;
                message.what=1;
                handler.sendMessage(message);
            };
        }).start();
    }
    public HistoryPresenterImpl(iHistory iView)
    {
        this.iView=iView;
        this.iPostBack=new HistoryInfoPostBack();
    }

    private Date getNearbyDay(Date date,int interval)
    {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,interval);
        date=calendar.getTime();
        return date;
    }
}
