package ch.ralena.activitypractice.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;

public class TimerService extends Service {
	public Messenger messenger = new Messenger(new TimerHandler());

	private class TimerHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			Log.d("tag", "handling message");
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// set up thread
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return messenger.getBinder();
	}
}
