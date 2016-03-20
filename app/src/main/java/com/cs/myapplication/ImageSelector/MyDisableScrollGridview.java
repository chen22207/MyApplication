package com.cs.myapplication.ImageSelector;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * Created by Administrator on 2016/3/20.
 */
public class MyDisableScrollGridview extends GridView {
    public MyDisableScrollGridview(Context context) {
        super(context);
    }

    public MyDisableScrollGridview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyDisableScrollGridview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyDisableScrollGridview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }
}
