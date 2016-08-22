package example.com.expressapp;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by lxs on 2016/5/17.
 * 使用一个ArrayList，用于在任何时候完全退出APP
 */
public class ActivityList
{
    private static  ArrayList<Activity> activityArrayList=new ArrayList<>();
    public static void addActivity(Activity activity)
    {
        for(Activity temp:activityArrayList)
        Log.d("List",temp.getLocalClassName()+",");
        activityArrayList.add(activity);
    }
    public static void removeActivity(Activity activity)
    {
        activityArrayList.remove(activity);
    }
    public static void exitAllActivity()
    {
        for(Activity temp:activityArrayList)
        {
            if(!temp.isFinishing()) temp.finish();
        }
    }
}
