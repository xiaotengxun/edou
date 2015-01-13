package com.yc.baidu.map;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
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
import android.preference.Preference;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.edoucell.ych.R;
import com.yc.attr.PrefAttr;
import com.yc.log.LogUtil;

public class MapAct extends Activity implements OnGetSuggestionResultListener, OnGetPoiSearchResultListener {
	private ImageView backImageView;// 返回键
	private TextView locatePlace;
	private Drawable upIconView, downIconView;
	private SDKReceiver mReceiver;
	private MapView mapView;
	private BaiduMap baiduMap;
	private PoiSearch search;
	private LocationClient mLocClient;
	private SuggestionSearch suggest;
	private List<String> listPlace = new ArrayList<String>();
	private MapPlaceAdapter placeAdapter;
	private ListView recomdPlaceListView;
	private RelativeLayout recomandPlaceShow;
	private Handler mHandler;
	private static final int UPDATE_PLACE_DELAY = 1000;//延迟更新搜索到的地点
	private Animation slide_down, slide_up;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.right_content_locate);
		initView();
		initMap();
		locate();
	}

	private void initView() {
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				updatePlace();
			}
		};
		slide_down = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.listview_slide_down);
		slide_up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.listview_slide_up);
		locatePlace = (TextView) findViewById(R.id.located_place);
		upIconView = getResources().getDrawable(R.drawable.expand_up);
		upIconView.setBounds(0, 0, upIconView.getMinimumWidth(), upIconView.getMinimumHeight());
		downIconView = getResources().getDrawable(R.drawable.expand_down);
		downIconView.setBounds(0, 0, downIconView.getMinimumWidth(), downIconView.getMinimumHeight());
		placeAdapter = new MapPlaceAdapter(getApplicationContext(), listPlace);
		recomandPlaceShow = (RelativeLayout) findViewById(R.id.recomand_locate_show);
		recomdPlaceListView = (ListView) findViewById(R.id.recomand_locate_place);
		recomdPlaceListView.setAdapter(placeAdapter);
		backImageView = (ImageView) findViewById(R.id.bar_back);
		backImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		locatePlace.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				changeTitleIcon();
			}
		});
		recomdPlaceListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				locatePlace.setText(listPlace.get(position));
				getSharedPreferences(PrefAttr.sharePrefenceKey, 0).edit()
						.putString(PrefAttr.localPlaceKey, listPlace.get(position)).commit();
			}
		});
	}

	/**
	 * 更新搜索到的周边住宅区
	 */
	private void updatePlace() {
		placeAdapter.setPlace(listPlace);
		placeAdapter.notifyDataSetChanged();
		recomandPlaceShow.setVisibility(View.VISIBLE);
		// recomdPlaceListView.setAnimation(slide_down);
		recomandPlaceShow.startAnimation(slide_down);
	}

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
		suggest = SuggestionSearch.newInstance();
		search.setOnGetPoiSearchResultListener(this);
		suggest.setOnGetSuggestionResultListener(this);
		search.searchNearby(new PoiNearbySearchOption().location(la).keyword("住宅小区").pageCapacity(20).radius(10000));
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
	 * 更新地点右边图标
	 */
	private void changeTitleIcon() {
		if (recomandPlaceShow.getVisibility() == View.VISIBLE) {
			locatePlace.setCompoundDrawables(null, null, upIconView, null);
			recomandPlaceShow.clearAnimation();
			recomandPlaceShow.startAnimation(slide_up);
			recomandPlaceShow.setVisibility(View.INVISIBLE);
		} else {
			locatePlace.setCompoundDrawables(null, null, downIconView, null);
			recomandPlaceShow.clearAnimation();
			recomandPlaceShow.startAnimation(slide_down);
			recomandPlaceShow.setVisibility(View.VISIBLE);
		}
	}

	private void initMap() {
		mapView = (MapView) findViewById(R.id.bmapsView);
		baiduMap = mapView.getMap();
	}

	private void initReceiver() {
		IntentFilter iFilter = new IntentFilter();
		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		mReceiver = new SDKReceiver();
		registerReceiver(mReceiver, iFilter);
	}

	/**
	 * 构造广播监听类，监听 SDK key 验证以及网络异常广播
	 */

	private class SDKReceiver extends BroadcastReceiver {
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

	private class LocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation arg0) {
			// TODO Auto-generated method stub
			if (arg0 != null) {
				mLocClient.stop();
				String s=arg0.getAddrStr()+"    "+arg0.getPoi()+"   "+arg0.getStreet()+"   "+arg0.getStreetNumber();
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

	@Override
	public void onGetPoiDetailResult(PoiDetailResult arg0) {
		String s = arg0.toString();
	}

	@Override
	public void onGetPoiResult(PoiResult arg0) {
		if (arg0 != null) {
			List<PoiInfo> list = arg0.getAllPoi();
			String s = "";
			if (list != null) {
				for (PoiInfo l : list) {
					s = l.address + "   " + l.city + "  " + l.name + "  " + l.phoneNum + "   " + l.postCode + "   \n";
					listPlace.add(l.name);

				}
				mHandler.sendEmptyMessageDelayed(0, UPDATE_PLACE_DELAY);
			} else {
				LogUtil.i("chen", "onGetPoiResult    list=nullllll");
			}
		} else {
			LogUtil.i("chen", "onGetPoiResult =nullllll");
		}

	}

	@Override
	public void onGetSuggestionResult(SuggestionResult arg0) {

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mapView != null) {
			mapView.destroyDrawingCache();
			mapView = null;
			baiduMap = null;
		}
	}
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

}
