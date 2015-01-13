package com.yc.shenqian;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.edoucell.ych.R;
import com.yc.baidu.map.MapAct;
import com.yc.container.AbSlidingAct;

public class CheapShopFrag extends Fragment {
	private String chen = "HomePagerFrag";
//	private ImageView menuView,searchView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.cheap_shop, null);
	}

	private void initView() {
//		menuView = (ImageView) getView().findViewById(R.id.menu_slide);
//		searchView = (ImageView) getView().findViewById(R.id.location_search);
//		menuView.setClickable(true);
//		menuView.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				AbSlidingAct.slidingMenu.showMenu();
//			}
//		});
//		searchView.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(getActivity(), MapAct.class);
//				startActivity(intent);
//			}
//		});
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		initView();
		super.onActivityCreated(savedInstanceState);
	}
}