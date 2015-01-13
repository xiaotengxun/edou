package com.yc.test;

import com.edoucell.ych.R;
import com.yc.view.tools.TopBarView;

import android.app.Activity;
import android.os.Bundle;

public class Test extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_view);
		TopBarView topBar = (TopBarView) findViewById(R.id.topbar);
		topBar.setActivity(this);
	}

}
