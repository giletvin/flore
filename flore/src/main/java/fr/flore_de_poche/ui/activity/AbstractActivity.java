package fr.flore_de_poche.ui.activity;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

import android.app.Activity;
import fr.flore.R;
import fr.flore_de_poche.helper.Constants;
import fr.flore_de_poche.ui.activity.ApplicationPreferenceActivity_;
import fr.flore_de_poche.ui.activity.HelpActivity_;
import fr.flore_de_poche.ui.activity.HomeActivity_;
import fr.flore_de_poche.ui.activity.MainActivity_;
import fr.flore_de_poche.ui.activity.MultiCriteriaSearchActivity_;

/**
 * The Class AbstractOrnidroidActivity. Extending this class enables the menu.
 */
@EActivity
@OptionsMenu(R.menu.options_menu)
public abstract class AbstractActivity extends Activity {

	/**
	 * Instantiates a new abstract ornidroid activity.
	 */
	public AbstractActivity() {
		super();
		Constants.initializeConstants(this);
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
		MultiCriteriaSearchActivity_.intent(this).start();
	}

	/**
	 * Preferences menu clicked.
	 */
	@OptionsItem(R.id.preferences)
	void preferencesMenuClicked() {
		ApplicationPreferenceActivity_.intent(this).start();
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
