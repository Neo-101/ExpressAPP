package example.com.expressapp.login.presenter;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Printer;
import android.widget.Toast;

import example.com.expressapp.basispage.view.BasisPageActivity;
import example.com.expressapp.login.model.UploadUserInformationByPostService;
import example.com.expressapp.login.model.iPostBack;
import example.com.expressapp.login.view.LoginActivity;
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
                    loginResult = ipostback.save(iView.getUsername(), iView.getPassword());
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
        this.ipostback=new UploadUserInformationByPostService();
    }
}
