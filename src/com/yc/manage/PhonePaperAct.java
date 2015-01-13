package com.yc.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.edoucell.ych.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yc.log.LogUtil;
import com.yc.model.Booktype;
import com.yc.utils.HttpUtil;
import com.yc.view.tools.TopBarView;

public class PhonePaperAct extends Activity {
	private ListView listPhoneView;
	private PhonePagerTypeAdapter adapter;
	private ImageView backBtn;
	List<List<HashMap<String, String>>> listContent = new ArrayList<List<HashMap<String, String>>>();
	List<String> listType = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manager_phone);
		TopBarView topBar = (TopBarView) findViewById(R.id.topbar);
		topBar.setActivity(this);
		initView();
		getPhoneNumber();
	}

	private void initData() {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> hash = new HashMap<String, String>();
		hash.put("company", "济南公司");
		hash.put("phone", "18232424234");
		hash.put("owner", "陈学呆");
		hash.put("phone", "11");
		list.add(new HashMap<String, String>(hash));
		hash.put("phone", "22");
		list.add(new HashMap<String, String>(hash));
		hash.put("phone", "33");
		list.add(new HashMap<String, String>(hash));
		hash.put("phone", "44");
		list.add(new HashMap<String, String>(hash));
		hash.put("phone", "55");
		list.add(new HashMap<String, String>(hash));
		hash.put("phone", "66");
		list.add(new HashMap<String, String>(hash));

		listContent.add(new ArrayList<HashMap<String, String>>(list));
		listContent.add(new ArrayList<HashMap<String, String>>(list));
		listContent.add(new ArrayList<HashMap<String, String>>(list));
		listContent.add(new ArrayList<HashMap<String, String>>(list));
		listContent.add(new ArrayList<HashMap<String, String>>(list));
		listContent.add(new ArrayList<HashMap<String, String>>(list));

		listType.add("A");
		listType.add("A");
		listType.add("A");
		listType.add("A");
		listType.add("A");
		listType.add("A");
	}

	private void initView() {
		initData();
		listPhoneView = (ListView) findViewById(R.id.lsview);
		adapter = new PhonePagerTypeAdapter(getApplicationContext(), listType, listContent);
		listPhoneView.setAdapter(adapter);
		backBtn = (ImageView) findViewById(R.id.bar_back);
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void getPhoneNumber() {
		String url = getString(R.string.url_domain) + getString(R.string.url_phone_note);
		HttpUtil.get(url, asyncHttpResponseHandler);
	}

	private JsonHttpResponseHandler asyncHttpResponseHandler = new JsonHttpResponseHandler() {

		@Override
		public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
			LogUtil.i("chen", "   response failed");
			super.onFailure(statusCode, headers, throwable, errorResponse);
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
			LogUtil.i("chen", "  3333response =" + response);
			List<Booktype> books = new ArrayList<Booktype>();
			GsonBuilder gb = new GsonBuilder();
			Gson g = gb.create();
			books = g.fromJson(response.toString(), new TypeToken<List<Booktype>>() {
			}.getType());
			super.onSuccess(statusCode, headers, response);
		}

		@Override
		public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
			LogUtil.i("chen", "   response failed");
			super.onFailure(statusCode, headers, responseString, throwable);
		}

		@Override
		public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
			LogUtil.i("chen", "   response failed");
			super.onFailure(statusCode, headers, throwable, errorResponse);
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
			LogUtil.i("chen", "  111111response =" + response);
			super.onSuccess(statusCode, headers, response);
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers, String responseString) {
			LogUtil.i("chen", "22222  response =" + responseString);
			super.onSuccess(statusCode, headers, responseString);
		}

		@Override
		protected Object parseResponse(byte[] responseBody) throws JSONException {
			LogUtil.i("chen", "  response = object  byte[]");
			return super.parseResponse(responseBody);
		}
	};
}
