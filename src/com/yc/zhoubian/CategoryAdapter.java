package com.yc.zhoubian;

import java.util.ArrayList;
import java.util.List;

import com.edoucell.ych.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryAdapter extends BaseAdapter {
	private List<String> list = new ArrayList<String>();
	private LayoutInflater mInflater;

	public CategoryAdapter(Context context, List<String> list) {
		this.list = list;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
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
		convertView = mInflater.inflate(R.layout.zhoubian_listview_item, null);
		TextView tv = (TextView) convertView.findViewById(R.id.lv_tx);
		tv.setText(list.get(position));
		convertView.setTag(list.get(position));
		return convertView;
	}

}
