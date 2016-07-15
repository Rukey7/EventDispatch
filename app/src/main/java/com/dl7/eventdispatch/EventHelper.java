package com.dl7.eventdispatch;

import android.view.MotionEvent;

/**
 * Created by long on 2016/7/15.
 */
public class EventHelper {

    private EventHelper() {

    }


    public static String getEventName(int eventAction) {
        switch (eventAction) {
            case MotionEvent.ACTION_DOWN:
                return "ACTION_DOWN";
            case MotionEvent.ACTION_MOVE:
                return "ACTION_MOVE";
            case MotionEvent.ACTION_UP:
                return "ACTION_UP";
            default:
                return "";
        }
    }
}
