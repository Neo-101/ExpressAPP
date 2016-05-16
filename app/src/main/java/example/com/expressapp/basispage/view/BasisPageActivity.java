package example.com.expressapp.basispage.view;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import example.com.expressapp.R;

public class BasisPageActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private double exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.basispage_layout);
        initViews();
        initToolbar();
        actionBarDrawerToggle=new ActionBarDrawerToggle(BasisPageActivity.this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
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
        else this.finish();
    }
    private void initViews()
    {
        exitTime=0;
        toolbar=(Toolbar)findViewById(R.id.Toolbar);
        drawerLayout=(DrawerLayout)findViewById(R.id.basispage_layout_content);
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
}
