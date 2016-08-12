package example.com.expressapp.basispage.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import example.com.expressapp.ActivityList;
import example.com.expressapp.R;
import example.com.expressapp.adminGUID;
import example.com.expressapp.history.view.HistoryFragment;
import example.com.expressapp.searchinformation.model.ExpressInfoManager;
import example.com.expressapp.searchinformation.view.InformationFragment;
import example.com.expressapp.send.view.SendFragment;
import example.com.expressapp.setting.presenter.PresenterImpl;
import example.com.expressapp.setting.presenter.iPresenter;
import example.com.expressapp.setting.view.SettingFragment;

public class BasisPageActivity extends AppCompatActivity implements iBasisPage{

    private adminGUID guid;
    private iPresenter iLogout;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private double exitTime;
    //private MaterialSearchView materialSearchView;
    private ExpressInfoManager mExpressInfoManager=new ExpressInfoManager();
    private Handler handler=new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            if(msg.what==1)
            {
                Log.d("test",msg.obj.toString());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ActivityList.addActivity(BasisPageActivity.this);
        initViews();
        guid=(adminGUID)getApplication();
        iLogout=new PresenterImpl(this);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.menu_drawer_item_send :
                        getSupportFragmentManager().beginTransaction().replace(R.id.basispage_layout_content, new SendFragment(mExpressInfoManager,guid)).commit();
                        break;
                    case R.id.menu_drawer_item_search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.basispage_layout_content,new InformationFragment(mExpressInfoManager,guid)).commit();
                        break;
                    case R.id.menu_drawer_item_history:
                        getSupportFragmentManager().beginTransaction().replace(R.id.basispage_layout_content,new HistoryFragment(guid)).commit();
                        break;
                    case R.id.menu_drawer_item_setting:
                        getFragmentManager().beginTransaction().replace(R.id.basispage_layout_content,new SettingFragment(guid)).commit();
                        break;
                }
                item.setChecked(true);
                drawerLayout.closeDrawer(Gravity.LEFT);
                return false;
            }
        });
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ActivityList.removeActivity(BasisPageActivity.this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        //materialSearchView.setMenuItem(menu.findItem(R.id.menu_toolbar_search));
        return true;
    }
    @Override
    public void onBackPressed()
    {
        if(drawerLayout.isDrawerOpen(Gravity.LEFT))
            drawerLayout.closeDrawer(Gravity.LEFT);
        else exitApp();
    }

    private void exitApp()
    {
        if(System.currentTimeMillis()-exitTime>2000)
        {
            Toast.makeText(BasisPageActivity.this,"再按一次退出",Toast.LENGTH_SHORT).show();
            exitTime=System.currentTimeMillis();
        }
        else {
            iLogout.logoutJudge(handler);
            ActivityList.exitAllActivity();
        }
    }
    private void initViews()
    {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.basispage_layout);
        exitTime=0;
        toolbar=(Toolbar)findViewById(R.id.Toolbar);
        drawerLayout=(DrawerLayout)findViewById(R.id.basispage_layout_drawerlayout);
        navigationView=(NavigationView)findViewById(R.id.basispage_layout_drawer);
        //materialSearchView=(MaterialSearchView)findViewById(R.id.basispage_layout_searchview) ;
        initToolbar();
        actionBarDrawerToggle=new ActionBarDrawerToggle(BasisPageActivity.this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        actionBarDrawerToggle.syncState();
    }
    private void initToolbar()
    {
        toolbar.setTitle("BasisPage");
        toolbar.setTitleTextColor(getResources().getColor(R.color.windowBackground));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Toolbar.OnMenuItemClickListener onMenuItemClickListener=new Toolbar.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.menu_toolbar_search:
                        Toast.makeText(BasisPageActivity.this,"Click Search",Toast.LENGTH_SHORT).show();
                }
                return  true;
            }
        };
        toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
    }

    @Override
    public String getGUID() {
        return guid.getGUID();
    }
}
