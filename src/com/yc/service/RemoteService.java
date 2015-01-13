package com.yc.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.edoucell.ych.R;
import com.yc.container.MainAct;
import com.yc.service.remoteBinder.Stub;

public class RemoteService extends Service {
	private Stub mBinder = new Stub() {

		@Override
		public void startBinder() throws RemoteException {
//			Log.i("chen", "startBinder");

		}
	};
	AlarmManager mAlarmManager = null;
	PendingIntent mPendingIntent = null;

	@Override
	public IBinder onBind(Intent intent) {
//		Log.i("chen", "onBind");
		return mBinder;
	}

	@Override
	public void onCreate() {
		initAlarm();
//		Log.i("chen", "onCreate");
		super.onCreate();
	}

	private void initAlarm() {
		Intent intent = new Intent(getString(R.string.service_action));
		mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		mPendingIntent = PendingIntent.getService(this, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
		long now = System.currentTimeMillis();
		mAlarmManager.setInexactRepeating(AlarmManager.RTC, now, 60000, mPendingIntent);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		flags = START_STICKY;
//		Log.i("chen", "onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}

	@SuppressWarnings("deprecation")
	private void setForeground() {
		Notification notification = new Notification(R.drawable.ic_launcher, getString(R.string.app_name),
				System.currentTimeMillis());
		PendingIntent pendingintent = PendingIntent.getActivity(this, 0, new Intent(this, MainAct.class), 0);
		notification.setLatestEventInfo(this, "uploadservice", "请保持程序在后台运行", pendingintent);
		startForeground(0x111, notification);
	}

	@Override
	public void onDestroy() {
	
		super.onDestroy();
	}

	@Override
	public boolean onUnbind(Intent intent) {
//		Log.i("chen", "service onUnbind");
		mBinder = null;
		return super.onUnbind(intent);
	}

}
