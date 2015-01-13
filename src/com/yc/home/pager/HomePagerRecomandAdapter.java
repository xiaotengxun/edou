package com.yc.home.pager;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.edoucell.ych.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ViewScaleType;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;

public class HomePagerRecomandAdapter extends BaseAdapter {
	private int[] picArray;
	private Context context;
	private LayoutInflater mInflater;

	public HomePagerRecomandAdapter(Context ctx, int[] list) {
		context = ctx;
		picArray = list;
		mInflater = LayoutInflater.from(context);
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
		convertView = mInflater.inflate(R.layout.home_recomand_item, null);
		ImageView iv = (ImageView) convertView.findViewById(R.id.store_item);
		// iv.setImageResource(picArray[position]);
		String picUrl = "http://p3.qhimg.com/dmt/528_351_/t01257aac6686b99339.jpg";
		ImageLoader.getInstance().displayImage(picUrl, iv);
		return convertView;
	}

}
