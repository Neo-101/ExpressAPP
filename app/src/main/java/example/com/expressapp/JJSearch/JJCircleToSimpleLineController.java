package example.com.expressapp.JJSearch;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

/**
 * Created by lxs on 2016/5/20.
 */
public class JJCircleToSimpleLineController extends JJBaseController
{
    private int cx,cy,cr;
    private RectF mRectF;
    private  int j;

    public  JJCircleToSimpleLineController()
    {
        mRectF=new RectF();
    }
    @Override
    public void draw(Canvas canvas, Paint paint)
    {
        Log.d("TAG","start draw");
        canvas.drawColor(Color.parseColor("#000000"));
        switch (mState)
        {
            case STATE_ANIM_NONE:
                drawNormalView(paint,canvas);
                break;
            case STATE_ANIM_START:
                drawStartAnimView(paint,canvas);
                break;
            case STATE_ANIM_STOP:
                drawStopAnimView(paint, canvas);
                break;
        }

    }
    private  void drawStopAnimView(Paint paint,Canvas canvas)
    {
        canvas.save();
        if (mPro<=0.2f)
        {
            canvas.drawLine(mRectF.right-cr+j+(1-mPro*5)*j, mRectF.bottom-cr+j+(1-mPro*5)*j,
                    mRectF.right-cr+2*j, mRectF.bottom-cr+2*j, paint);
        } else {
            canvas.drawLine(mRectF.right - cr + j, mRectF.bottom - cr + j,
                    mRectF.right - cr + 2 * j, mRectF.bottom - cr + 2 * j, paint);
            canvas.drawArc(mRectF, 45, -(360*(mPro-0.2f)*1.25f), false, paint);
        }
        canvas.drawLine((mRectF.right-cr+2*j) *(0.2f+mPro*0.8f), mRectF.bottom-cr+2*j,
                mRectF.right-cr+2*j, mRectF.bottom-cr+2*j, paint);
        canvas.restore();
    }
    private void drawStartAnimView(Paint paint, Canvas canvas) {
        canvas.save();
        if(mPro<=0.8f) {
            canvas.drawArc(mRectF, 45, -(360 - 360 * (mPro == -1 ? 1 : mPro*1.25f)), false, paint);
            canvas.drawLine(mRectF.right - cr + j, mRectF.bottom - cr + j,
                    mRectF.right - cr + 2 * j, mRectF.bottom - cr + 2 * j, paint);
        }
        else {
            canvas.drawLine(mRectF.right - cr + j + (mPro-0.8f)*5 * j, mRectF.bottom - cr + j + (mPro-0.8f)*5 * j,
                    mRectF.right - cr + 2 * j, mRectF.bottom - cr + 2 * j, paint);
        }

        canvas.drawLine((mRectF.right-cr+2*j) * (1 - mPro * 0.8f), mRectF.bottom-cr+2*j,
                mRectF.right-cr+2*j, mRectF.bottom-cr+2*j, paint);
        canvas.restore();
    }

    private void drawNormalView(Paint paint, Canvas canvas) {
        cr = getWidth() / 14;
        cx = getWidth() / 2;
        cy = getHeight() / 2;
        j=(int)(cr/(Math.sqrt(2)));
        mRectF.left = cx - cr;
        mRectF.right = cx + cr;
        mRectF.top = cy - cr;
        mRectF.bottom = cy + cr;
        canvas.save();
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.STROKE);
        canvas.rotate(45, cx, cy);
        canvas.drawLine(cx + cr, cy,(cx + cr * 2), cy, paint);
        canvas.drawArc(mRectF, 0, 360, false, paint);
        canvas.restore();
    }

    @Override
    public void startAnim()
    {
        if(mState==STATE_ANIM_START) return;
        mState=STATE_ANIM_START;
        Log.d("TAG","start JJController");
        startSearchViewAnim();
    }

    @Override
    public void resetAnim()
    {
        if(mState==STATE_ANIM_STOP) return;
        mState=STATE_ANIM_STOP;
        startSearchViewAnim();
    }
}
