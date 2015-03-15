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

	/** The wikipedia link. */
	@ViewById(R.id.details_wikipedia)
	TextView wikipediaLink;

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

		final String wikipedia = this.mOrnidroidService
				.getWikipediaLink(this.mOrnidroidService.getCurrentSubject());

		this.wikipediaLink.setText(Html.fromHtml(wikipedia));
		this.wikipediaLink.setMovementMethod(LinkMovementMethod.getInstance());

	}
}
