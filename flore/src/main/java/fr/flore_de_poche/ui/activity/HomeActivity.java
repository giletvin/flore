package fr.flore_de_poche.ui.activity;

import java.io.File;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.widget.TextView;
import fr.flore.R;
import fr.flore_de_poche.helper.ApplicationException;
import fr.flore_de_poche.helper.BasicConstants;
import fr.flore_de_poche.helper.StringHelper;
import fr.flore_de_poche.service.IIOService;
import fr.flore_de_poche.service.IOServiceImpl;
import fr.flore_de_poche.service.IService;
import fr.flore_de_poche.service.ServiceFactory;
import fr.flore_de_poche.ui.preferences.MyPrefs_;

/**
 * The Class HomeActivity. Start screen of the application
 */
@EActivity(R.layout.home)
public class HomeActivity extends AbstractActivity {

	/** The my prefs. */
	@Pref
	MyPrefs_ myPrefs;

	/** The about link. */
	@ViewById(R.id.menu_about)
	TextView aboutLink;

	/** The help link. */
	@ViewById(R.id.menu_help)
	TextView helpLink;

	/** The multi criteria search link. */
	@ViewById(R.id.menu_search_multi)
	TextView multiCriteriaSearchLink;

	/** The ornidroid io service. */
	private final IIOService iOService;

	/** The ornidroid service. */
	private final IService service;

	/** The preferences link. */
	@ViewById(R.id.menu_preferences)
	TextView preferencesLink;

	/** The search link. */
	@ViewById(R.id.menu_search)
	TextView searchLink;

	/**
	 * Instantiates a new home activity.
	 */
	public HomeActivity() {
		super();
		this.iOService = new IOServiceImpl();
		this.service = ServiceFactory.getService(this);
	}

	/**
	 * Click search link.
	 */
	@Click(R.id.menu_search)
	void clickSearchLink() {
		launchActivity(MainActivity_.class);

	}

	/**
	 * Click search multi link.
	 */
	@Click(R.id.menu_search_multi)
	void clickSearchMultiLink() {
		launchActivity(MultiCriteriaSearchActivity_.class);
	}

	/**
	 * Click about link.
	 */
	@Click(R.id.menu_about)
	void clickAboutLink() {
		launchActivity(AboutActivity_.class);
	}

	/**
	 * Click help link.
	 */
	@Click(R.id.menu_help)
	void clickHelpLink() {
		launchActivity(HelpActivity_.class);
	}

	/**
	 * Click preferences link.
	 */
	@Click(R.id.menu_preferences)
	void clickPreferencesLink() {
		launchActivity(ApplicationPreferenceActivity_.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
		checkOrnidroidHomeDirectory();
		String releaseNotes = service.getReleaseNotes();
		if (StringHelper.isNotBlank(releaseNotes)) {
			Dialog dialog = new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle(R.string.release_notes)
					.setMessage(releaseNotes)
					.setPositiveButton(R.string.ok,
							new DialogInterface.OnClickListener() {
								public void onClick(
										final DialogInterface dialog,
										final int whichButton) {
									dialog.dismiss();
								}
							}).create();
			dialog.show();
		}
	}

	/**
	 * Check ornidroid home directory.
	 */
	private void checkOrnidroidHomeDirectory() {
		try {
			this.iOService.checkOrnidroidHome(myPrefs.ornidroidHome().getOr(
					Environment.getExternalStorageDirectory().getAbsolutePath()
							+ File.separator
							+ BasicConstants.APPLICATION_DIRECTORY));

			// check the ornidroid.sqlite file.
			this.service.createDbIfNecessary();

		} catch (final ApplicationException e) {
			StringBuffer sbuf = new StringBuffer();
			if (e.getSourceExceptionMessage() != null) {
				sbuf.append(BasicConstants.CARRIAGE_RETURN);
				sbuf.append(e.getSourceExceptionMessage());
			}
			Dialog dialog;
			switch (e.getErrorType()) {
			case DATABASE_NOT_FOUND:
				sbuf.insert(
						0,
						this.getText(R.string.dialog_alert_ornidroid_database_not_found));
				dialog = new AlertDialog.Builder(this)
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle(R.string.warning)
						.setMessage(sbuf.toString())
						.setNegativeButton(R.string.ok,
								new DialogInterface.OnClickListener() {
									public void onClick(
											final DialogInterface dialog,
											final int id) {
										dialog.cancel();
									}
								}).create();
				break;
			case ORNIDROID_HOME_NOT_FOUND:
				sbuf.insert(
						0,
						this.getText(R.string.dialog_alert_ornidroid_home_not_found));
				dialog = new AlertDialog.Builder(this)
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle(R.string.warning)
						.setMessage(sbuf.toString())
						.setNegativeButton(R.string.ok,
								new DialogInterface.OnClickListener() {
									public void onClick(
											final DialogInterface dialog,
											final int id) {
										dialog.cancel();
									}
								}).create();
				break;
			default:
				sbuf.insert(
						0,
						this.getText(R.string.dialog_alert_ornidroid_home_not_found));
				dialog = new AlertDialog.Builder(this)
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle(R.string.warning)
						.setMessage(sbuf.toString())
						.setPositiveButton(R.string.ok,
								new DialogInterface.OnClickListener() {
									public void onClick(
											final DialogInterface dialog,
											final int whichButton) {
										dialog.dismiss();
									}
								}).create();

				break;
			}
			dialog.show();

		}
	}

	/**
	 * Launch activity.
	 * 
	 * @param activityClass
	 *            the activity class
	 * @return true, if successful
	 */
	@SuppressWarnings("rawtypes")
	private boolean launchActivity(final Class activityClass) {
		final Intent intent = new Intent(getApplicationContext(), activityClass);
		startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		return true;
	}
}
