package com.yc.view.tools;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

public class MarQueeTextView extends TextView {

	public MarQueeTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MarQueeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MarQueeTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

//	@Override
//	protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
//		if (focused)
//			super.onFocusChanged(focused, direction, previouslyFocusedRect);
//	}

//	@Override
//	public void onWindowFocusChanged(boolean focused) {
//		if (focused)
//			super.onWindowFocusChanged(focused);
//	}

	@Override
	public boolean isFocused() {
		return true;
	}
}
