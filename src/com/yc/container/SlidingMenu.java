package com.yc.container;

import com.yc.log.LogUtil;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.TextView;

public class SlidingMenu extends ViewGroup {
	private final static String TAG = "SlidingMenu";
	private View leftV, rightV;
	private Scroller scroller;
	private int distance = 0;
	private int leftViewWidth = 500;// 菜单leftV的宽度
	private int rightViewWidth = 0;
	private int leftTouchX = 30;
	private int lastTouchX = 0;// 上一次手触屏的x坐标
	private int curTouchX = 0;// 当前手触屏的x坐标
	private int viewPosition = 0;// 当前viewgroup的位置
	private boolean isSlidingPrepared = false;
	private boolean isMenuShow = true;

	public SlidingMenu(Context context) {
		super(context);
		scroller = new Scroller(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int measureWidth = measureWidth(widthMeasureSpec);
		int measureHeight = measureHeight(heightMeasureSpec);
		measureChildren(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(measureWidth, measureHeight);
	}

	private int measureWidth(int pWidthMeasureSpec) {
		int result = 0;
		int widthMode = MeasureSpec.getMode(pWidthMeasureSpec);// 得到模式
		int widthSize = MeasureSpec.getSize(pWidthMeasureSpec);// 得到尺寸
		switch (widthMode) {
		case MeasureSpec.AT_MOST:
		case MeasureSpec.EXACTLY:
			result = widthSize;
			break;
		}
		return result;
	}

	private int measureHeight(int pHeightMeasureSpec) {
		int result = 0;

		int heightMode = MeasureSpec.getMode(pHeightMeasureSpec);
		int heightSize = MeasureSpec.getSize(pHeightMeasureSpec);
		switch (heightMode) {
		case MeasureSpec.AT_MOST:
		case MeasureSpec.EXACTLY:
			result = heightSize;
			break;
		}
		return result;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View childView = getChildAt(i);
			int measuredHeight = getChildAt(childCount - 1).getMeasuredHeight();
			if (i == 0) {
				childView.layout(0, 0, leftViewWidth, measuredHeight);
			} else if (i == 1) {
				int measuredWidth = childView.getMeasuredWidth();
				childView.layout(leftViewWidth, 0, leftViewWidth + measuredWidth, measuredHeight);
			}
		}
	}

	public View getLeftV() {
		return leftV;
	}

	public void setLeftV(View leftV) {
		this.leftV = leftV;
		measureView(leftV);
		addView(this.leftV);
		leftViewWidth = leftV.getMeasuredWidth();
		// setViewOnTouchListener(leftV);
	}

	public View getRightV() {
		return rightV;
	}

	private void setViewOnTouchListener(View view) {
		view.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				LogUtil.i("chen", "setViewOnTouchListener222");
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					isSlidingPrepared = true;
				case MotionEvent.ACTION_MOVE:
					break;
				case MotionEvent.ACTION_UP:
					break;
				}
				return false;
			}
		});
	}

	public void setRightV(View rightV) {
		this.rightV = rightV;
		measureView(rightV);
		addView(this.rightV);
		rightViewWidth = rightV.getMeasuredWidth();
	}

	int ncouts = 0;

	@Override
	public void computeScroll() {
		if (scroller.computeScrollOffset()) {
			scrollTo(scroller.getCurrX(), 0);
			postInvalidate();
		}
		super.computeScroll();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// if (!isSlidingPrepared) {
		// return true;
		// }
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			// lastTouchX = (int) event.getX();
			// return true;
		case MotionEvent.ACTION_MOVE:
			// curTouchX = (int) event.getX();
			// distance = lastTouchX - curTouchX;
			// if ((viewPosition + distance) <= leftViewWidth && (viewPosition +
			// distance) >= 0) {
			// scroller.startScroll(viewPosition, 0, distance, 0, 500);
			// invalidate();
			// lastTouchX = curTouchX;
			// viewPosition += distance;
			// }
			break;
		case MotionEvent.ACTION_CANCEL:
			break;
		case MotionEvent.ACTION_UP:
			if (viewPosition >= (leftViewWidth / 5 * 2)) {// 向左滑动,show
				// scroller.startScroll(viewPosition, 0, leftViewWidth -
				// viewPosition, 0, 1000);
				// viewPosition = leftViewWidth;
				// invalidate();
				showRightView();
			} else {// 向右滑动,show
				// scroller.startScroll(viewPosition, 0, -viewPosition, 0,
				// 1000);
				// viewPosition = 0;
				// invalidate();
				showLeftView();
			}
			// isSlidingPrepared = false;
			break;
		}

		return true;
	}

	private void showLeftView() {
		isMenuShow = true;
		scroller.startScroll(viewPosition, 0, -viewPosition, 0, 200);
		invalidate();
		viewPosition = 0;
	}

	private void showRightView() {
		isMenuShow = false;
		scroller.startScroll(viewPosition, 0, leftViewWidth - viewPosition, 0, 200);
		invalidate();
		viewPosition = leftViewWidth;
	}

	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		// switch (event.getAction()) {
		// case MotionEvent.ACTION_DOWN:
		// int x = (int) event.getX();
		// if (x <= leftTouchX) {
		// isSlidingPrepared = true;
		// } else if (isMenuShow && x <= (leftViewWidth + leftTouchX)) {
		// isSlidingPrepared = true;
		// }
		// break;
		// case MotionEvent.ACTION_MOVE:
		// break;
		// case MotionEvent.ACTION_UP:
		// break;
		// }
		return super.onInterceptTouchEvent(event);
	}

	public void showMenu() {
		if (isMenuShow) {
			showRightView();
		} else {
			showLeftView();
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			lastTouchX = (int) event.getX();
			if (isMenuShow) {
				if (lastTouchX <= (leftTouchX + leftViewWidth)) {
					isSlidingPrepared = true;
				}
			} else if (lastTouchX < leftTouchX) {
				isSlidingPrepared = true;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (isSlidingPrepared) {
				curTouchX = (int) event.getX();
				distance = lastTouchX - curTouchX;
				if ((viewPosition + distance) <= leftViewWidth && (viewPosition + distance) >= 0) {
					scroller.startScroll(viewPosition, 0, distance, 0, 500);
					invalidate();
					lastTouchX = curTouchX;
					viewPosition += distance;
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			if (isSlidingPrepared) {
				if (viewPosition >= (leftViewWidth / 5 * 2)) {// 向左滑动,show
					showRightView();
				} else {// 向右滑动,show
					showLeftView();
				}
				isSlidingPrepared = false;
			}
			break;
		}
		return super.dispatchTouchEvent(event);
	}

}
