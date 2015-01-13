package com.yc.home.pager;

import com.edoucell.ych.R;
import com.yc.view.tools.HomeViewAnimation;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class HomePagerManageAdapter extends BaseAdapter {
	private int[] picArray;
	private Context context;

	public HomePagerManageAdapter(Context ctx, int[] str) {
		picArray = str;
		context = ctx;
	}

	private ImageView createImageView(int id) {
		
		HomeViewAnimation ani = new HomeViewAnimation();
		ImageView img = new ImageView(context);
		int size=(int) context.getResources().getDimension(R.dimen.homepager_manager_icon_size);
		LayoutParams params = new LayoutParams(size,size);
		img.setLayoutParams(params);
		img.requestLayout();
		img.setScaleType(ScaleType.FIT_XY);
		img.setImageResource(id);
		 img.setAnimation(ani);
		 img.setTag(id);
		return img;
	}

	@Override
	public int getCount() {
		return picArray.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return createImageView(picArray[position]);
	}

}
