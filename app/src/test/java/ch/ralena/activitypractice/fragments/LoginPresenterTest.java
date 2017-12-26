package ch.ralena.activitypractice.fragments;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {
	LoginPresenter presenter;

	@Mock
	LoginFragment view;

	@Before
	public void setUp() throws Exception {
		presenter = new LoginPresenter(view);
	}

	@Test
	public void checkUsernamePasswordCorrectShowsSuccess() throws Exception {
		// arrange
		String username = "coolguy";
		String password = "password";

		// act
		presenter.checkUsernamePasswordCorrect(username, password);

		// assert
		Mockito.verify(view).showLoginSuccess();
	}

	@Test
	public void checkUsernamePasswordIncorrectShowsFailure() throws Exception {
		// arrange
		String username = "coolguy";
		String password = "wrongPassword";

		// act
		presenter.checkUsernamePasswordCorrect(username, password);

		// assert
		Mockito.verify(view).showLoginFail();
	}

	@Test
	public void loadLoggedInPageWhenLoggedIn() throws Exception {
		// arrange
		boolean isLoggedIn = true;

		// act
		presenter.checkUserLoggedIn(isLoggedIn);

		// assert
		Mockito.verify(view).loadLoggedInPage();
	}

	@Test
	public void loadLoginPageWhenNotLoggedIn() throws Exception {
		// arrange
		boolean isLoggedIn = false;

		// act
		presenter.checkUserLoggedIn(isLoggedIn);

		// assert
		Mockito.verify(view).loadLoginPage();
	}

}