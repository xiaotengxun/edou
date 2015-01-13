package com.yc.user;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.edoucell.ych.R;
import com.yc.view.tools.TopBarView;

public class UserInfoAct extends Activity {
	private EditText regUserName, regUserPhone, regUserMail, regUserQQ;
	private EditText regUserType, regUserBuildName, regUserBuildNumber, regUserHouseNumber, regUserDescription;
	private ImageView editView;
	private Button submit;
	private RelativeLayout foreColor;
	private Spinner userType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_user_info);
		TopBarView topBar = (TopBarView) findViewById(R.id.topbar);
		topBar.setActivity(this);
		initView();
	}

	private void initView() {
		regUserName = (EditText) findViewById(R.id.register_true_name);
		regUserPhone = (EditText) findViewById(R.id.register_phone_number);
		regUserMail = (EditText) findViewById(R.id.register_email);
		regUserQQ = (EditText) findViewById(R.id.register_qq);

		regUserBuildName = (EditText) findViewById(R.id.register_building_name);
		regUserBuildNumber = (EditText) findViewById(R.id.register_building_number);
		regUserHouseNumber = (EditText) findViewById(R.id.register_house_number);
		regUserDescription = (EditText) findViewById(R.id.register_user_description);
		userType = (Spinner) findViewById(R.id.register_type);

		submit = (Button) findViewById(R.id.user_info_submit);
		editView = (ImageView) findViewById(R.id.user_info_edit);
		foreColor = (RelativeLayout) findViewById(R.id.user_info_forecolor);
		editView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				openEdit(true);
			}
		});
		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				openEdit(false);
				subInfoToServer();
			}
		});
	}

	private void subInfoToServer() {

	}

	private void openEdit(boolean isOpen) {
		regUserName.setFocusableInTouchMode(isOpen);
		regUserPhone.setFocusableInTouchMode(isOpen);
		regUserMail.setFocusableInTouchMode(isOpen);
		regUserQQ.setFocusableInTouchMode(isOpen);

		regUserBuildName.setFocusableInTouchMode(isOpen);
		regUserBuildNumber.setFocusableInTouchMode(isOpen);
		regUserHouseNumber.setFocusableInTouchMode(isOpen);
		regUserDescription.setFocusableInTouchMode(isOpen);
		userType.setClickable(isOpen);
		if (isOpen) {
			foreColor.setVisibility(View.INVISIBLE);
			submit.setVisibility(View.VISIBLE);
		} else {
			foreColor.setVisibility(View.VISIBLE);
			submit.setVisibility(View.INVISIBLE);

			regUserName.setFocusable(isOpen);
			regUserPhone.setFocusable(isOpen);
			regUserMail.setFocusable(isOpen);
			regUserQQ.setFocusable(isOpen);
			regUserBuildName.setFocusable(isOpen);
			regUserBuildNumber.setFocusable(isOpen);
			regUserHouseNumber.setFocusable(isOpen);
			regUserDescription.setFocusable(isOpen);
		}
	}
}
