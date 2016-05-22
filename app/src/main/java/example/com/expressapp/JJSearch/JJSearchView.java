package example.com.expressapp.JJSearch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;

import example.com.expressapp.R;

/**
 * Created by lxs on 2016/5/20.
 */
public class JJSearchView extends android.support.v7.widget.SearchView
{
    private Paint mPaint;
    private Path mPath;
    private JJBaseController mController;
    public JJSearchView(Context context) {
        this(context, null);
    }

    public JJSearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JJSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        Log.d("TAG","init");
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(4);
        mPath=new Path();
        mController=new JJCircleToSimpleLineController();
        mController.setSearchView(this);
    }
    public void setController(JJBaseController controller)
    {
        this.mController=controller;
        mController.setSearchView(this);
    }

    @Override
    protected void onSizeChanged(int w,int h,int oldw,int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        Log.d("TAG","onDraw");
        mController.draw(canvas,mPaint);
    }


    public void startAnim()
    {
        Log.d("TAG","start JJSearchView");
        if(mController!=null) mController.startAnim();
    }

    public void resetAnim()
    {
        if(mController!=null) mController.resetAnim();
    }
}
