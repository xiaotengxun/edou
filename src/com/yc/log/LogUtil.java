package com.yc.log;

import android.util.Log;

public class LogUtil {
	private static boolean isOpen = true;

	public static void i(String tag, Object msg) {
		if (isOpen) {
			Log.i(tag, msg.toString());
		}
	}

	public static void e(String tag, String msg) {
		if (isOpen) {
			Log.e(tag, msg);
		}
	}

}
