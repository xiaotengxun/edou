package com.yc.container;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.edoucell.ych.R;
import com.yc.attr.PrefAttr;
import com.yc.baidu.map.MapAct;
import com.yc.baidu.map.MapPlaceAdapter;
import com.yc.home.pager.HomePagerFrag;
import com.yc.life.LifeCareFrag;
import com.yc.log.LogUtil;
import com.yc.quanquan.QuanQuanFrag;
import com.yc.shenqian.CheapShopFrag;
import com.yc.view.tools.MarQueeTextView;
import com.yc.zhoubian.ZhouBianFrag;

public abstract class SlidingContentAct extends SlidingMenuAct implements OnGetPoiSearchResultListener {
	private FragmentTabHost mTabHost;
	private ImageView menuView, searchView;
	private MarQueeTextView topTitle;
	private LayoutInflater layoutInflater;
	private MapPlaceAdapter placeAdapter;
	private ListView recomdPlaceListView;
	private RelativeLayout recomandPlaceShow;// 显示搜索到的地方
	private View refresh;// 刷新
	private Handler mHandler;
	private static final int UPDATE_PLACE_DELAY = 1000;// 延迟更新搜索到的地点
	private static final int REFRESH_BEGIN = 1;// 开始刷新
	private static final int REFRESH_ING = 2;// 正在刷新
	private static final int REFRESH_OVER = 3;// 刷新结束
	private static final int PLACE_SHOW = 4;// 显示搜索到的
	private Animation slide_down, slide_up;
	private List<String> listPlace = new ArrayList<String>();
	private Drawable upIconView, downIconView, leftIcomView;
	private SDKReceiver mReceiver;
	private MapView mapView;
	private BaiduMap baiduMap;
	private PoiSearch search;
	private LocationClient mLocClient;
	private View topBarRightBtn;
	private static ZhouBianSearchListener zhouBianBeginSearchListener;

	private Class fragmentArray[] = { HomePagerFrag.class, QuanQuanFrag.class, ZhouBianFrag.class, CheapShopFrag.class,
			LifeCareFrag.class };

	private int mImageViewArray[] = { R.drawable.tab_home_btn, R.drawable.tab_message_btn, R.drawable.tab_selfinfo_btn,
			R.drawable.tab_square_btn, R.drawable.tab_more_btn };

