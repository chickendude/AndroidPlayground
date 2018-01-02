package ch.ralena.activitypractice.fragments;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import ch.ralena.activitypractice.R;
import ch.ralena.activitypractice.services.TimerService;

public class TimerFragment extends BaseFragment implements ServiceConnection {
	public static final int MSG_GET_TIME = 0;

	private FragmentActivity activity;
	private TextView timeValue;
	private Button startStopButton;

	private TimerService timerService;
	private Messenger messenger;
	private TimerHandler handler;
	private boolean isBoundToService = false;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		activity = getActivity();

		// handler to regularly query service
		handler = new TimerHandler(this);

		rootView = inflater.inflate(R.layout.fragment_timer, container, false);
		timeValue = rootView.findViewById(R.id.timeValue);

		startStopButton = rootView.findViewById(R.id.startStopButton);
		Button resetButton = rootView.findViewById(R.id.resetButton);

		startStopButton.setOnClickListener(view -> {
			if (isBoundToService) {
				if (timerService.isRunning()) {
					timerService.stopTimer();
					startStopButton.setText("Start");
					handler.removeMessages(MSG_GET_TIME);
				} else {
					startTimer();
				}
			}
		});

		resetButton.setOnClickListener(view -> {
			if (isBoundToService) {
				timerService.resetTimer();
				showTime();
			}
		});

		return rootView;
	}

	private void startTimer() {
		timerService.startTimer();
		startStopButton.setText("Stop");
		handler.sendEmptyMessage(MSG_GET_TIME);
	}

	@Override
	public void onStart() {
		super.onStart();
		// start timer service
		Intent intent = new Intent(getContext(), TimerService.class);
		activity.startService(intent);
		activity.bindService(intent, this, 0);
	}

	@Override
	public void onStop() {
		super.onStop();
		// stop service
		if (isBoundToService) {
			if (timerService.isRunning()) {
				timerService.sendToForeground();
			} else {
				timerService.stopSelf();
				timerService.stopForeground(true);
			}
			activity.unbindService(this);
			isBoundToService = false;
		}
	}

	public void showTime() {
		if (isBoundToService) {
			float time = timerService.getTimeInSeconds();
			timeValue.setText(String.format(Locale.getDefault(), "%.2f seconds", time));
		}
	}

	@Override
	public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
		// get our messenger and show that we're bound to the service
		isBoundToService = true;
		timerService = ((TimerService.TimerBinder) iBinder).getService();
		timerService.sendToBackground();
		if (timerService.isRunning()) {
			startTimer();
		} else {
			startStopButton.setText("Start");
		}
	}

	@Override
	public void onServiceDisconnected(ComponentName componentName) {
		// update service bound boolean
		isBoundToService = false;
	}

	public class TimerHandler extends Handler {
		TimerFragment fragment;

		public TimerHandler(TimerFragment fragment) {
			this.fragment = fragment;
		}

		@Override
		public void handleMessage(Message msg) {
			fragment.showTime();
			sendEmptyMessageDelayed(MSG_GET_TIME, 25);
		}
	}

}
