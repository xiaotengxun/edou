package com.yc.manage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.edoucell.ych.R;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.yc.manage.ManagerItemContentAct.VoiceOnListener;
import com.yc.utils.JsonParser;

public class ManagerAddFixFrag extends Fragment {
	private EditText edTitle;
	private Handler mHandler;
	private boolean isListening = false;
	private static final int SPEEK_OVER = 1;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initSpeechTwo();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.manager_funciton_addfix, null);
	}

	private void initView() {

		edTitle = (EditText) getView().findViewById(R.id.manager_addfix_title);

		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case SPEEK_OVER:
					if (isListening) {
						dialog.show();
					}
					break;
				}
			}
		};
		ManagerItemContentAct.setVoiceOnListener(new VoiceOnListener() {

			@Override
			public void beginVoice() {
				// initSpeechTwo();
				dialog.show();
			}
		});
	}

	SpeechRecognizer mIat;

	private void initSpeech() {
		mIat = SpeechRecognizer.createRecognizer(getActivity(), new InitListener() {

			@Override
			public void onInit(int arg0) {
				Log.i("chen", "init code=" + arg0);

			}
		});
		mIat.setParameter(SpeechConstant.SAMPLE_RATE, "16000");
		mIat.setParameter(SpeechConstant.DOMAIN, "iat");
		mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
		mIat.setParameter(SpeechConstant.ACCENT, "mandarin");
		mIat.startListening(new RecognizerListener() {

			@Override
			public void onVolumeChanged(int arg0) {
			}

			@Override
			public void onResult(RecognizerResult arg0, boolean arg1) {
				JsonParser parser = new JsonParser();
				String result = parser.parseIatResult(arg0.getResultString());
				Log.i("chen", "result=" + result);

			}

			@Override
			public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {

			}

			@Override
			public void onError(SpeechError arg0) {
			}

			@Override
			public void onEndOfSpeech() {
			}

			@Override
			public void onBeginOfSpeech() {
			}
		});

		Handler mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				mIat.stopListening();
				Log.i("chen", "stop listener");
				super.handleMessage(msg);
			}
		};
		// mHandler.sendMessageDelayed(new Message(), 5000);

	}

	RecognizerDialog dialog;

	private void initSpeechTwo() {
		isListening = true;
		dialog = new RecognizerDialog(getActivity(), new InitListener() {

			@Override
			public void onInit(int arg0) {
				Log.i("chen", "init code=" + arg0);

			}
		});
		dialog.setParameter(SpeechConstant.SAMPLE_RATE, "16000");
		dialog.setParameter(SpeechConstant.DOMAIN, "iat");
		dialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
		dialog.setParameter(SpeechConstant.ACCENT, "mandarin");
		// dialog.setParameter(SpeechConstant.KEY_SPEECH_TIMEOUT, "6000000");
		dialog.setListener(new RecognizerDialogListener() {

			@Override
			public void onResult(RecognizerResult arg0, boolean arg1) {
				JsonParser parser = new JsonParser();
				String result = parser.parseIatResult(arg0.getResultString());
				result = edTitle.getText().toString() + result;
				edTitle.setText(result);
//				dialog.cancel();
				mHandler.sendEmptyMessage(SPEEK_OVER);
			}

			@Override
			public void onError(SpeechError arg0) {
				Log.i("chen", "error code=" + arg0);

			}
		});
		// dialog.show();

	}

}
