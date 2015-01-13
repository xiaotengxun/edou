package com.yc.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.edoucell.ych.R;

public class PhonePagerTypeAdapter extends BaseAdapter {
	List<List<HashMap<String, String>>> list = new ArrayList<List<HashMap<String, String>>>();
	List<String> listType = new ArrayList<String>();
	private Context context;
	private LayoutInflater mInflater;

	public PhonePagerTypeAdapter(Context ctx, List<String> listType, List<List<HashMap<String, String>>> list) {
		this.context = ctx;
		this.list = list;
		this.listType = listType;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.manager_phone_item, null);
			vh = new ViewHolder();
			convertView.setTag(vh);
			vh.listView = (ListView) convertView.findViewById(R.id.phone_list);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		initListView(vh.listView, position);
		TextView typeTV = (TextView) convertView.findViewById(R.id.phone_type);
		typeTV.setText(listType.get(position));
		return convertView;
	}

	private void initListView(ListView listView, int position) {
		PhonePaperContentAdapter adapter = new PhonePaperContentAdapter(context, list.get(position));
		listView.setAdapter(adapter);
		setListViewHeightBasedOnChildren(listView);
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		PhonePaperContentAdapter listAdapter = (PhonePaperContentAdapter) listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	class ViewHolder {
		ListView listView;
	};
}
