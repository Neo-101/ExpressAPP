package example.com.expressapp.login.presenter;

import android.os.Handler;
import android.os.Message;

import example.com.expressapp.login.model.LoginPostBack;
import example.com.expressapp.login.model.iPostBack;
import example.com.expressapp.login.view.i_LoginView;

/**
 * Created by lxs on 2016/5/12.
 */
public class PresenterCompl implements iLoginPresenter{
    private i_LoginView iView;
    private iPostBack ipostback;
    @Override
    public void LoginJudge(final Handler handler) {
        iView.LoginLoading();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String loginResult=new String();
                try {
                    loginResult = ipostback.login(iView.getUsername(), iView.getPassword());
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
    public PresenterCompl(i_LoginView iView)
    {
        this.iView=iView;
        this.ipostback=new LoginPostBack();
    }
}
