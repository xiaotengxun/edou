package com.yc.view.tools;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edoucell.ych.R;

public class TopBarView extends RelativeLayout {
	private ImageView back;
	private Activity act = null;
	private TextView title;
	private ImageView rightBtn;

	public TopBarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		intView(context);
		initStyle(context, attrs);
	}

	public TopBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		intView(context);
		initStyle(context, attrs);
	}

	private void initStyle(Context context, AttributeSet attrs) {
		TypedArray type = context.obtainStyledAttributes(attrs, R.styleable.top_bar_sty);
		int size = (int) type.getDimension(R.styleable.top_bar_sty_textSize, 18);
		int color = type.getColor(R.styleable.top_bar_sty_textColor, Color.WHITE);
		String content = type.getString(R.styleable.top_bar_sty_text);
		Log.i("chen", size + "    " + color);
		title.setTextSize(size);
		title.setTextColor(color);
		title.setText(content);
		type.recycle();
	}

	public TopBarView(Context context) {
		super(context);
		intView(context);
	}

	public void setTitle(String titles) {
		int a = 0;
		title.setText(titles);
	}

	public void setOnRightBtnClickListener(View.OnClickListener cl) {
		if (rightBtn != null) {
			rightBtn.setOnClickListener(cl);
		}
	}
	public void setRightBtnIcon(int id){
		if(rightBtn != null){
			rightBtn.setImageResource(id);
		}
	}

	public void setRightBtnVisible(int visible){
		if(rightBtn != null){
			rightBtn.setVisibility(visible);
		}
	}
	private void intView(Context context) {
		View topBar = LayoutInflater.from(context).inflate(R.layout.sub_title_bar, null);
		LayoutParams param = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		addView(topBar, param);
		back = (ImageView) topBar.findViewById(R.id.bar_back);
		rightBtn = (ImageView) topBar.findViewById(R.id.bar_right_btn);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (act == null) {
					throw new NullPointerException(
							"actionBar return button is not initialized,you must invoke method setActivity");
				} else {
					act.finish();
				}
			}
		});
		title = (TextView) topBar.findViewById(R.id.top_title);

	}

	public void setActivity(Activity act) {
		this.act = act;
	}

}
