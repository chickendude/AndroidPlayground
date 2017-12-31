package ch.ralena.activitypractice.fragments;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Interval;
import org.joda.time.Months;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.Weeks;

import java.util.Locale;

import ch.ralena.activitypractice.contracts.AgeCalculatorContract;

class AgeCalculatorPresenter {
	private AgeCalculatorContract view;

	AgeCalculatorPresenter(AgeCalculatorContract view) {
		this.view = view;
	}

	void updateTime(int years, int months, int days) {
		DateTime today = new DateTime().withTimeAtStartOfDay();
		DateTime birthDate = today.minusYears(years).minusMonths(months).minusDays(days);

		Hours numHours = Hours.hoursBetween(birthDate, today);
		Days numDays = numHours.toStandardDays();
		Weeks numWeeks = numHours.toStandardWeeks();
		Months numMonths = Months.monthsBetween(birthDate, today);
		view.showHours(numHours.getHours());
		view.showDays(numDays.getDays());
		view.showWeeks(numWeeks.getWeeks());
		view.showMonths(numMonths.getMonths());
	}

	public String formatForPlural(String datePart, int value) {
		if (value != 1)
			datePart = datePart + "s";
		return String.format(Locale.getDefault(), "%d %s", value, datePart);
	}

	void updateBirthDate(int year, int month, int day) {
		DateTime today = new DateTime().withTimeAtStartOfDay();
		DateTime birthDate = new DateTime(year, month + 1, day, 0, 0);
		Interval interval = new Interval(birthDate, today);
		Period difference = interval.toPeriod().normalizedStandard(PeriodType.yearMonthDay());
		view.updateAge(difference.getYears(), difference.getMonths(), difference.getDays());
	}
}
