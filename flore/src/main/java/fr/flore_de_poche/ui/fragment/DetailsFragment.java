package fr.flore_de_poche.ui.fragment;

import java.util.List;

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
import fr.flore_de_poche.bo.Subject;
import fr.flore_de_poche.helper.BasicConstants;
import fr.flore_de_poche.helper.StringHelper;
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

	/** The category. */
	@ViewById(R.id.details_category)
	TextView category;

	/** The order and family. */
	@ViewById(R.id.details_order_family)
	TextView orderAndFamily;

	/** The size. */
	@ViewById(R.id.details_size)
	TextView size;

	/** The description. */
	@ViewById(R.id.details_description)
	TextView description;

	/** The distribution. */
	@ViewById(R.id.details_distribution)
	TextView distribution;

	/** The list countries. */
	@ViewById(R.id.details_countries)
	TextView listCountries;

	/** The xeno canto map link. */
	@ViewById(R.id.details_xeno_canto)
	TextView xenoCantoMapLink;

	/** The wikipedia link. */
	@ViewById(R.id.details_wikipedia)
	TextView wikipediaLink;

	/** The oiseaux net link. */
	@ViewById(R.id.details_oiseaux_net)
	TextView oiseauxNetLink;

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
			printBirdCategory(mOrnidroidService.getCurrentSubject());
			printBirdOrderAndFamily(mOrnidroidService.getCurrentSubject());
			printBirdSize(mOrnidroidService.getCurrentSubject());
			printBirdDescription(mOrnidroidService.getCurrentSubject());
			printBirdDistributionAndBehaviour(mOrnidroidService
					.getCurrentSubject());
			fillCountriesList();
			printHttpLinks();

		}
	}

	/**
	 * Fill countries list.
	 */
	private void fillCountriesList() {
		final List<String> listCountries = this.mOrnidroidService
				.getGeographicDistribution(this.mOrnidroidService
						.getCurrentSubject().getId());
		if (!listCountries.isEmpty()) {
			StringBuffer sbuf = new StringBuffer(getActivity().getText(
					R.string.geographic_distribution)
					+ BasicConstants.COLUMN_STRING
					+ BasicConstants.CARRIAGE_RETURN
					+ BasicConstants.CARRIAGE_RETURN);

			for (String country : listCountries) {
				sbuf.append(country + BasicConstants.CARRIAGE_RETURN);
			}

			this.listCountries.setText(sbuf.toString());
		}

	}

	/**
	 * Prints the bird category.
	 * 
	 * @param currentBird
	 *            the current bird
	 */
	private void printBirdCategory(final Subject currentBird) {
		this.category.setText(getActivity().getText(R.string.search_category)
				+ BasicConstants.COLUMN_STRING + currentBird.getCategory());
	}

	/**
	 * Prints the bird description.
	 * 
	 * @param bird
	 *            the bird
	 */
	private void printBirdDescription(final Subject bird) {
		if (StringHelper.isNotBlank(bird.getDescription())) {
			this.description.setText(getActivity()
					.getText(R.string.description)
					+ BasicConstants.COLUMN_STRING
					+ BasicConstants.CARRIAGE_RETURN
					+ BasicConstants.CARRIAGE_RETURN + bird.getDescription());

		}
	}

	/**
	 * Prints the bird distribution and behaviour.
	 * 
	 * @param bird
	 *            the bird
	 */
	private void printBirdDistributionAndBehaviour(final Subject bird) {
		final StringBuilder distributionAndBehaviour = new StringBuilder();
		distributionAndBehaviour.append(getActivity().getText(
				R.string.distribution));
		distributionAndBehaviour.append(BasicConstants.COLUMN_STRING);
		distributionAndBehaviour.append(BasicConstants.CARRIAGE_RETURN);
		distributionAndBehaviour.append(BasicConstants.CARRIAGE_RETURN);
		distributionAndBehaviour.append(bird.getHabitat());
		if (StringHelper.isNotBlank(bird.getDistribution())) {
			distributionAndBehaviour.append(BasicConstants.CARRIAGE_RETURN);
			distributionAndBehaviour.append(BasicConstants.CARRIAGE_RETURN);
			distributionAndBehaviour.append(bird.getDistribution());
		}
		this.distribution.setText(distributionAndBehaviour.toString());
	}

	/**
	 * Prints the bird order and family.
	 * 
	 * @param bird
	 *            the bird
	 */
	private void printBirdOrderAndFamily(final Subject bird) {
		if (StringHelper.isNotBlank(bird.getScientificFamily())
				&& StringHelper.isNotBlank(bird.getScientificOrder())) {
			this.orderAndFamily
					.setText(getActivity().getText(R.string.scientific_order)
							+ BasicConstants.COLUMN_STRING
							+ bird.getScientificOrder()
							+ BasicConstants.CARRIAGE_RETURN
							+ BasicConstants.CARRIAGE_RETURN
							+ getActivity().getText(R.string.scientific_family)
							+ BasicConstants.COLUMN_STRING
							+ bird.getScientificFamily());
		}
	}

	/**
	 * Prints the bird size.
	 * 
	 * @param currentBird
	 *            the current bird
	 */
	private void printBirdSize(final Subject currentBird) {

		this.size.setText(getActivity().getText(R.string.search_size)
				+ BasicConstants.COLUMN_STRING + currentBird.getSize()
				+ BasicConstants.BLANK_STRING
				+ getActivity().getText(R.string.cm));

	}

	/**
	 * Prints the http links.
	 */
	private void printHttpLinks() {
		final String xenoCantoUrl = this.mOrnidroidService
				.getXenoCantoMapUrl(this.mOrnidroidService.getCurrentSubject());
		this.xenoCantoMapLink.setText(Html.fromHtml(xenoCantoUrl));
		this.xenoCantoMapLink.setMovementMethod(LinkMovementMethod
				.getInstance());

		final String wikipedia = this.mOrnidroidService
				.getWikipediaLink(this.mOrnidroidService.getCurrentSubject());

		this.wikipediaLink.setText(Html.fromHtml(wikipedia));
		this.wikipediaLink.setMovementMethod(LinkMovementMethod.getInstance());
		final String oiseauxNet = this.mOrnidroidService
				.getOiseauxNetLink(this.mOrnidroidService.getCurrentSubject());
		if (StringHelper.isNotBlank(oiseauxNet)) {
			this.oiseauxNetLink.setText(Html.fromHtml(oiseauxNet));
			this.oiseauxNetLink.setMovementMethod(LinkMovementMethod
					.getInstance());
		}
	}
}
