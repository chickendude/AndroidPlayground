package ch.ralena.activitypractice.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ch.ralena.activitypractice.MainActivity;
import ch.ralena.activitypractice.R;
import ch.ralena.activitypractice.contracts.LoginContract;

public class LoginFragment extends BaseFragment implements LoginContract {
	LoginPresenter presenter;
	// login views
	private CardView loginCard;
	private EditText usernameEdit;
	private EditText passwordEdit;
	private boolean isLoggedIn = false;
	// logged in views
	private CardView loggedInCard;
	private Button logOutButton;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_login, container, false);

		preferences = getContext().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);

		presenter = new LoginPresenter(this);

		loginCard = rootView.findViewById(R.id.loginCard);
		loggedInCard = rootView.findViewById(R.id.loggedInCard);

		isLoggedIn = preferences.getBoolean(MainActivity.PREF_LOGGED_IN, false);
		presenter.checkUserLoggedIn(isLoggedIn);

		return rootView;
	}

	public void loadLoginPage() {
		loginCard.setVisibility(View.VISIBLE);
		loggedInCard.setVisibility(View.INVISIBLE);
		usernameEdit = rootView.findViewById(R.id.usernameEdit);
		passwordEdit = rootView.findViewById(R.id.passwordEdit);
		Button loginButton = rootView.findViewById(R.id.loginButton);
		loginButton.setOnClickListener(v -> {
			login();
		});
	}

	@Override
	public void loadLoggedInPage() {
		loginCard.setVisibility(View.INVISIBLE);
		loggedInCard.setVisibility(View.VISIBLE);
		logOutButton = rootView.findViewById(R.id.logOutButton);
		logOutButton.setOnClickListener(view -> presenter.logOut());
	}

	@Override
	public void logOut() {
		isLoggedIn = false;
		preferences.edit().putBoolean(MainActivity.PREF_LOGGED_IN, false).apply();
		presenter.checkUserLoggedIn(isLoggedIn);
	}

	private void login() {
		String username = usernameEdit.getText().toString();// == null ? "" : usernameEdit.getText().toString();
		String password = passwordEdit.getText() == null ? "" : passwordEdit.getText().toString();
		presenter.checkUsernamePasswordCorrect(username, password);
	}


	@Override
	public void showLoginSuccess() {
		isLoggedIn = true;
		preferences.edit().putBoolean(MainActivity.PREF_LOGGED_IN, true).apply();
		Snackbar.make(rootView, "Logged in successfully!", Snackbar.LENGTH_SHORT).show();
		presenter.checkUserLoggedIn(isLoggedIn);
	}

	@Override
	public void showLoginFail() {
		Snackbar.make(rootView, "Log in failed!", Snackbar.LENGTH_SHORT).show();
	}
}
