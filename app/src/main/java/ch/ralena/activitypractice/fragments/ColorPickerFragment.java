package ch.ralena.activitypractice.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import ch.ralena.activitypractice.R;
import ch.ralena.activitypractice.adapters.ColorPickerAdapter;
import ch.ralena.activitypractice.contracts.ColorPickerContract;

/**
 * Created by oversluij on 12/23/2017.
 */

public class ColorPickerFragment extends BaseFragment implements ColorPickerContract, ColorPickerAdapter.OnColorClickedListener {
	public static final String PREF_BACKGROUND = "pref_background";
	List<String> colors;
	ColorPickerPresenter presenter;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_color_picker, container, false);

		presenter = new ColorPickerPresenter(this);

		colors = presenter.getColors();

		RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
		ColorPickerAdapter adapter = new ColorPickerAdapter(colors, this);
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));

		return rootView;
	}

	@Override
	public void onColorClicked(String color) {
		updateBackground(color);
		Toast.makeText(getContext(), "Background updated!", Toast.LENGTH_SHORT);
		for(Fragment fragment : getFragmentManager().getFragments()) {
			// just update fragment's background color
			fragment.onResume();
		}
	}
}
