package com.yc.baidu.map;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edoucell.ych.R;
import com.yc.log.LogUtil;
import com.yc.view.tools.MarQueeTextView;

public class MapPlaceAdapter extends BaseAdapter {
	private List<String> listPlace = new ArrayList<String>();
	private Context context;
	private LayoutInflater mInflater;

	public MapPlaceAdapter(Context ctx, List<String> list) {
		this.context = ctx;
		listPlace = list;
		mInflater = LayoutInflater.from(context);
	}

	public void setPlace(List<String> list) {
		listPlace = list;
	}

	@Override
	public int getCount() {
		return listPlace.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(R.layout.right_content_locate_place_item, null);
		TextView tv = (TextView) convertView.findViewById(R.id.locate_place_item_name);
		tv.setText(listPlace.get(position));
		return convertView;
	}

}
