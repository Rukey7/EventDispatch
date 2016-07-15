package com.dl7.eventdispatch.views;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.dl7.eventdispatch.EventHelper;
import com.dl7.eventdispatch.entity.EventMsg;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by long on 2016/7/15.
 */
public class MyViewGroup extends FrameLayout implements View.OnTouchListener, View.OnClickListener {

    private boolean mIsIntercept;
    private boolean mIsConsume;
    private boolean mIsTouchConsume;
    private boolean mIsOpenListener;


    public MyViewGroup(Context context) {
        this(context, null);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * dispatchTouchEvent前后调用都打印
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("Event", "ViewGroup : dispatchTouchEvent1 = "
                + EventHelper.getEventName(MotionEventCompat.getActionMasked(ev)));
        EventBus.getDefault().post(new EventMsg("ViewGroup : dispatchTouchEvent1 = "
                + EventHelper.getEventName(MotionEventCompat.getActionMasked(ev))));
        boolean result = super.dispatchTouchEvent(ev);
        Log.e("Event", "ViewGroup : dispatchTouchEvent2 = "
                + EventHelper.getEventName(MotionEventCompat.getActionMasked(ev))
                + " - " + result);
        EventBus.getDefault().post(new EventMsg("ViewGroup : dispatchTouchEvent2 = "
                + EventHelper.getEventName(MotionEventCompat.getActionMasked(ev))
                + " - " + result));
        return result;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        super.onInterceptTouchEvent(ev);
        Log.e("Event", "ViewGroup : onInterceptTouchEvent = "
                + EventHelper.getEventName(MotionEventCompat.getActionMasked(ev))
                + " - " + mIsIntercept);
        EventBus.getDefault().post(new EventMsg("ViewGroup : onInterceptTouchEvent = "
                + EventHelper.getEventName(MotionEventCompat.getActionMasked(ev))
                + " - " + mIsIntercept));
        return mIsIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        boolean result = super.onTouchEvent(event);
        Log.e("Event", "ViewGroup : onTouchEvent = "
                + EventHelper.getEventName(MotionEventCompat.getActionMasked(event))
                + " - " + mIsConsume);
        EventBus.getDefault().post(new EventMsg("ViewGroup : onTouchEvent = "
                + EventHelper.getEventName(MotionEventCompat.getActionMasked(event))
                + " - " + mIsConsume));
        return mIsConsume;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.e("Event", "ViewGroup : OnTouchListener = "
                + EventHelper.getEventName(MotionEventCompat.getActionMasked(event))
                + " - " + mIsTouchConsume);
        EventBus.getDefault().post(new EventMsg("ViewGroup : OnTouchListener = "
                + EventHelper.getEventName(MotionEventCompat.getActionMasked(event))
                + " - " + mIsTouchConsume));
        return mIsTouchConsume;
    }

    @Override
    public void onClick(View v) {
        Log.e("Event", "ViewGroup : OnClickListener");
        EventBus.getDefault().post(new EventMsg("ViewGroup : OnClickListener"));
    }

    public void setIntercept(boolean intercept) {
        mIsIntercept = intercept;
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
