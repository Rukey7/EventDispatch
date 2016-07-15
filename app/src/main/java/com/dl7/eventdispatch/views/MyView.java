package com.dl7.eventdispatch.views;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.dl7.eventdispatch.EventHelper;
import com.dl7.eventdispatch.entity.EventMsg;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by long on 2016/7/15.
 */
public class MyView extends View implements View.OnTouchListener, View.OnClickListener {

    private boolean mIsConsume;
    private boolean mIsTouchConsume;
    private boolean mIsOpenListener;

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * dispatchTouchEvent前后调用都打印
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.w("Event", "View : dispatchTouchEvent1 = "
                + EventHelper.getEventName(MotionEventCompat.getActionMasked(ev)));
        EventBus.getDefault().post(new EventMsg("View : dispatchTouchEvent1 = "
                + EventHelper.getEventName(MotionEventCompat.getActionMasked(ev))));
        boolean result = super.dispatchTouchEvent(ev);
        Log.w("Event", "View : dispatchTouchEvent2 = "
                + EventHelper.getEventName(MotionEventCompat.getActionMasked(ev))
                + " - " + result);
        EventBus.getDefault().post(new EventMsg("View : dispatchTouchEvent2 = "
                + EventHelper.getEventName(MotionEventCompat.getActionMasked(ev))
                + " - " + result));
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        super.onTouchEvent(event);
        Log.w("Event", "View : onTouchEvent = "
                + EventHelper.getEventName(MotionEventCompat.getActionMasked(event))
                + " - " + mIsConsume);
        EventBus.getDefault().post(new EventMsg("View : onTouchEvent = "
                + EventHelper.getEventName(MotionEventCompat.getActionMasked(event))
                + " - " + mIsConsume));
        return mIsConsume;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.w("Event", "View : OnTouchListener = "
                + EventHelper.getEventName(MotionEventCompat.getActionMasked(event))
                + " - " + mIsTouchConsume);
        EventBus.getDefault().post(new EventMsg("View : OnTouchListener = "
                + EventHelper.getEventName(MotionEventCompat.getActionMasked(event))
                + " - " + mIsTouchConsume));
        return mIsTouchConsume;
    }

    @Override
    public void onClick(View v) {
        Log.w("Event", "View : OnClickListener");
        EventBus.getDefault().post(new EventMsg("View : OnClickListener"));
    }

    public void setConsume(boolean consume) {
        mIsConsume = consume;
    }

    public void setTouchConsume(boolean touchConsume) {
        mIsTouchConsume = touchConsume;
    }

    public void setOpenListener(boolean openListener) {
        mIsOpenListener = openListener;
        if (mIsOpenListener) {
            setOnTouchListener(this);
            setOnClickListener(this);
        } else {
            setOnTouchListener(null);
            setOnClickListener(null);
        }
    }
}
