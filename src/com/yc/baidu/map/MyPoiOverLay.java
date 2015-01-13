package com.yc.baidu.map;

import android.util.Log;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.poi.PoiResult;

public class MyPoiOverLay extends PoiOverlay {

	public MyPoiOverLay(BaiduMap arg0) {
		super(arg0);
		arg0.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker arg0) {
				// TODO Auto-generated method stub
				if(arg0 != null){
					Log.i("chen", arg0.getTitle()+"    ");
				}
				return false;
			}
		});
	}

	@Override
	public boolean onPoiClick(int arg0) {
		// TODO Auto-generated method stub
		Log.i("chen", "onClick item =" + arg0);
		return super.onPoiClick(arg0);
	}

	@Override
	public void setData(PoiResult arg0) {
		super.setData(arg0);
	}

}
