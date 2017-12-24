package ch.ralena.activitypractice.contracts;

/**
 * Created by oversluij on 12/23/2017.
 */

public interface LoginContract {
	void loadLoginPage();
	void loadLoggedInPage();
	void logOut();
	void showLoginSuccess();
	void showLoginFail();
}
