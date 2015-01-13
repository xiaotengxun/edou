package com.yc.container;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edoucell.ych.R;
import com.yc.attr.PrefAttr;
import com.yc.log.LogUtil;
import com.yc.manage.BroadcastInfoAct;
import com.yc.manage.ManagerAct;
import com.yc.user.LoginAct;
import com.yc.user.UserInfoAct;

public abstract class SlidingMenuAct extends AbSlidingAct {
	private TextView loginTextView;
	private LinearLayout linUserInfo, linUserMessage, linUserSettings, linUserManager;
	private ImageView logoView;

	@Override
	protected ViewGroup createLeftView() {
		ViewGroup view = (ViewGroup) getLayoutInflater().inflate(R.layout.left_menu, null);
		view.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		return view;
	}

	private void initView() {
		loginTextView = (TextView) findViewById(R.id.user_login);
		linUserInfo = (LinearLayout) findViewById(R.id.menu_user_info);
		linUserMessage = (LinearLayout) findViewById(R.id.menu_user_message);
		linUserManager = (LinearLayout) findViewById(R.id.menu_user_manager);
		logoView = (ImageView) findViewById(R.id.menu_user_logo);
	}

	private void setViewOnClickListener() {
		loginTextView.setOnClickListener(MenuOnclick);
		linUserInfo.setOnClickListener(MenuOnclick);
		linUserMessage.setOnClickListener(MenuOnclick);
		linUserManager.setOnClickListener(MenuOnclick);
	}

	private OnClickListener MenuOnclick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.user_login:
				startActivity(new Intent(SlidingMenuAct.this, LoginAct.class));
				break;
			case R.id.menu_user_info:
				startActivity(new Intent(SlidingMenuAct.this, UserInfoAct.class));
				break;
			case R.id.menu_user_message:
				startActivity(new Intent(SlidingMenuAct.this, BroadcastInfoAct.class));
				break;
			case R.id.menu_user_settings:
				break;
			case R.id.menu_user_manager:
				startActivity(new Intent(SlidingMenuAct.this, ManagerAct.class));
				break;

			default:
				break;
			}
			changeMenu();

		}
	};

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
		setViewOnClickListener();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		if (getSharedPreferences(PrefAttr.sharePrefenceKey, 0).getBoolean(PrefAttr.isLoginSuccess, false)) {
			logoView.setVisibility(View.VISIBLE);
//			loginTextView.setText(getSharedPreferences(PrefAttr.sharePrefenceKey, 0).getString(PrefAttr.userName, ""));
			loginTextView.setText(getString(R.string.user_logined));
		}
		super.onResume();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}