	private String[] mTextviewArray;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mTextviewArray = getResources().getStringArray(R.array.tab_array);
		initView();
		initReceiver();
		initMap();
		firstLocate();
	}

	/**
	 */
	private void initView() {
		topBarRightBtn = (View) findViewById(R.id.top_bar_right_rel);
		refresh = (View) findViewById(R.id.refresh_all);
		layoutInflater = LayoutInflater.from(this);
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		int count = fragmentArray.length;

		for (int i = 0; i < count; i++) {
			TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
			mTabHost.addTab(tabSpec, fragmentArray[i], null);
			mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
		}
		menuView = (ImageView) findViewById(R.id.menu_slide);
		searchView = (ImageView) findViewById(R.id.location_search);
		topTitle = (MarQueeTextView) findViewById(R.id.bar_title);
		menuView.setClickable(true);
		menuView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AbSlidingAct.slidingMenu.showMenu();
			}
		});
		searchView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (zhouBianBeginSearchListener != null) {
					zhouBianBeginSearchListener.beginSearch();
				}
			}
		});

		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case PLACE_SHOW:
					updatePlace();
					break;
				}
			}
		};
		leftIcomView = getResources().getDrawable(R.drawable.locate_icon);
		leftIcomView.setBounds(0, 0, leftIcomView.getMinimumWidth(), leftIcomView.getMinimumHeight());
		upIconView = getResources().getDrawable(R.drawable.expand_up);
		upIconView.setBounds(0, 0, upIconView.getMinimumWidth(), upIconView.getMinimumHeight());
		downIconView = getResources().getDrawable(R.drawable.expand_down);
		downIconView.setBounds(0, 0, downIconView.getMinimumWidth(), downIconView.getMinimumHeight());
		slide_down = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.listview_slide_down);
		slide_up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.listview_slide_up);
		placeAdapter = new MapPlaceAdapter(getApplicationContext(), listPlace);
		recomandPlaceShow = (RelativeLayout) findViewById(R.id.recomand_locate_show);
		recomdPlaceListView = (ListView) findViewById(R.id.recomand_locate_place);
		recomdPlaceListView.setAdapter(placeAdapter);
		topTitle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				changeTitleIcon();
			}
		});
		recomdPlaceListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				topTitle.setText(listPlace.get(position));
				getSharedPreferences(PrefAttr.sharePrefenceKey, 0).edit()
						.putString(PrefAttr.localPlaceKey, listPlace.get(position)).commit();
			}
		});

		ZhouBianFrag.setOnShowSearchBtnListener(new ZhouBianSearchBtnShowListener() {

			@Override
			public void showSearchBtn() {
				topBarRightBtn.setVisibility(View.VISIBLE);
			}

			@Override
			public void hideSearchBtn() {
				topBarRightBtn.setVisibility(View.INVISIBLE);

			}
		});
	}

	public static void setOnBeginSearchListener(ZhouBianSearchListener sListener) {
		zhouBianBeginSearchListener = sListener;
	}

	private void initMap() {
		mapView = (MapView) findViewById(R.id.bmapsView);
		baiduMap = mapView.getMap();
	}

	/**
	 * 定位自己的坐标
	 */
	private void locate() {
		baiduMap.setMyLocationEnabled(true);
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(new LocationListener());
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
	}

	/**
	 * 百度地图授权注册事件
	 */
	private void initReceiver() {
		IntentFilter iFilter = new IntentFilter();
		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		mReceiver = new SDKReceiver();
		registerReceiver(mReceiver, iFilter);
	}

	/**
	 * 第一次定位
	 */
	private void firstLocate() {
		String place = getSharedPreferences(PrefAttr.sharePrefenceKey, 0).getString(PrefAttr.localPlaceKey, "");
		if (place.equals("")) {
			locate();
		} else {
			topTitle.setText(place);
		}
	}

	/**
	 * 更新地点右边图标
	 */
	private void changeTitleIcon() {
		if (recomandPlaceShow.getVisibility() == View.VISIBLE) {
			topTitle.setCompoundDrawables(leftIcomView, null, downIconView, null);
			recomandPlaceShow.clearAnimation();
			recomandPlaceShow.startAnimation(slide_up);
			recomandPlaceShow.setVisibility(View.INVISIBLE);
		} else {
			topTitle.setCompoundDrawables(leftIcomView, null, upIconView, null);
			// recomandPlaceShow.clearAnimation();
			// recomandPlaceShow.startAnimation(slide_down);
			// recomandPlaceShow.setVisibility(View.VISIBLE);
			locate();
		}
	}

	/**
	 * 更新搜索到的周边住宅区
	 */
	private void updatePlace() {
		placeAdapter.setPlace(listPlace);
		placeAdapter.notifyDataSetChanged();
		recomandPlaceShow.setVisibility(View.VISIBLE);
		recomandPlaceShow.clearAnimation();
		recomandPlaceShow.startAnimation(slide_down);
	}

	/**
	 */
	private View getTabItemView(int index) {
		View view = layoutInflater.inflate(R.layout.tab_item_view, null);

		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		imageView.setImageResource(mImageViewArray[index]);
		TextView textView = (TextView) view.findViewById(R.id.textview);
		textView.setText(mTextviewArray[index]);

		return view;
	}

	/**
	 * 构造广播监听类，监听 SDK key 验证以及网络异常广播
	 */

	public class SDKReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			String s = intent.getAction();
			if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
				Toast.makeText(getApplicationContext(), getString(R.string.authorize_error), 2000).show();
				;
			} else if (s.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
				Toast.makeText(getApplicationContext(), getString(R.string.net_error), 2000).show();
			}
		}
	}

	@Override
	protected ViewGroup createRightView() {
		ViewGroup view = (ViewGroup) getLayoutInflater().inflate(R.layout.right_content, null);
		view.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		return view;
	}

	@Override
	protected void onPause() {
		LogUtil.i("chen", "onPause");
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private class LocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation arg0) {
			// TODO Auto-generated method stub
			if (arg0 != null) {
				mLocClient.stop();
				String s = arg0.getAddrStr() + "    " + arg0.getPoi() + "   " + arg0.getStreet() + "   "
						+ arg0.getStreetNumber();
				LogUtil.i("chen", s);
				LatLng la = new LatLng(Double.valueOf(arg0.getLatitude()), Double.valueOf(arg0.getLongitude()));
				setLocation(la);
				initSearch(la);
			}

		}

		@Override
		public void onReceivePoi(BDLocation arg0) {
			if (arg0 != null) {
			}
		}
	};

	/**
	 * 在地图上显示坐标
	 * 
	 * @param lt
	 */
	private void setLocation(LatLng lt) {
		MapStatus status = new MapStatus.Builder().target(lt).zoom(17).build();
		MapStatusUpdate update = MapStatusUpdateFactory.newMapStatus(status);
		baiduMap.setMapStatus(update);
		Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.icon_marka);
		BitmapDescriptor d = BitmapDescriptorFactory.fromBitmap(b);
		OverlayOptions ooA = new MarkerOptions().position(lt).icon(d).zIndex(9).draggable(true);
		Marker mMarkerA = (Marker) (baiduMap.addOverlay(ooA));

	}

	private void initSearch(LatLng la) {
		search = PoiSearch.newInstance();
		search.setOnGetPoiSearchResultListener(this);
		search.searchNearby(new PoiNearbySearchOption().location(la).keyword("住宅小区").pageCapacity(20).radius(1000));
	}

	@Override
	public void onGetPoiDetailResult(PoiDetailResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetPoiResult(PoiResult arg0) {
		if (arg0 != null) {
			List<PoiInfo> list = arg0.getAllPoi();
			String s = "";
			if (list != null) {
				for (PoiInfo l : list) {
					// s = l.address + "   " + l.city + "  " + l.name + "  " +
					// l.phoneNum + "   " + l.postCode + "   \n";
					// LogUtil.i("chen", s);
					listPlace.add(l.name);
				}
				mHandler.sendEmptyMessageDelayed(PLACE_SHOW, UPDATE_PLACE_DELAY);
			} else {
				LogUtil.i("chen", "onGetPoiResult    list=nullllll");
			}
		} else {
			LogUtil.i("chen", "onGetPoiResult =nullllll");
		}

	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(mReceiver);
		super.onDestroy();
	}

}
