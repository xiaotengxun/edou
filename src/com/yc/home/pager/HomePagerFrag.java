package com.yc.home.pager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

import com.edoucell.ych.R;
import com.yc.log.LogUtil;
import com.yc.manage.PhonePaperAct;

public class HomePagerFrag extends Fragment {

	private GridView functionList;
	private WebView webViewStore;
	private HomePagerRecomandAdapter recomandAdapter;
	private HomePagerManageAdapter managerAdapter;
	private int[] recomdPicArray = new int[] { R.drawable.pic1, R.drawable.pic1, R.drawable.pic1, R.drawable.pic1,
			R.drawable.pic1, R.drawable.pic1, R.drawable.pic1, R.drawable.pic1 };
	private int[] managerPicArray = new int[] { R.drawable.manage_icon, R.drawable.quanquan_icon,
			R.drawable.zhoubian_icon, R.drawable.duoshou, R.drawable.manager_store_icon,
			R.drawable.manager_inform_icon, R.drawable.manager_fix_icon, R.drawable.manager_phone_icon };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.home_pager, null);
	}

	private void initWebView() {
		webViewStore.getSettings().setJavaScriptEnabled(true);
		webViewStore.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		webViewStore.loadUrl(getString(R.string.homepager_store_url));
		webViewStore.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Log.i("chen", "url=" + url);
				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
			}
		});
	}

	private void initView() {
		webViewStore = (WebView) getView().findViewById(R.id.webview);
		recomandAdapter = new HomePagerRecomandAdapter(getActivity(), recomdPicArray);
		functionList = (GridView) getView().findViewById(R.id.function_list);
		managerAdapter = new HomePagerManageAdapter(getActivity(), managerPicArray);
		functionList.setAdapter(managerAdapter);
		functionList.setOnItemClickListener(functionListItemOnClick);
		initWebView();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	private AdapterView.OnItemClickListener functionListItemOnClick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			int tag = (Integer)((ImageView)view).getTag();
			switch (tag) {
			case R.drawable.manage_icon:
				LogUtil.i("chen", "manage_icon");
				break;
			case R.drawable.duoshou:
				LogUtil.i("chen", "duoshou");
				break;
			case R.drawable.quanquan_icon:
				LogUtil.i("chen", "quanquan_icon");
				break;
			case R.drawable.zhoubian_icon:
				LogUtil.i("chen", "zhoubian_icon");
				break;
			case R.drawable.manager_store_icon:
				LogUtil.i("chen", "manager_store_icon");
				break;
			case R.drawable.manager_inform_icon:
				LogUtil.i("chen", "manager_inform_icon");
				break;
			case R.drawable.manager_phone_icon:
				getActivity().startActivity(new Intent(getActivity(), PhonePaperAct.class));
				LogUtil.i("chen", "manager_phone_icon");
				break;
			case R.drawable.manager_fix_icon:
				LogUtil.i("chen", "manager_fix_icon");
				break;
			}
		}
	};

}