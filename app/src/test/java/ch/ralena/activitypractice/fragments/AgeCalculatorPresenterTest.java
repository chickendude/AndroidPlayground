package ch.ralena.activitypractice.fragments;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class AgeCalculatorPresenterTest {
	AgeCalculatorPresenter presenter;

	@Mock
	AgeCalculatorFragment view;

	@Before
	public void setUp() throws Exception {
		presenter = new AgeCalculatorPresenter(view);
	}

	@Test
	public void updateMonths() throws Exception {
		// arrange
		int years = 1;
		int months = 1;
		int days = 0;

		// act
		presenter.updateTime(years, months, days);

		// assert
		Mockito.verify(view).showMonths(13);
	}

	@Test
	public void updateWeeks() throws Exception {
		// arrange
		int years = 0;
		int months = 1;
		int days = 14;

		// act
		presenter.updateTime(years, months, days);

		// assert
		Mockito.verify(view).showWeeks(6);
	}

	@Test
	public void updateDays() throws Exception {
		// arrange
		int years = 0;
		int months = 0;
		int days = 14;

		// act
		presenter.updateTime(years, months, days);

		// assert
		Mockito.verify(view).showDays(14);
	}

	@Test
	public void updateHours() throws Exception {
		// arrange
		int years = 0;
		int months = 0;
		int days = 2;

		// act
		presenter.updateTime(years, months, days);

		// assert
		Mockito.verify(view).showHours(48);
	}

	@Test
	public void oneReturnsSingular() throws Exception {
		// arrange
		String day = "day";
		int numDays = 1;

		// act
		String result = presenter.formatForPlural(day, numDays);

		// assert
		assertEquals(result, "1 day");
	}

	@Test
	public void notOneReturnsPlural() throws Exception {
		// arrange
		String day = "day";
		int numDays1 = 2;
		int numDays2 = 0;

		// act
		String result1 = presenter.formatForPlural(day, numDays1);
		String result2 = presenter.formatForPlural(day, numDays2);

		// assert
		assertEquals(result1, "2 days");
		assertEquals(result2, "0 days");
	}

}