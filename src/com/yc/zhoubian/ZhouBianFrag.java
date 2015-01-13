package com.yc.zhoubian;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
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
import com.yc.container.SlidingContentAct;
import com.yc.container.ZhouBianSearchBtnShowListener;
import com.yc.container.ZhouBianSearchListener;

public class ZhouBianFrag extends Fragment implements OnGetPoiSearchResultListener {
	private Activity act;
	private TextView tvCategory, tvDistance;
	private Drawable upIconView, downIconView;
	private ListView lvCategory, lvDistance;
	private CategoryAdapter adapterCategory, adaperDistance;
	private static ZhouBianSearchBtnShowListener showSearchBtnListener;// 显示搜索按钮回调函数
	private MapView mapView;
	private BaiduMap baiduMap;
	private PoiSearch search;
	private LocationClient mLocClient;
	private List<String> listLocationInfo = new ArrayList<String>();// 搜索到的目标的描述信息
	private List<LatLng> listLocation = new ArrayList<LatLng>();// 搜索到的目标的经纬度 集合
	private Handler mHandler;
	private final static int SEARCH_SUCCESS = 1;
	private final static int SEARCH_FAILED = 2;
	private final static int MAP_POPWINDOW = 3;
	private LatLng userLatLng = null;// 手机所在坐标
	private String selectSearchDestination = "";// 搜索的东西
	private int selectSelectDistance = -1;// 搜索的范围

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.zhou_bian, null);
	}

	public static void setOnShowSearchBtnListener(ZhouBianSearchBtnShowListener cl) {
		showSearchBtnListener = cl;
	}

	private void initMap() {
		mapView = (MapView) getView().findViewById(R.id.bmapsView);
		baiduMap = mapView.getMap();
		baiduMap.setBuildingsEnabled(true);
		locate();
		baiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker arg0) {
				if (arg0 != null) {
					if (arg0.getTitle() != null) {
						Message msg = new Message();
						msg.what = MAP_POPWINDOW;
						msg.obj = arg0;
						mHandler.sendMessage(msg);
					}

				}
				return false;
			}
		});
	}

	/**
	 * 定位自己的坐标
	 */
	private void locate() {
		baiduMap.setMyLocationEnabled(true);
		mLocClient = new LocationClient(act);
		mLocClient.registerLocationListener(new LocationListener());
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
	}

	private void initView() {
		downIconView = getResources().getDrawable(R.drawable.expand_down);
		downIconView.setBounds(0, 0, downIconView.getMinimumWidth(), downIconView.getMinimumHeight());
		upIconView = getResources().getDrawable(R.drawable.expand_up);
		upIconView.setBounds(0, 0, upIconView.getMinimumWidth(), upIconView.getMinimumHeight());
		lvCategory = (ListView) getView().findViewById(R.id.zhoubian_category_lv);
		lvDistance = (ListView) getView().findViewById(R.id.zhoubian_distance_lv);
		List<String> categoryList = Arrays.asList(getResources().getStringArray(R.array.zhoubian_category_array));
		List<String> disList = Arrays.asList(getResources().getStringArray(R.array.zhoubian_distance_array));
		adaperDistance = new CategoryAdapter(act, disList);
		adapterCategory = new CategoryAdapter(act, categoryList);
		lvCategory.setAdapter(adapterCategory);
		lvDistance.setAdapter(adaperDistance);
		tvCategory = (TextView) getView().findViewById(R.id.zhoubian_tv_category);
		tvDistance = (TextView) getView().findViewById(R.id.zhoubian_tv_dis);
		tvCategory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (lvCategory.getVisibility() == View.VISIBLE) {
					lvCategory.setVisibility(View.INVISIBLE);
					tvCategory.setCompoundDrawables(null, null, downIconView, null);
				} else {
					lvCategory.setVisibility(View.VISIBLE);
					tvCategory.setCompoundDrawables(null, null, upIconView, null);

				}

			}
		});
		tvDistance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (lvDistance.getVisibility() == View.VISIBLE) {
					lvDistance.setVisibility(View.INVISIBLE);
					tvDistance.setCompoundDrawables(null, null, downIconView, null);
				} else {
					lvDistance.setVisibility(View.VISIBLE);
					tvDistance.setCompoundDrawables(null, null, upIconView, null);

				}
			}
		});

		lvCategory.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				selectSearchDestination = (String) view.getTag();
				tvCategory.setText(selectSearchDestination);
				tvCategory.performClick();
			}

		});
		lvDistance.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				selectSelectDistance = Integer.valueOf((String) view.getTag());
				tvDistance.setText(String.format(getString(R.string.zhoubian_distance_tx_format, selectSelectDistance)));
				tvDistance.performClick();
			}
		});

		SlidingContentAct.setOnBeginSearchListener(new ZhouBianSearchListener() {

			@Override
			public void beginSearch() {
				beginSearchPlace(selectSearchDestination, selectSelectDistance);
			}
		});

		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case SEARCH_SUCCESS:
					drawSearchedMakerOnMap();
					break;
				case SEARCH_FAILED:
					break;
				case MAP_POPWINDOW:
					try {
						Marker marker = (Marker) msg.obj;
						View popView = act.getLayoutInflater().inflate(R.layout.map_item_pop, null);
						TextView location = (TextView) popView.findViewById(R.id.map_pop_tx);
						// location.setPadding(30, 20, 30, 50);
						if (marker.getTitle() != null) {
							location.setText(marker.getTitle());
						} else {

						}
						Point p = baiduMap.getProjection().toScreenLocation(marker.getPosition());
						p.y += 0;
						p.x += 0;
						LatLng llInfo = baiduMap.getProjection().fromScreenLocation(p);
						BitmapDescriptor bd = BitmapDescriptorFactory.fromView(location);
						InfoWindow infoW = new InfoWindow(bd, llInfo, 0, new InfoWindow.OnInfoWindowClickListener() {

							@Override
							public void onInfoWindowClick() {
								baiduMap.hideInfoWindow();
							}
						});
						baiduMap.showInfoWindow(infoW);
					} catch (Exception e) {
						// TODO: handle exception
						Log.i("chen", "5555" + e);
					} catch (Error ex) {
						Log.i("chen", "66666" + ex);
					}
					break;
				default:
					break;
				}
			}
		};

	}

	/**
	 * 在地图上绘制搜索到的覆盖物
	 */
	private void drawSearchedMakerOnMap() {
		Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.icon_markb);
		BitmapDescriptor d = BitmapDescriptorFactory.fromBitmap(b);
		int index = 0;
		for (LatLng latLng : listLocation) {
			OverlayOptions ooA = new MarkerOptions().position(latLng).icon(d).zIndex(9).draggable(true);
			Marker mMarkerA = (Marker) (baiduMap.addOverlay(ooA));
			mMarkerA.setTitle(listLocationInfo.get(index));
			++index;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	private class LocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation arg0) {
			// TODO Auto-generated method stub
			if (arg0 != null) {
				mLocClient.stop();
				LatLng la = new LatLng(Double.valueOf(arg0.getLatitude()), Double.valueOf(arg0.getLongitude()));
				userLatLng = la;
				setLocation(la);
			}

		}

		@Override
		public void onReceivePoi(BDLocation arg0) {
			if (arg0 != null) {
			}
		}
	};

	private void beginSearchPlace(String searchDestination, int radius) {
		baiduMap.clear();
		if (userLatLng == null || searchDestination == null || searchDestination.equals("") || radius <= 0) {
			return;
		}
		search = PoiSearch.newInstance();
		search.setOnGetPoiSearchResultListener(this);
		search.searchNearby(new PoiNearbySearchOption().location(userLatLng).keyword(searchDestination)
				.pageCapacity(20).radius(radius));
	}

	/**
	 * 在地图上显示坐标
	 * 
	 * @param lt
	 */
	private void setLocation(LatLng lt) {
		MapStatus status = new MapStatus.Builder().target(lt).zoom(13).build();
		MapStatusUpdate update = MapStatusUpdateFactory.newMapStatus(status);
		baiduMap.animateMapStatus(update, 2000);
		// baiduMap.setMapStatus(update);
		Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.icon_marka);
		BitmapDescriptor d = BitmapDescriptorFactory.fromBitmap(b);
		Bundle bs = new Bundle();
		bs.putInt("id", 1);
		OverlayOptions ooA = new MarkerOptions().position(lt).icon(d).zIndex(9).title("chenshuwan").extraInfo(bs)
				.draggable(true);
		Marker mMarkerA = (Marker) (baiduMap.addOverlay(ooA));
		// mMarkerA.setTitle("chenshuwan");
		Log.i("chen", mMarkerA.getTitle());

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		act = getActivity();
		initView();
		initMap();
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onPause() {
		if (showSearchBtnListener != null) {
			showSearchBtnListener.hideSearchBtn();
		}
		super.onPause();
	}

	@Override
	public void onResume() {
		if (showSearchBtnListener != null) {
			showSearchBtnListener.showSearchBtn();
			;
		}
		super.onResume();
	}

	@Override
	public void onGetPoiDetailResult(PoiDetailResult arg0) {
		if (arg0 == null) {
			System.out.println("detail null");
		} else {
			System.out.println("detail not  null");
		}

	}

	@Override
	public void onGetPoiResult(PoiResult arg0) {
		if (arg0 != null) {
			List<PoiInfo> listInfo = arg0.getAllPoi();
			listLocationInfo = new ArrayList<String>();
			listLocation = new ArrayList<LatLng>();
			if (listInfo != null) {
				for (PoiInfo info : listInfo) {
					String s = "";
					if (info.name != null && !info.name.equals("")) {
						s += getString(R.string.map_location_name) + info.name + "\n";
					}
					if (info.address != null && !info.address.equals("")) {
						s += getString(R.string.map_location_adddress) + info.address + "\n";
					}
					if (info.phoneNum != null && !info.phoneNum.equals("")) {
						s += getString(R.string.map_location_phone) + info.phoneNum + "";
					}
					System.out.println(s + "\n" + "next:\n");
					listLocationInfo.add(s);
					listLocation.add(info.location);
				}
				mHandler.sendEmptyMessage(SEARCH_SUCCESS);
			}
		}
	}
}