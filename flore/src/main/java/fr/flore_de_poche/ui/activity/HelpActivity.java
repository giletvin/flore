package fr.flore_de_poche.ui.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.text.util.Linkify;
import android.widget.TextView;
import fr.flore.R;

/**
 * The Class HelpActivity.
 */
@EActivity(R.layout.help)
public class HelpActivity extends AbstractActivity {

	/** The help text. */
	@ViewById(R.id.help_text)
	TextView helpText;

	/**
	 * After views.
	 */
	@AfterViews
	void afterViews() {
		Linkify.addLinks(helpText, Linkify.ALL);
	}

}
