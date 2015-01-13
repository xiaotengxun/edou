package com.yc.user;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.edoucell.ych.R;
import com.yc.user.LoginInFrag.OnChangeRegisterClick;
import com.yc.view.tools.TopBarView;

public class LoginAct extends FragmentActivity implements OnChangeRegisterClick {
	private ImageView back;
	private TextView topTitle;
	private LoginInFrag loginInFrag;
	private LoginRegisterFrag loginRegisterFrag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		TopBarView topBar = (TopBarView) findViewById(R.id.topbar);
		topBar.setActivity(this);
		initView();
	}

	private void initView() {
		back = (ImageView) findViewById(R.id.bar_back);
		topTitle = (TextView) findViewById(R.id.top_title);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		loginInFrag = new LoginInFrag();
		loginRegisterFrag = new LoginRegisterFrag();
		loginInFrag.setChangeTORegister(this);
		FragmentTransaction transation = getSupportFragmentManager().beginTransaction();
		transation.replace(R.id.container, loginInFrag);
		transation.commit();
	}

	@Override
	public void goToRegister() {
		topTitle.setText(getString(R.string.login_register));
		FragmentTransaction transation = getSupportFragmentManager().beginTransaction();
		transation.replace(R.id.container, loginRegisterFrag);
		transation.commit();

	}
}
