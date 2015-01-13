package com.yc.view.tools;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * @see阻止触屏事件下发到子控件
 * 2014.12.16
 * @author chenshuwan
 *
 */
public class TouchFramLayout extends FrameLayout {

	public TouchFramLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public TouchFramLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public TouchFramLayout(Context context) {
		super(context);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
//		return true;
		return false;
	}

}
