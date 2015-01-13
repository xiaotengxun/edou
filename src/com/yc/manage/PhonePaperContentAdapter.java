package com.yc.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edoucell.ych.R;
import com.yc.log.LogUtil;
import com.yc.view.tools.MarQueeTextView;

public class PhonePaperContentAdapter extends BaseAdapter {
	private List<HashMap<String, String>> listHash = new ArrayList<HashMap<String, String>>();
	private Context context;
	private LayoutInflater mInflater;

	public PhonePaperContentAdapter(Context ctx, List<HashMap<String, String>> list) {
		listHash = list;
		context = ctx;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return listHash.size();
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
		ViewHolder vh = null;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = mInflater.inflate(R.layout.manager_phone_item_list_item, null);
			vh.company = (MarQueeTextView) convertView.findViewById(R.id.phone_company);
			vh.owner = (TextView) convertView.findViewById(R.id.company_owner);
			vh.dial = (TextView) convertView.findViewById(R.id.company_dial);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		HashMap<String, String> hash = listHash.get(position);
		vh.company.setText(hash.get("company"));
		vh.owner.setText(hash.get("owner"));
		vh.dial.setTag(hash.get("phone"));
		vh.dial.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String s = (String) v.getTag();
				Intent phoneIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + s));
				phoneIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(phoneIntent);
			}
		});
		return convertView;
	}

	class ViewHolder {
		MarQueeTextView company;
		TextView owner;
		TextView dial;
	};

}
