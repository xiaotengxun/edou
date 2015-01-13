package com.yc.container;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.edoucell.ych.R;
import com.yc.log.LogUtil;

public abstract class AbSlidingAct extends FragmentActivity {
	public static SlidingMenu slidingMenu;

	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(arg0);
		slidingMenu = new SlidingMenu(this);
		slidingMenu.setLeftV(createLeftView());
		slidingMenu.setRightV(createRightView());
		LinearLayout view = (LinearLayout) getLayoutInflater().inflate(R.layout.container, null);
		LinearLayout lin = new LinearLayout(getApplicationContext());
		LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		lin.setLayoutParams(param);
		lin.requestLayout();
		ViewGroup.LayoutParams p = new android.view.ViewGroup.LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.FILL_PARENT);
		slidingMenu.setLayoutParams(p);
		slidingMenu.requestLayout();
		lin.addView(slidingMenu);
		view.addView(lin);
		setContentView(view);
		slidingMenu.showMenu();
	}

	public static void  changeMenu(){
		slidingMenu.showMenu();
	}
	abstract protected ViewGroup createLeftView();

	abstract protected ViewGroup createRightView();

//	abstract protected void Show();

}
