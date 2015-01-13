package com.yc.user;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.edoucell.ych.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yc.attr.PrefAttr;
import com.yc.log.LogUtil;
import com.yc.utils.HttpUtil;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

public class LoginInFrag extends Fragment {
	private EditText inputName, inputPwd;
	private String userNameKey = "userNameKey";
	private String userPwd = "userPwd";
	private String autoLoginKey = "autoLoginKey";
	private String remPwdKey = "remPwdKey";
	private Button loginBtn, registerBtn;
	private CheckBox remPwd, autoLogin;
	private Activity act = null;
	private OnChangeRegisterClick onChangeRegisterClick = null;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		act = getActivity();
		initView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.login_in, null);
	}

	private void testHttp() {
		String url = "http://192.168.1.101:8080/edou/user/addacc.do?";
		RequestParams param = new RequestParams();
		param.add("account", "chenshuwan");
		param.add("pwd", "chenshuwan");
		HttpUtil.get(url, param, asyncHttpResponseHandler);
	}

	private void initView() {
		inputName = (EditText) getView().findViewById(R.id.login_name);
		inputPwd = (EditText) getView().findViewById(R.id.login_pwd);
		loginBtn = (Button) getView().findViewById(R.id.login_in);
		registerBtn = (Button) getView().findViewById(R.id.login_register);
		remPwd = (CheckBox) getView().findViewById(R.id.login_rem_pwd);
		autoLogin = (CheckBox) getView().findViewById(R.id.login_auto);
		loginBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		registerBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (onChangeRegisterClick != null) {
					onChangeRegisterClick.goToRegister();
				}

			}
		});
		remPwd.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				act.getSharedPreferences(PrefAttr.sharePrefenceKey, 0).edit().putBoolean(remPwdKey, isChecked);
			}
		});
		autoLogin.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				act.getSharedPreferences(PrefAttr.sharePrefenceKey, 0).edit().putBoolean(autoLoginKey, isChecked);
			}
		});
	}

	private void beginLogin() {
		if(inputName.getText().toString().equals("")|| inputPwd.getText().toString().equals("")){
			Toast.makeText(act, getString(R.string.user_login_error_tip), 1000).show();
		}else{
			
		}
	}

	private JsonHttpResponseHandler asyncHttpResponseHandler = new JsonHttpResponseHandler() {

		@Override
		public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
			LogUtil.i("chen", " asyncHttpResponseHandler  response failed");
			super.onFailure(statusCode, headers, throwable, errorResponse);
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
			LogUtil.i("chen", "response=666");
			super.onSuccess(statusCode, headers, response);
		}

		@Override
		public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
			LogUtil.i("chen", " asyncHttpResponseHandler  response failed");
			super.onFailure(statusCode, headers, responseString, throwable);
		}

		@Override
		public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
			LogUtil.i("chen", " asyncHttpResponseHandler  response failed");
			super.onFailure(statusCode, headers, throwable, errorResponse);
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
			LogUtil.i("chen", "response 333=" + response);
			super.onSuccess(statusCode, headers, response);
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers, String responseString) {
			// LogUtil.i("chen", "response 222");
			super.onSuccess(statusCode, headers, responseString);
		}

		@Override
		protected Object parseResponse(byte[] responseBody) throws JSONException {
			// LogUtil.i("chen", "response 111="+responseBody);
			return super.parseResponse(responseBody);
		}
	};

	private void saveInfo(String key, String info) {
		act.getSharedPreferences(PrefAttr.sharePrefenceKey, 0).edit().putString(key, info).commit();
	}

	private String getInfo(String key, String defaultValue) {
		return act.getSharedPreferences(PrefAttr.sharePrefenceKey, 0).getString(key, defaultValue);
	}

	public void setChangeTORegister(OnChangeRegisterClick click) {
		this.onChangeRegisterClick = click;
	}

	public interface OnChangeRegisterClick {
		public void goToRegister();
	};
}
