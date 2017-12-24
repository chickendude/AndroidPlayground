package ch.ralena.activitypractice.fragments;

import ch.ralena.activitypractice.contracts.LoginContract;

/**
 * Created by oversluij on 12/23/2017.
 */

public class LoginPresenter {
	private static final String USERNAME = "coolguy";
	private static final String PASSWORD = "password";
	private LoginContract view;



	public LoginPresenter(LoginFragment view) {
		this.view = view;
	}

	public void checkUsernamePasswordCorrect(String username, String password) {
		if (username.equals(USERNAME) && password.equals(PASSWORD)) {
			view.showLoginSuccess();
		} else {
			view.showLoginFail();
		}
	}

	public void checkUserLoggedIn(boolean isLoggedIn) {
		if (isLoggedIn) {
			view.loadLoggedInPage();
		} else {
			view.loadLoginPage();
		}
	}

	public void logOut() {
		view.logOut();
	}
}
