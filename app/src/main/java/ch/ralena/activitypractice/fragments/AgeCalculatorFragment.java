package ch.ralena.activitypractice.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ch.ralena.activitypractice.R;
import ch.ralena.activitypractice.contracts.AgeCalculatorContract;
import ch.ralena.activitypractice.views.AgeEditText;

public class AgeCalculatorFragment extends Fragment implements AgeCalculatorContract {
	View root;
	TextView monthsValue;
	TextView weeksValue;
	TextView daysValue;
	TextView hoursValue;

	AgeCalculatorPresenter presenter;
	AgeEditText ageEdit;
	int years;
	int months;
	int days;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		presenter = new AgeCalculatorPresenter(this);

		// inflate layout
		root = inflater.inflate(R.layout.fragment_age_calculator, container, false);

		monthsValue = root.findViewById(R.id.monthsValue);
		weeksValue = root.findViewById(R.id.weeksValue);
		daysValue = root.findViewById(R.id.daysValue);
		hoursValue = root.findViewById(R.id.hoursValue);

		ageEdit = root.findViewById(R.id.ageEdit);
		ageEdit.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void afterTextChanged(Editable editable) {
				years = ageEdit.getYears();
				months = ageEdit.getMonths();
				days = ageEdit.getDays();
				presenter.updateTime(years, months, days);
			}
		});

		// default values
		presenter.updateTime(0, 0, 0);

		return root;
	}

	@Override
	public void showMonths(int months) {
		updateTextView(monthsValue, "month", months);
	}

	@Override
	public void showWeeks(int weeks) {
		updateTextView(weeksValue, "week", weeks);
	}

	@Override
	public void showDays(int days) {
		updateTextView(daysValue, "day", days);
	}

	@Override
	public void showHours(int hours) {
		updateTextView(hoursValue, "hour", hours);
	}

	void updateTextView(TextView textView, String datePart, int value) {
		String string = presenter.formatForPlural(datePart, value);
		textView.setText(string);
	}
}
