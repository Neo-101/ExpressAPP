package example.com.expressapp;

import java.util.ArrayList;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lxs on 2016/5/17.
 */
public class ViewPagerAdapter extends PagerAdapter
{
    private ArrayList<View> view_list;

    public ViewPagerAdapter(ArrayList<View> view_list)
    {
        this.view_list=view_list;
    }
    @Override
    public int getCount()
    {
        return view_list.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1)
    {
        return arg0==arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView(view_list.get(position));
    };

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        container.addView(view_list.get(position),null);
        return view_list.get(position);
    }

}
