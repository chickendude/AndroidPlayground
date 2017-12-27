package ch.ralena.activitypractice.fragments;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Duration;
import org.joda.time.Hours;
import org.joda.time.Months;
import org.joda.time.Weeks;

import ch.ralena.activitypractice.contracts.AgeCalculatorContract;

/**
 * Created by oversluij on 12/24/2017.
 */

public class AgeCalculatorPresenter {
	AgeCalculatorContract view;

	public AgeCalculatorPresenter(AgeCalculatorContract view) {
		this.view = view;
	}

	public void updateTime(int years, int months, int days) {
		DateTime today = new DateTime().withTimeAtStartOfDay();
		DateTime birthDate = today.minusYears(years).minusMonths(months).minusDays(days);

		Duration duration = new Duration(birthDate, today);

		Hours numHours = Hours.hoursBetween(birthDate, today);
		Days numDays = numHours.toStandardDays();
		Weeks numWeeks = Weeks.weeksBetween(birthDate, today);
		Months numMonths = Months.monthsBetween(birthDate, today);
		view.showHours(numHours.getHours());
		view.showDays(numDays.getDays());
		view.showWeeks(numWeeks.getWeeks());
		view.showMonths(numMonths.getMonths());
	}

	public String formatForPlural(String datePart, int value) {
		if (value != 1)
			datePart = datePart + "s";
		return String.format("%d %s", value, datePart);
	}
}
