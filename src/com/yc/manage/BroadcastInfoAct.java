package com.yc.manage;

import java.util.ArrayList;
import java.util.List;

import com.edoucell.ych.R;
import com.yc.view.tools.TopBarView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;

public class BroadcastInfoAct extends Activity {
	private ImageView backBtn;
	private BroadcastAdapter adapter;
	private ExpandableListView listInfo;
	private List<List<String>> childList = new ArrayList<List<String>>();
	private List<String> groupList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manager_broadcast);
		TopBarView topBar = (TopBarView) findViewById(R.id.topbar);
		topBar.setActivity(this);
		initView();
	}

	private void initData() {
		groupList.add("第一条");
		groupList.add("第二条");
		groupList.add("第三条");
		groupList.add("第四条");
		groupList.add("第五条");
		List<String> list = new ArrayList<String>();
		list.add("数据内容数据内容数据内容数据内容数据内容数据内容数据内容数据内容数据内容数据内容数据内容" + "数据内容数据内容数据内容数据内容数据内容数据内容数据内容数据内容数据内容数据内容数据内容"
				+ "数据内容数据内容数据内容数据内容数据内容数据内容数据内容数据内容数据内容数据内容数据内容" + "数据内容数据内容数据内容数据内容数据内容数据内容数据内容数据内容数据内容数据内容数据内容"
				+ "数据内容数据内容数据内容数据内容数据内容数据内容数据内容数据内容数据内容数据内容数据内容");
		childList.add(new ArrayList<String>(list));
		childList.add(new ArrayList<String>(list));
		childList.add(new ArrayList<String>(list));
		childList.add(new ArrayList<String>(list));
		childList.add(new ArrayList<String>(list));
	}

	private void initView() {
		initData();
		backBtn = (ImageView) findViewById(R.id.bar_back);
		adapter = new BroadcastAdapter(childList, groupList, getApplicationContext());
		listInfo = (ExpandableListView) findViewById(R.id.broadcast_expanlist);
		listInfo.setAdapter(adapter);
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
