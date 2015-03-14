package fr.ornidroid.ui.fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.webkit.WebView;
import fr.flore.R;
import fr.ornidroid.bo.MediaFile;
import fr.ornidroid.bo.MediaFileType;

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
			MediaFile wikipediaPage = ornidroidService.getCurrentBird()
					.getWikipediaPage();
			wikipediaWebView.loadUrl("file:///" + wikipediaPage.getPath());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.ornidroid.ui.components.AbstractFragment#getFileType()
	 */
	@Override
	public MediaFileType getFileType() {
		return MediaFileType.WIKIPEDIA_PAGE;
	}

}
