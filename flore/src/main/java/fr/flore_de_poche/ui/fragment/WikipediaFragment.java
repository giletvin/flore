package fr.flore_de_poche.ui.fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.webkit.WebView;
import fr.flore.R;
import fr.flore_de_poche.bo.MediaFile;
import fr.flore_de_poche.bo.MediaFileType;

/**
 * The Class WikipediaFragment.
 */
@EFragment(R.layout.fragment_wikipedia)
public class WikipediaFragment extends AbstractFragment {

	@ViewById(R.id.wikipiedia_webview)
	WebView wikipediaWebView;

	@AfterViews
	void afterViews() {

		if (commonAfterViews()) {
			MediaFile wikipediaPage = service.getCurrentSubject()
					.getWikipediaPage();
			wikipediaWebView.loadUrl("file:///" + wikipediaPage.getPath());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.flore_de_poche.ui.components.AbstractFragment#getFileType()
	 */
	@Override
	public MediaFileType getFileType() {
		return MediaFileType.WIKIPEDIA_PAGE;
	}

}
