package example.com.expressapp.send.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import example.com.expressapp.send.model.UpdataLadingPostBack;
import example.com.expressapp.send.model.iUpdataLadingBack;
import example.com.expressapp.send.view.iSend;

/**
 * Created by lxs on 2016/5/17.
 */
public class UpdataLadingPresenterImpl implements iUpdataLadingPresenter {
    private iSend iView;
    private iUpdataLadingBack iPostBack;

    @Override
    public void judgeUpLading(final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String upLadingResult=new String();
                try {
                    Log.d("test2",iView.getIdNum());
                    Log.d("test2",iView.getGUID());
                    upLadingResult=iPostBack.updataLading(iView.getGUID(),iView.getIdNum());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                Message message=Message.obtain();
                message.what=1;
                message.obj=upLadingResult;
                handler.sendMessage(message);
            }
        }).start();
    }

    public UpdataLadingPresenterImpl(iSend iView)
    {
        this.iView=iView;
        iPostBack=new UpdataLadingPostBack();
    }
}
