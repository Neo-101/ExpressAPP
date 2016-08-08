package example.com.expressapp.JJSearch;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PathMeasure;
import android.support.annotation.IntDef;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

/**
 * Created by lxs on 2016/5/20.
 */
public abstract class JJBaseController
{
    public static final int STATE_ANIM_NONE=0;
    public static final int STATE_ANIM_START=1;
    public static final int STATE_ANIM_STOP=2;
    public static final int DEFAULT_ANIM_TIME=2000;
    public static final float DEFAULT_ANIM_START=0;
    public static final float DEFAULT_ANIM_ENDF=1;

    protected  float mPro=-1;
    private View mSearchView;
    protected  float[] mPos=new float[2];

    @IntDef({STATE_ANIM_NONE,STATE_ANIM_START, STATE_ANIM_STOP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface  State{}

    @JJBaseController.State protected  int mState=STATE_ANIM_NONE;

    public int getState()
    {
        return mState;
    }
    public void setState(int state)
    {
        mState=state;
    }
    public void setSearchView(View searchView)
    {
        this.mSearchView=searchView;
    }
    public View getSearchView()
    {
        return  mSearchView!=null?mSearchView:null;
    }
    public int getWidth()
    {
        return getSearchView()!=null?getSearchView().getWidth():0;
    }
    public int getHeight()
    {
        return getSearchView()!=null?getSearchView().getHeight():0;
    }
    public abstract void draw(Canvas canvas, Paint paint);
    public void startAnim() {}
    public void resetAnim() {}

    public ValueAnimator startSearchViewAnim()
    {
        ValueAnimator valueAnimator=startSearchViewAnim(DEFAULT_ANIM_START,DEFAULT_ANIM_ENDF,DEFAULT_ANIM_TIME);
        return  valueAnimator;
    }

    public ValueAnimator startSearchViewAnim(float start,float end,long time)
    {
        ValueAnimator valueAnimator=startSearchViewAnim(start, end, time,null);
        return  valueAnimator;
    }
    public ValueAnimator startSearchViewAnim(float start, float end, long time, final PathMeasure pathMeasure)
    {
        Log.d("TAG","start JJBaseController");
        final ValueAnimator valueAnimator=ValueAnimator.ofFloat(start,end);
        valueAnimator.setDuration(time);
        valueAnimator.setInterpolator(new BounceInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                mPro=(float) valueAnimator.getAnimatedValue();
                if(pathMeasure!=null)
                    pathMeasure.getPosTan(mPro,mPos,null);
                getSearchView().invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        if(!valueAnimator.isRunning()) valueAnimator.start();
        mPro=0;
        return  valueAnimator;
    }
}
