package fr.flore_de_poche.ui.fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import fr.flore.R;
import fr.flore_de_poche.helper.SupportedLanguage;
import fr.flore_de_poche.service.IService;
import fr.flore_de_poche.service.ServiceFactory;

/**
 * The Class DetailsFragment.
 */
@EFragment(R.layout.fragment_details)
public class DetailsFragment extends Fragment {

	/** The m ornidroid service. */
	private IService mOrnidroidService = ServiceFactory
			.getService(getActivity());

	@ViewById(R.id.details_fr_wikipedia)
	TextView frenchWikipediaLink;

	@ViewById(R.id.details_en_wikipedia)
	TextView englishWikipediaLink;

	@ViewById(R.id.details_doc_url)
	TextView docUrlLink;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Association du layout pour ce Fragment
		return inflater.inflate(R.layout.fragment_details, container, false);

	}

	@AfterViews
	public void afterViews() {
		if (mOrnidroidService.getCurrentSubject() != null) {

			printHttpLinks();

		}
	}

	/**
	 * Prints the http links.
	 */
	private void printHttpLinks() {

		final String frenchWikipedia = this.mOrnidroidService.getWikipediaLink(
				this.mOrnidroidService.getCurrentSubject(),
				SupportedLanguage.FRENCH);
		final String englishWikipedia = this.mOrnidroidService
				.getWikipediaLink(this.mOrnidroidService.getCurrentSubject(),
						SupportedLanguage.ENGLISH);

		this.frenchWikipediaLink.setText(Html.fromHtml(frenchWikipedia));
		this.frenchWikipediaLink.setMovementMethod(LinkMovementMethod
				.getInstance());

		this.englishWikipediaLink.setText(Html.fromHtml(englishWikipedia));
		this.englishWikipediaLink.setMovementMethod(LinkMovementMethod
				.getInstance());

		final String docUrl = this.mOrnidroidService
				.getDocUrlLink(this.mOrnidroidService.getCurrentSubject());

		this.docUrlLink.setText(Html.fromHtml(docUrl));
		this.docUrlLink.setMovementMethod(LinkMovementMethod.getInstance());

	}
}
