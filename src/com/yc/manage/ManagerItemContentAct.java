package com.yc.manage;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.edoucell.ych.R;
import com.yc.view.tools.TopBarView;

public class ManagerItemContentAct extends FragmentActivity {
	private ImageView back;
	private ManagerAddFixFrag addFixFrag;
	private ManagerCellInfoFrag cellInfoFrag;
	private TopBarView topBar;
	private static VoiceOnListener voiceOnListener = null;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.manager_funciton_item_content);
		topBar = (TopBarView) findViewById(R.id.topbar);
		topBar.setActivity(this);
		initView();
		choseContent();
	}

	private void initView() {
		back = (ImageView) findViewById(R.id.bar_back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		addFixFrag = new ManagerAddFixFrag();
		cellInfoFrag = new ManagerCellInfoFrag();
	}

	private void choseContent() {
		int index = getIntent().getIntExtra("item", 0);
		topBar.setRightBtnVisible(View.INVISIBLE);
		switch (index) {
		case 0:
			getSupportFragmentManager().beginTransaction().replace(R.id.manager_content_container, cellInfoFrag)
					.commit();
			topBar.setTitle(getString(R.string.manager_listitem_cell_introduce));
			break;
		case 1:
			getSupportFragmentManager().beginTransaction().replace(R.id.manager_content_container, addFixFrag).commit();
			topBar.setTitle(getString(R.string.manager_listitem_addfix));
			topBar.setRightBtnVisible(View.VISIBLE);
			topBar.setRightBtnIcon(R.drawable.voice_speech_icon);
			topBar.setOnRightBtnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (voiceOnListener != null) {
						voiceOnListener.beginVoice();
					}

				}
			});

			break;
		}
	}

	public static void setVoiceOnListener(VoiceOnListener vl) {
		voiceOnListener = vl;
	}

	public interface VoiceOnListener {
		public void beginVoice();
	};
}
