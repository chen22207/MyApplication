package com.cs.myapplication.touchTest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by chenshuai12619 on 2015/10/30 14:58.
 */
public class MyCustomlinearLayout extends LinearLayout {

	private final static String TAG = "MyCustomlinearLayout";

	public MyCustomlinearLayout(Context context) {
		super(context);
	}

	public MyCustomlinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyCustomlinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.d(TAG, "dispatchTouchEvent " + ev.getAction());
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.d(TAG, "onInterceptTouchEvent " + ev.getAction());
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d(TAG, "onTouchEvent " + event.getAction());
		return false;
	}
}
