package ch.ralena.activitypractice.fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ch.ralena.activitypractice.R;
import ch.ralena.activitypractice.services.TimerService;

public class TimerFragment extends BaseFragment implements ServiceConnection {
	private TextView timeValue;

	private Messenger messenger;
	private boolean isBoundToService = false;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_timer, container, false);
		timeValue = rootView.findViewById(R.id.timeValue);

		Button startStopButton = rootView.findViewById(R.id.startStopButton);
		Button resetButton = rootView.findViewById(R.id.resetButton);

		startStopButton.setOnClickListener(view -> {
			if (isBoundToService) {

			}
		});

		return rootView;
	}

	@Override
	public void onStart() {
		super.onStart();
		// start timer service
		Intent intent = new Intent(getContext(), TimerService.class);
		getActivity().bindService(intent, this, Context.BIND_AUTO_CREATE);
	}

	@Override
	public void onStop() {
		super.onStop();
		// stop service
		if (isBoundToService) {
			getActivity().unbindService(this);
			isBoundToService = false;
		}
	}

	@Override
	public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
		// get our messenger and show that we're bound to the service
		isBoundToService = true;
		messenger = new Messenger(iBinder);
	}

	@Override
	public void onServiceDisconnected(ComponentName componentName) {
		// update service bound boolean
		isBoundToService = false;
	}
}
