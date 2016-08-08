package example.com.expressapp.JJSearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import example.com.expressapp.R;

public class TestActivity extends AppCompatActivity
{
    JJSearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchview_test);
        mSearchView=(JJSearchView)findViewById(R.id.jjSearchView);
        mSearchView.setController(new JJCircleToSimpleLineController());
    }

     public void start(View v)
     {
         mSearchView.startAnim();
     }
    public void reset(View v)
    {
        mSearchView.resetAnim();
    }

}
