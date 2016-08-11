package example.com.expressapp.welcome.presenter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import example.com.expressapp.ActivityList;
import example.com.expressapp.login.presenter.PresenterCompl;
import example.com.expressapp.login.view.LoginActivity;
import example.com.expressapp.welcome.view.WelcomeActivity;

/**
 * Created by xyj64 on 2016/8/9.
 */
public class JudgeActivity extends Activity{
    private double exitTime;
    private SharedPreferences sharedPreferences;
    private iWelcomePresenter presenter;
    private boolean isfirstrun;
    private boolean firstRun=true;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ActivityList.addActivity(JudgeActivity.this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        sharedPreferences=getSharedPreferences("share",0);
        presenter=new WelcomePresenterImpl(this);
        isfirstrun=presenter.Isfirst_run(sharedPreferences);
        if(isfirstrun)
        {
            intent=new Intent(this, WelcomeActivity.class);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        while (firstRun) {
                            startActivity(intent);
                            firstRun = false;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        else
        {
            intent=new Intent(this, LoginActivity.class);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                        while (firstRun) {
                            startActivity(intent);
                            firstRun=false;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ActivityList.removeActivity(JudgeActivity.this);
    }

    //当返回键被按下时调用
    @Override
    public void onBackPressed()
    {
        exitAPP();
    }

    //退出APP
    private void exitAPP()
    {
        if(System.currentTimeMillis()-exitTime>2000)
        {
            Toast.makeText(JudgeActivity.this,"再按一次退出",Toast.LENGTH_SHORT).show();
            exitTime=System.currentTimeMillis();
        }
        else {
            ActivityList.exitAllActivity();
            firstRun=false;
        }
    }
}