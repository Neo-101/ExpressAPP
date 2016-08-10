package example.com.expressapp.welcome.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import example.com.expressapp.ActivityList;
import example.com.expressapp.R;
import example.com.expressapp.ViewPagerAdapter;
import example.com.expressapp.login.view.LoginActivity;

//第一次打开APP，引导界面
public class WelcomeActivity extends AppCompatActivity
{
    private double exitTime;
    private ViewPager view_pager;     //页面切换控制组件
    private ImageView []point_list;   //存放四个小点对象的数组
    private ArrayList<View> viewpage_list;  //存放四个引导页面的链表
    private AppCompatButton AppCompatButton_start;      //开始按钮
    //存放四个引导页面的图片ID
    private int []welcome_pictures={R.drawable.welcome_bg1,R.drawable.welcome_bg2,R.drawable.welcome_bg3,R.drawable.welcome_bg4};
    private int current_position=0;    //当前页面位置，value：0~3
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ActivityList.addActivity(WelcomeActivity.this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcome_layout);
        init_viewpage();
        view_pager=(ViewPager)findViewById(R.id.welcome_layout_view_pager);
        view_pager.setAdapter(new ViewPagerAdapter(viewpage_list));
        AppCompatButton_start=(AppCompatButton) findViewById(R.id.welcome_layout_AppCompatButton_start);
        view_pager.setOnPageChangeListener(new OnPageChangeListener()
        {
            @Override
            public void onPageSelected(int arg0)
            {
                if(current_position==3) button_disappear();
                set_current_point(arg0);
                if(current_position==3) button_appear();

            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {}
            @Override
            public void onPageScrollStateChanged(int arg0) {}
        });
        init_point();
    }
    //初始化页面资源数据
    private void init_viewpage()
    {
        viewpage_list=new ArrayList<>();
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        for(int i=0;i<welcome_pictures.length;i++)
        {
            ImageView view_temp=new ImageView(WelcomeActivity.this);
            view_temp.setLayoutParams(layoutParams);
            view_temp.setImageResource(welcome_pictures[i]);
            view_temp.setScaleType(ScaleType.FIT_XY);
            viewpage_list.add(view_temp);
        }
    }
    //初始化小点的数据
    private void init_point()
    {
        point_list=new ImageView[viewpage_list.size()];
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.welcome_layout_point_line);
        for(int i=0;i<point_list.length;i++)
        {
            point_list[i]=(ImageView)linearLayout.getChildAt(i);
            point_list[i].setEnabled(true);
            point_list[i].setTag(i);    //为每个小点添加标签，value；0~3
            point_list[i].setOnClickListener(new OnClickListener()
            {
                //当小点被点击时触发
                @Override
                public void onClick(View v)
                {
                    int click_position=(Integer)v.getTag();
                    set_current_page(click_position);
                    set_current_point(click_position);
                }
            });
        }
        point_list[0].setEnabled(false);
    }
    //按照被点击的小点切换当前页面
    private void set_current_page(int click_position)
    {
        if(click_position<0||click_position>=point_list.length) return;
        view_pager.setCurrentItem(click_position);
    }
    //设置被点击小点的状态
    private void set_current_point(int click_position)
    {
        if(click_position<0||click_position>=point_list.length||click_position==current_position) return;
        point_list[click_position].setEnabled(false);
        point_list[current_position].setEnabled(true);
        current_position=click_position;
    }
    //进入按钮出现
    private void button_appear()
    {
        AnimationSet animationSet=new AnimationSet(true);
        AlphaAnimation alphaAnimation=new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(500);
        animationSet.addAnimation(alphaAnimation);
        AppCompatButton_start.startAnimation(animationSet);
        AppCompatButton_start.setVisibility(View.VISIBLE);
        AppCompatButton_start.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                final Intent intent=new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    //进入按钮消失
    private void button_disappear()
    {
        AnimationSet animationSet=new AnimationSet(true);
        AlphaAnimation alphaAnimation=new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(350);
        animationSet.addAnimation(alphaAnimation);
        AppCompatButton_start.startAnimation(animationSet);
        AppCompatButton_start.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ActivityList.removeActivity(WelcomeActivity.this);
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
            Toast.makeText(WelcomeActivity.this,"再按一次退出",Toast.LENGTH_SHORT).show();
            exitTime=System.currentTimeMillis();
        }
        else ActivityList.exitAllActivity();
    }
}
