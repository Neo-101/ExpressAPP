package example.com.expressapp;

import android.app.Application;
import android.util.Log;

/**
 * Created by xyj64 on 2016/8/9.
 */
public class adminGUID extends Application {
    private String GUID;

    public String getGUID()
    {
        return GUID;
    }

    public void setGUID(String guid)
    {
        Log.d("test","1");
        GUID=guid;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        GUID="";
    }
}
