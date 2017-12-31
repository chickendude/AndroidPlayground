package ch.ralena.activitypractice.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import ch.ralena.activitypractice.R;
import ch.ralena.activitypractice.contracts.AgeCalculatorContract;
import ch.ralena.activitypractice.views.AgeEditText;

public class AgeCalculatorFragment extends BaseFragment implements AgeCalculatorContract {
	private TextView birthDateText;
	private AgeEditText ageEdit;

	private TextView monthsValue;
	private TextView weeksValue;
	private TextView daysValue;
	private TextView hoursValue;

	private AgeCalculatorPresenter presenter;
	private int years = 0;
	private int months = 0;
	private int days = 0;

	DatePickerDialog.OnDateSetListener onDateSetListener = (datePicker, year, month, day) -> {
		// update birthdatetext to new date
		DateFormat df = new SimpleDateFormat("MMM d, yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, 0, 0);
		birthDateText.setText(df.format(calendar.getTime()));
		// update ageEdit values
		presenter.updateBirthDate(year, month, day);
	};

	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		presenter = new AgeCalculatorPresenter(this);

		// inflate layout
		rootView = inflater.inflate(R.layout.fragment_age_calculator, container, false);

		monthsValue = rootView.findViewById(R.id.monthsValue);
		weeksValue = rootView.findViewById(R.id.weeksValue);
		daysValue = rootView.findViewById(R.id.daysValue);
		hoursValue = rootView.findViewById(R.id.hoursValue);

		// set up age edit listener
		ageEdit = rootView.findViewById(R.id.ageEdit);
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

		// set up birthday textview to bring up date picker and update years/months/days
		birthDateText = rootView.findViewById(R.id.birthDateText);
		birthDateText.setOnClickListener(v -> {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.YEAR, -years);
			calendar.add(Calendar.MONTH, -months);
			calendar.add(Calendar.DAY_OF_MONTH, -days);
			DatePickerDialog datePicker = new DatePickerDialog(
					getContext(),
					onDateSetListener,
					calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH)
			);
			datePicker.show();
		});


		// default values
		presenter.updateTime(0, 0, 0);

		return rootView;
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

	@Override
	public void updateAge(int years, int months, int days) {
		ageEdit.setDate(years, months, days);
	}

	void updateTextView(TextView textView, String datePart, int value) {
		String string = presenter.formatForPlural(datePart, value);
		textView.setText(string);
	}
}
