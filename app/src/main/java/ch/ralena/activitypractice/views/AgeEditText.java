package ch.ralena.activitypractice.views;

import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

public class AgeEditText extends android.support.v7.widget.AppCompatEditText {
	private static final String TEMPLATE = "%s years %s months %s days";
	String years = "___";
	String months = "__";
	String days = "__";

	public int getYears() {
		return stringToInt("___", years);
	}

	public int getMonths() {
		return stringToInt("__", months);
	}

	public int getDays() {
		return stringToInt("__", days);
	}

	public void setDate(int newYears, int newMonths, int newDays) {
		String y = leftPadString("" + newYears, 3, 999);
		String m = leftPadString("" + newMonths, 2, 12);
		String d = leftPadString("" + newDays, 2, 30);
		setText(String.format(TEMPLATE, y, m, d));
	}

	private int stringToInt(String empty, String string) {
		// make sure string isn't empty, if it is return 0 to avoid parsing error
		return string.equals(empty) ? 0 : Integer.parseInt(string.replace("_", ""));
	}

	private static final String TAG = AgeEditText.class.getSimpleName();

	public AgeEditText(Context context) {
		super(context, null);
		setUpEditText();
	}

	public AgeEditText(Context context, AttributeSet attrs) {
		super(context, attrs, android.support.v7.appcompat.R.attr.editTextStyle);
		setUpEditText();
	}

	public AgeEditText(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setUpEditText();
	}

	private void setUpEditText() {
		setInputType(InputType.TYPE_CLASS_NUMBER);
		setText(String.format(TEMPLATE, years, months, days));
		setLongClickable(false);
		setCustomSelectionActionModeCallback(new ActionMode.Callback() {
			@Override
			public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
				return false;
			}

			@Override
			public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
				return false;
			}

			@Override
			public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
				return false;
			}

			@Override
			public void onDestroyActionMode(ActionMode actionMode) {

			}
		});
		addTextChangedListener(textWatcher);
	}


	@Override
	protected void onSelectionChanged(int selStart, int selEnd) {
		if (getText().length() > 0) {
			if (selStart < 9 && selStart != 3) {
				setSelection(3);
				return;
			} else if (selStart >= 9 && selStart < 19 && selStart != 12) {
				setSelection(12);
				return;
			} else if (selStart >= 19 && selStart != 22) {
				setSelection(22);
				return;
			}
		}
		super.onSelectionChanged(selStart, selEnd);
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
		if (focused) {
			setSelection(0);
		}
	}

	// === text watcher ===
	TextWatcher textWatcher = new TextWatcher() {
		int curPosition;

		@Override
		public void beforeTextChanged(CharSequence charSequence, int start, int end, int count) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int end, int count) {
			curPosition = start;
			// split up string and make sure values are correct
			String[] parts = s.toString().split(" [a-z]+[ ]?");
			if (parts.length == 3) {
				curPosition = 0;
				years = parts[0];
				months = parts[1];
				days = parts[2];

				if (start < 4) {
					years = years.replace("_", "");
					curPosition = years.length() < 3 ? 3 : 12;
					years = leftPadString(years, 3, 999);
				} else if (start < 13) {
					months = months.replace("_", "");
					curPosition = months.length() < 2 ? 12 : 22;
					months = leftPadString(months, 2, 11);
				} else if (start > 19) {
					days = days.replace("_", "");
					curPosition = 22;
					days = leftPadString(days, 2, 30);
				}
			}
		}

		@Override
		public void afterTextChanged(Editable editable) {
			removeTextChangedListener(textWatcher);
			setText(String.format(TEMPLATE, years, months, days));
			setSelection(curPosition);
			addTextChangedListener(textWatcher);
		}
	};

	String leftPadString(String number, int size, int maxValue) {

		// trim off left part of string if it's too long
		if (number.length() > size)
			number = number.substring(number.length() - size);

		if (!number.isEmpty()) {
			int value = Integer.parseInt(number);
			number = "" + (value > maxValue ? maxValue : value);
		}

		// remove trailing 0's
		// left pad string with underscores
		return String.format("%" + size + "s", number).replace(" ", "_");
	}

}
