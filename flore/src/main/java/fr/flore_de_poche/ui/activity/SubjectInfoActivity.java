package fr.flore_de_poche.ui.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import fr.flore.R;
import fr.flore_de_poche.helper.Constants;
import fr.flore_de_poche.service.IService;
import fr.flore_de_poche.service.ServiceFactory;
import fr.flore_de_poche.ui.adapter.SubjectActivityTabsPagerAdapter;

/**
 * The Class SubjectInfoActivity.
 */
@EActivity(R.layout.subject_activity)
@OptionsMenu(R.menu.options_menu)
public class SubjectInfoActivity extends FragmentActivity implements
		ActionBar.TabListener {

	/** The Constant INTENT_ACTIVITY_TO_OPEN. */
	public static final String INTENT_TAB_TO_OPEN = "intentTabToOpenParameter";

	@Extra(INTENT_TAB_TO_OPEN)
	int tabToOpen = 0;

	/** The subjectId. */
	@Extra(MainActivity.SUBJECT_ID_ITENT_PRM)
	int subjectId = 0;
	/** The ornidroid service. */
	private final IService service = ServiceFactory.getService(this);

	/** The view pager. */
	@ViewById(R.id.pager)
	ViewPager viewPager;

	/** The m adapter. */
	private SubjectActivityTabsPagerAdapter mAdapter;

	/** The action bar. */
	private ActionBar actionBar;

	/**
	 * After views.
	 */
	@AfterViews
	void afterViews() {
		Constants.initializeConstants(this);
		// Initilization
		actionBar = getActionBar();
		mAdapter = new SubjectActivityTabsPagerAdapter(
				getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		// actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.addTab(actionBar.newTab().setIcon(R.drawable.ic_tab_pictures)
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab()
				.setIcon(R.drawable.ic_tab_wikipedia).setTabListener(this));
		actionBar.addTab(actionBar.newTab()
				.setIcon(R.drawable.ic_tab_bird_names).setTabListener(this));
		actionBar.addTab(actionBar.newTab().setIcon(R.drawable.ic_tab_links)
				.setTabListener(this));

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			public void onPageScrollStateChanged(int arg0) {
			}
		});

		viewPager.setCurrentItem(tabToOpen);
		if (0 != this.subjectId) {
			this.service.loadSubjectDetails(subjectId);
		}
		this.service.getCurrentSubject();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.app.ActionBar.TabListener#onTabReselected(android.app.ActionBar
	 * .Tab, android.app.FragmentTransaction)
	 */
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.app.ActionBar.TabListener#onTabSelected(android.app.ActionBar
	 * .Tab, android.app.FragmentTransaction)
	 */
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.app.ActionBar.TabListener#onTabUnselected(android.app.ActionBar
	 * .Tab, android.app.FragmentTransaction)
	 */
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	/**
	 * Gets the current tab id.
	 * 
	 * @return the current tab id
	 */
	public int getCurrentTabId() {
		return viewPager.getCurrentItem();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		super.onStop();
	}

	/**
	 * Search menu clicked.
	 */
	@OptionsItem(R.id.search)
	void searchMenuClicked() {
		MainActivity_.intent(this).start();
	}

	/**
	 * Search multi menu clicked.
	 */
	@OptionsItem(R.id.search_multi)
	void searchMultiMenuClicked() {
		startActivity(new Intent(this, MultiCriteriaSearchActivity_.class));
	}

	/**
	 * Preferences menu clicked.
	 */
	@OptionsItem(R.id.preferences)
	void preferencesMenuClicked() {
		startActivity(new Intent(this, ApplicationPreferenceActivity_.class));
	}

	/**
	 * Home menu clicked.
	 */
	@OptionsItem(R.id.home)
	void homeMenuClicked() {
		HomeActivity_.intent(this).start();
	}

	/**
	 * Help menu clicked.
	 */
	@OptionsItem(R.id.help)
	void helpMenuClicked() {
		HelpActivity_.intent(this).start();
	}
}
