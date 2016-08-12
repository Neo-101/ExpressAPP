package example.com.expressapp.setting.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import example.com.expressapp.basispage.view.iBasisPage;
import example.com.expressapp.setting.model.PostBack;
import example.com.expressapp.setting.view.iPersonInfo;
import example.com.expressapp.setting.view.iSetting;

/**
 * Created by lxs on 2016/5/17.
 */
public class PresenterImpl implements iPresenter {
    private String guid;
    private example.com.expressapp.setting.model.iPostBack iPostBack;
    @Override
    public void logoutJudge(final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String logoutResult=new String();
                try {
                    logoutResult = iPostBack.postBackInfo(guid,"AndroidLogoutMethod");
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

    public void personalInfoJudge(final Handler handler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String personalInfoResult=new String();
                try {
                    Log.d("PersonalInfo","thread");
                    personalInfoResult = iPostBack.postBackInfo(guid,"AccountSettingMethod");
                    Log.d("PersonalInfo","3");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                Message message=Message.obtain();
                message.obj=personalInfoResult;
                message.what=2;
                handler.sendMessage(message);
                Log.d("PersonalInfo","4");
            };
        }).start();
    }

    public PresenterImpl(iBasisPage iView)
    {
        guid=iView.getGUID();
        this.iPostBack=new PostBack();
    }

    public PresenterImpl(iPersonInfo iView)
    {
        guid=iView.getGUID();
        this.iPostBack=new PostBack();
    }

}
