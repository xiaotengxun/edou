package com.yc.manage;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class BroadcastAdapter extends BaseExpandableListAdapter {
	private List<List<String>> childList = new ArrayList<List<String>>();
	private List<String> groupList = new ArrayList<String>();
	private Context context;

	
	public BroadcastAdapter(List<List<String>> childList, List<String> groupList, Context context) {
		super();
		this.childList = childList;
		this.groupList = groupList;
		this.context = context;
	}

	@Override
	public int getGroupCount() {
		return groupList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return (childList.get(groupPosition).size());
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groupPosition;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	private TextView createView() {
		TextView tx = new TextView(context);
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		tx.setPadding(90, 30, 10, 30);
		tx.setTextSize(20);
		tx.setTextColor(Color.BLACK);
		tx.setLayoutParams(params);
		tx.requestLayout();
		return tx;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView tx = createView();
		tx.setText(groupList.get(groupPosition));
		return tx;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		TextView tx = createView();
		tx.setText(childList.get(groupPosition).get(childPosition));
		tx.setTextSize(18);
		return tx;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

}
