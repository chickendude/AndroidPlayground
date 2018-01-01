package ch.ralena.activitypractice.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import ch.ralena.activitypractice.R;

public class TimerService extends Service {
	public static final int NOTIFICATION_ID = 1337;
	public static final String NOTIFICATION_CHANNEL_ID = "1338";
	public static final String NOTIFICATION_CHANNEL_NAME = "playground-timer";

	TimerBinder binder;

	long startTime = 0;
	long duration = 0;
	boolean isRunning = false;

	// get a copy of the service so we can run its methods from fragment
	public class TimerBinder extends Binder {
		public TimerService getService() {
			return TimerService.this;
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		binder = new TimerBinder();
		startTime = 0;
		duration = 0;
		isRunning = false;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		// custom service binder to give us access to service methods
		return binder;
	}

	public void sendToForeground() {
		// if SDK > 26, we need to create a channel
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
			NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			NotificationChannel channel = new NotificationChannel(
					NOTIFICATION_CHANNEL_ID,
					NOTIFICATION_CHANNEL_NAME,
					NotificationManager.IMPORTANCE_LOW
			);
			manager.createNotificationChannel(channel);
		}

		NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
		builder.setSmallIcon(R.mipmap.ic_launcher);
		startForeground(NOTIFICATION_ID, builder.build());
	}

	public void sendToBackground() {
		stopForeground(true);
	}

	public void startTimer() {
		if (!isRunning) {
			isRunning = true;
			startTime = System.currentTimeMillis();
		}
	}

	public void stopTimer() {
		if (isRunning) {
			isRunning = false;
			duration += System.currentTimeMillis() - startTime;
		}
	}

	public void resetTimer() {
		startTime = System.currentTimeMillis();
		duration = 0;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public float getTimeInSeconds() {
		long currentTime = System.currentTimeMillis();
		long timePassed = currentTime - startTime + duration;
		return ((float) timePassed) / 1000;
	}
}
