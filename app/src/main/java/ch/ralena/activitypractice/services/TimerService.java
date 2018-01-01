package ch.ralena.activitypractice.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class TimerService extends Service {
	public final static int START_TIMER = 0;
	public final static int STOP_TIMER = 1;
	public final static int RESET_TIMER = 2;

	TimerBinder binder;

	long startTime = 0;
	long endTime = 0;
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
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		isRunning = false;
		return START_STICKY;
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
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
		startTime = 0;
		endTime = 0;
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
