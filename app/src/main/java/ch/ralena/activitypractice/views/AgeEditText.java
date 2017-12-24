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

	boolean cursorPositionBlocked;

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
		cursorPositionBlocked = false;
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
		if (cursorPositionBlocked)
			return;
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
			String[] parts = s.toString().split(" [a-z]+[ ]?");
			if (parts.length == 3) {
				curPosition = 0;
				years = parts[0];
				months = parts[1];
				days = parts[2];

				if (start < 4) {
					curPosition = 3;
					years = years.replace("_", "");
					if (years.length() >= 3)
						curPosition = 12;
					int prefixSize = 3 - years.length();
					for (int i = 0; i < prefixSize; i++) {
						years = "_" + years;
					}
					if (years.length() > 3) {
						years = years.substring(years.length() - 3);
					}
				} else if (start < 13) {
					curPosition = 12;
					months = months.replace("_", "");
					if (months.length() >= 2)
						curPosition = 22;
					int prefixSize = 2 - months.length();
					for (int i = 0; i < prefixSize; i++) {
						months = "_" + months;
					}
					if (months.length() > 2) {
						months = months.substring(months.length() - 2);
					}
				} else if (start > 19) {
					curPosition = 22;
					days = days.replace("_", "");
					int prefixSize = 2 - days.length();
					for (int i = 0; i < prefixSize; i++) {
						days = "_" + days;
					}
					if (days.length() > 2) {
						days = days.substring(days.length() - 2);
					}
				}
//				setSelection(curPosition);
			}
		}


		@Override
		public void afterTextChanged(Editable editable) {
			removeTextChangedListener(textWatcher);
			setText(String.format(TEMPLATE, years, months, days));
			cursorPositionBlocked = false;
			setSelection(curPosition);
			cursorPositionBlocked = true;
			addTextChangedListener(textWatcher);
		}
	};
}
