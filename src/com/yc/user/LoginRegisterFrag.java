package com.yc.user;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.edoucell.ych.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yc.attr.PrefAttr;
import com.yc.log.LogUtil;
import com.yc.utils.HttpUtil;

public class LoginRegisterFrag extends Fragment {
	private EditText regLoginName, regLoginPwd, regLoginSurePwd;
	private Button btnRegister;
	private String name = "", pwd = "";
	private Handler mHandler;
	private static final int REGISTER_SUCCESS = 1;
	private static final int REGISTER_FAILED = 2;
	private AlertDialog dialog = null;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.login_register, null);
	}

	private void initDialog() {
		dialog = new AlertDialog.Builder(getActivity()).setView(
				LayoutInflater.from(getActivity()).inflate(R.layout.login_in_dialog, null)).create();
		dialog.setView(LayoutInflater.from(getActivity()).inflate(R.layout.login_in_dialog, null));
		dialog.show();
	}

	private void initView() {
		mHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case REGISTER_SUCCESS:
					String id = (String) msg.obj;
					getActivity().getSharedPreferences(PrefAttr.sharePrefenceKey, 0).edit()
							.putString(PrefAttr.userId, id).putString(PrefAttr.userName, name)
							.putBoolean(PrefAttr.isLoginSuccess, true).commit();
					dialog.cancel();
					registerSuccessAndNext();
					break;
				case REGISTER_FAILED:
					dialog.cancel();
					Toast.makeText(getActivity(), getString(R.string.user_register_error_tip), 1000).show();
					getActivity().getSharedPreferences(PrefAttr.sharePrefenceKey, 0).edit()
							.putBoolean(PrefAttr.isLoginSuccess, false).commit();
					break;
				}
			}

		};
		regLoginName = (EditText) getView().findViewById(R.id.register_name);
		regLoginPwd = (EditText) getView().findViewById(R.id.register_pwd);
		regLoginSurePwd = (EditText) getView().findViewById(R.id.register_sure_pwd);
		btnRegister = (Button) getView().findViewById(R.id.register_button);
		btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				beginRegister();
			}
		});
	}

	private boolean checkInfo() {
		name = regLoginName.getText().toString();
		pwd = regLoginPwd.getText().toString();
		Log.i("chen", name + "   " + pwd + "    " + regLoginSurePwd.getText().toString());
		if (name.equals("") || pwd.equals("") || !(pwd.equals(regLoginSurePwd.getText().toString()))) {
			regLoginName.setText("");
			regLoginPwd.setText("");
			regLoginSurePwd.setText("");
			Toast.makeText(getActivity(), getString(R.string.user_register_error_tip), 1000).show();
			return false;
		} else {
			return true;
		}
	}

	private void beginRegister() {
		if (checkInfo()) {
			String url = getString(R.string.url_domain) + getString(R.string.url_user_register);
			url = String.format(url, new String[] { name, pwd });
			initDialog();
			HttpUtil.get(url, asyncHttpResponseHandler);
			LogUtil.i("chen", "url=" + url);
		}
	}

	private void registerSuccessAndNext() {
		dialog = new AlertDialog.Builder(getActivity())
				.setPositiveButton(getString(R.string.btn_sure), new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						Intent intent = new Intent(getActivity(), UserInfoAct.class);
						getActivity().startActivity(intent);
					}
				}).setNegativeButton(getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						getActivity().finish();
					}
				}).create();
		dialog.show();
	}

	private JsonHttpResponseHandler asyncHttpResponseHandler = new JsonHttpResponseHandler() {

		@Override
		public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
			LogUtil.i("chen", " register  response failed");
			registerFailedSendMsg();
			super.onFailure(statusCode, headers, throwable, errorResponse);
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
			LogUtil.i("chen", " register response =" + response);
			super.onSuccess(statusCode, headers, response);
		}

		@Override
		public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
			LogUtil.i("chen", " register  response failed");
			registerFailedSendMsg();
			super.onFailure(statusCode, headers, responseString, throwable);
		}

		@Override
		public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
			LogUtil.i("chen", " register  response failed");
			registerFailedSendMsg();
			super.onFailure(statusCode, headers, throwable, errorResponse);
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
			LogUtil.i("chen", " register response =" + response);
			try {
				boolean suc = response.getBoolean(register.successKey);
				String sid = response.getString(register.idKey);
				Message msg = new Message();
				msg.obj = sid;
				if (suc) {
					msg.what = REGISTER_SUCCESS;
				} else {
					msg.what = REGISTER_FAILED;
				}
				mHandler.sendMessage(msg);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			super.onSuccess(statusCode, headers, response);
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers, String responseString) {
			LogUtil.i("chen", " register response =" + responseString);
			super.onSuccess(statusCode, headers, responseString);
		}

		@Override
		protected Object parseResponse(byte[] responseBody) throws JSONException {
			LogUtil.i("chen", " register response = object  byte[]");
			return super.parseResponse(responseBody);
		}
	};

	private void registerFailedSendMsg() {
		Message msg = new Message();
		msg.what = REGISTER_FAILED;
		mHandler.sendMessage(msg);
	}

	private interface register {
		String idKey = "sid";
		String successKey = "success";
		String msgKey = "msg";
	};
}
