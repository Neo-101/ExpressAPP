package example.com.expressapp.setting.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import example.com.expressapp.basispage.view.iBasisPage;
import example.com.expressapp.setting.model.LogoutPostBack;
import example.com.expressapp.setting.model.iLogoutPostBack;
import example.com.expressapp.setting.view.iSetting;

/**
 * Created by lxs on 2016/5/17.
 */
public class LogoutPresenterImpl implements iLogoutPresenter {
    private String guid;
    private iLogoutPostBack iPostBack;
    @Override
    public void LogoutJudge(final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String logoutResult=new String();
                try {
                    logoutResult = iPostBack.logout(guid);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                Message message=Message.obtain();
                message.obj=logoutResult;
                message.what=1;
                handler.sendMessage(message);
            };
        }).start();
    }
    public LogoutPresenterImpl(iSetting iView)
    {
        guid=iView.getGUID();
        this.iPostBack =new LogoutPostBack();
    }

    public LogoutPresenterImpl(iBasisPage iView)
    {
        guid=iView.getGUID();
        this.iPostBack=new LogoutPostBack();
    }

}
