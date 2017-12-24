package ch.ralena.activitypractice.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import ch.ralena.activitypractice.R;
import ch.ralena.activitypractice.contracts.AgeCalculatorContract;

public class AgeCalculatorFragment extends Fragment implements AgeCalculatorContract {
	View root;
	AgeCalculatorPresenter presenter;
	EditText ageEdit;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		presenter = new AgeCalculatorPresenter(this);

		// inflate layout
		root = inflater.inflate(R.layout.fragment_age_calculator, container, false);

		ageEdit = root.findViewById(R.id.ageEdit);
		ageEdit.setOnFocusChangeListener((view, hasFocus) -> {
			if (hasFocus) {
				ageEdit.setSelection(0);
			}
		});

		return root;
	}
}
