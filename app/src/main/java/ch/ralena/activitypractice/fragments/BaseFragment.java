package ch.ralena.activitypractice.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.View;

public class BaseFragment extends Fragment {
	public static final String SHARED_PREFERENCES = "prefs";
	public static final String PREF_BACKGROUND = "pref_background";
	View rootView;
	SharedPreferences preferences;

	@Override
	public void onStart() {
		super.onStart();
		preferences = getActivity().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
	}

	@Override
	public void onResume() {
		super.onResume();
		String bgColor = preferences.getString(PREF_BACKGROUND, null);
		updateBackground(bgColor);

	}

	void updateBackground(String color) {
		if (color != null) {
			rootView.setBackgroundColor(Color.parseColor(color));
			preferences.edit().putString(PREF_BACKGROUND, color).apply();
		}
	}
}
