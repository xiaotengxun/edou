package com.yc.manage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.edoucell.ych.R;
import com.yc.view.tools.TopBarView;

public class ManagerAct extends Activity {
	private ImageView back;
	private View managerAddFixSheet, managerCellIntroduce;
	private Activity act;
	private TopBarView topBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manager_function);
		topBar = (TopBarView) findViewById(R.id.topbar);
		topBar.setActivity(this);
		act = ManagerAct.this;
		initView();
		setViewOnClick();
	}

	private void initView() {
		back = (ImageView) findViewById(R.id.bar_back);
		managerAddFixSheet = findViewById(R.id.manager_item_addfix);
		managerCellIntroduce = findViewById(R.id.manager_item_cell_introduce);
	}

	private void setViewOnClick() {
		back.setOnClickListener(ManagerListItemOnClick);
		managerAddFixSheet.setOnClickListener(ManagerListItemOnClick);
		managerCellIntroduce.setOnClickListener(ManagerListItemOnClick);
	}

	private Intent intent = new Intent();
	private OnClickListener ManagerListItemOnClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			intent = new Intent(act, ManagerItemContentAct.class);
			switch (v.getId()) {
			case R.id.bar_back:
				finish();

				break;
			case R.id.manager_item_cell_introduce:
				intent.putExtra("item", 0);
				startActivity(intent);
				break;
			case R.id.manager_item_addfix:
				intent.putExtra("item", 1);
				startActivity(intent);
				break;
			default:
				break;
			}

		}
	};

}
