package com.yc.container;

import com.edoucell.ych.R;
import com.yc.service.remoteBinder;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class MainAct extends SlidingContentAct {
	private remoteBinder rBinder;
	private ServiceConnection conn;
	private Runnable runn = new Runnable() {

		@Override
		public void run() {
			stopService();
		}
	};
	private Handler handler;

	@Override
	public void onCreate(Bundle arg0) {
		super.onCreate(arg0);
	}

	private void stopService() {
		Log.i("chen", "stopService");
		unbindService(conn);
	}

	private void test() {
		conn = new ServiceConnection() {
			@Override
			public void onServiceDisconnected(ComponentName name) {
				conn = null;
			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				rBinder = remoteBinder.Stub.asInterface(service);
				if (rBinder != null) {
					try {
						rBinder.startBinder();
					} catch (Exception e) {
						Log.i("chen", "onServiceConnected  exeception=" + e);
					}
				}
			}
		};
		Intent intent = new Intent();
		String act = getString(R.string.service_action);
		intent.setAction(act);
		bindService(intent, conn, Service.BIND_AUTO_CREATE);
		handler = new Handler();
		handler.postDelayed(runn, 10000);
	}

}
