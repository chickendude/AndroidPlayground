package ch.ralena.activitypractice;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import ch.ralena.activitypractice.fragments.AgeCalculatorFragment;
import ch.ralena.activitypractice.fragments.ColorPickerFragment;
import ch.ralena.activitypractice.fragments.LoginFragment;

public class MainActivity extends AppCompatActivity {
	public static final String PREF_LOGGED_IN = "pref_logged_in";

	SharedPreferences sharedPreferences;
	ViewPager viewPager;
	TabLayout tabLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);

		// create tabs
		tabLayout = findViewById(R.id.tabLayout);
		String[] fragmentTitles = {"Login", "Age Calculator", "Color Picker"};
		int tabId = 0;
		for (String title : fragmentTitles) {
			TabLayout.Tab tab = tabLayout.newTab();
			tab.setText(title);
			tab.setTag(tabId++);
			tabLayout.addTab(tab);
		}

		// set up tab listener to make sure correct fragment gets shown
		tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				// tag = tab position
				int position = (int) tab.getTag();
				boolean isLoggedIn = sharedPreferences.getBoolean(PREF_LOGGED_IN, false);
				if (!isLoggedIn && position > 0) {
					Snackbar.make(viewPager, String.format("Please log in before accessing %s!", fragmentTitles[position]), Snackbar.LENGTH_SHORT).show();
					tabLayout.getTabAt(0).select();
				} else {
					viewPager.setCurrentItem(position);
				}
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {

			}
		});

		// set up viewpager
		viewPager = findViewById(R.id.viewPager);
		viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public Fragment getItem(int position) {
				switch (position) {
					case 0:
						return new LoginFragment();
					case 1:
						return new AgeCalculatorFragment();
					case 2:
						return new ColorPickerFragment();
				}
				return new LoginFragment();
			}

			@Override
			public int getCount() {
				return 3;
			}
		});

		// coordinate viewpager and tablayout
		viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
	}
}
