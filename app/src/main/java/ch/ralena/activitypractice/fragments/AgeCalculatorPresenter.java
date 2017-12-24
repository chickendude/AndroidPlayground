package ch.ralena.activitypractice.fragments;

import ch.ralena.activitypractice.contracts.AgeCalculatorContract;

/**
 * Created by oversluij on 12/24/2017.
 */

public class AgeCalculatorPresenter {
	AgeCalculatorContract view;

	public AgeCalculatorPresenter(AgeCalculatorContract view) {
		this.view = view;

	}
}
