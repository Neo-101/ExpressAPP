package example.com.expressapp;

import android.app.Application;
import android.util.Log;

/**
 * Created by xyj64 on 2016/8/9.
 */
public class adminGUID extends Application {
    private String GUID;
    public static String ipAddress="172.26.52.66";

    public String getGUID()
    {
        return GUID;
    }

    public void setGUID(String guid)
    {
        GUID=guid;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        GUID="";
    }
}
