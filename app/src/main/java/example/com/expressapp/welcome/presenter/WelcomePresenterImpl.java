package example.com.expressapp.welcome.presenter;

import android.content.SharedPreferences;

/**
 * Created by lxs on 2016/5/12.
 */
public class WelcomePresenterImpl implements iWelcomePresenter{

    private JudgeActivity judgeActivity;

    public WelcomePresenterImpl(JudgeActivity judgeActivity)
    {
        this.judgeActivity=judgeActivity;
    }


    @Override
    public boolean Isfirst_run(SharedPreferences sharedPreferences) {
        boolean isFirstRun=sharedPreferences.getBoolean("isFirstRun",true);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        if(isFirstRun)
        {
            editor.putBoolean("isFirstRun",false);
            editor.commit();
            return true;
        }
        else
        {
            return false;
        }
    }
}
