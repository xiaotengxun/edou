package com.yc.service;

import com.edoucell.ych.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ServiceBroadReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())
				|| Intent.ACTION_USER_PRESENT.equals(intent.getAction())) {
			Intent startServiceIntent = new Intent(context.getString(R.string.service_action));
			startServiceIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startService(startServiceIntent);
		}
	}

}
