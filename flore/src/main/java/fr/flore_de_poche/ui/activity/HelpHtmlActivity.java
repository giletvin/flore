package fr.flore_de_poche.ui.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import android.webkit.WebView;
import fr.flore.R;
import fr.flore_de_poche.ui.multicriteriasearch.MultiCriteriaSearchFieldType;

@EActivity(R.layout.help_html)
public class HelpHtmlActivity extends AbstractActivity {

	public static final String MultiCriteriaSearchFieldTypeIntentPrm = "MultiCriteriaSearchFieldType";

	@ViewById(R.id.wv_help)
	WebView wvHelp;

	@Extra(MultiCriteriaSearchFieldTypeIntentPrm)
	MultiCriteriaSearchFieldType fieldType;

	/**
	 * After views.
	 */
	@AfterViews
	void afterViews() {
		StringBuffer sbuf = new StringBuffer("file:///android_asset/");
		switch (fieldType) {
		case ASPECT:
			sbuf.append("help_aspect.html");
			break;
		case PARTICULARITE:
			sbuf.append("help_particularite.html");
			break;
		case INFLORESCENCE:
			sbuf.append("help_inflorescence.html");
			break;
		case NB_PETALE:
			sbuf.append("help_nb_petale.html");
			break;
		case LEAF_TYPE:
			sbuf.append("help_type_feuille.html");
			break;
		default:
			break;
		}

		wvHelp.loadUrl(sbuf.toString());

	}

}
