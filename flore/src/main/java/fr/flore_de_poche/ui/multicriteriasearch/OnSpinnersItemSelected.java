package fr.flore_de_poche.ui.multicriteriasearch;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import fr.flore_de_poche.ui.activity.MultiCriteriaSearchActivity;

/**
 * This class handles the clicks on the spinners items in the
 * MultiCriteriaSearchActivity.
 */
public class OnSpinnersItemSelected implements OnItemSelectedListener {

	/** The activity. */
	private final MultiCriteriaSearchActivity activity;

	/**
	 * Instantiates a new on spinners item selected.
	 * 
	 * @param pActivity
	 *            the activity
	 */
	public OnSpinnersItemSelected(final MultiCriteriaSearchActivity pActivity) {
		super();
		this.activity = pActivity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.AdapterView.OnItemSelectedListener#onItemSelected(
	 * android.widget.AdapterView, android.view.View, int, long)
	 */
	public void onItemSelected(final AdapterView<?> parent, final View view,
			final int pos, final long id) {
		final MultiCriteriaSearchFieldType selectType = this.activity
				.getSelectType(parent);
		switch (selectType) {
		case SCIENTIFIC_FAMILY:
			this.activity.getFormBean().setScientificFamilyId(
					this.activity.getOrnidroidService().getScientificFamilyId(
							parent.getItemAtPosition(pos).toString()));
			break;
		case INFLORESCENCE:
			this.activity.getFormBean().setInflorescenceId(
					this.activity.getOrnidroidService().getInflorescenceId(
							parent.getItemAtPosition(pos).toString()));
			break;
		case ASPECT:
			this.activity.getFormBean().setAspectId(
					this.activity.getOrnidroidService().getAspectId(
							parent.getItemAtPosition(pos).toString()));
			break;
		case PARTICULARITE:
			this.activity.getFormBean().setParticulariteId(
					this.activity.getOrnidroidService().getParticulariteId(
							parent.getItemAtPosition(pos).toString()));
			break;
		case LEAF_TYPE:
			this.activity.getFormBean().setLeafTypeId(
					this.activity.getOrnidroidService().getLeafTypeId(
							parent.getItemAtPosition(pos).toString()));
			break;
		case NB_PETALE:
			this.activity.getFormBean().setNbPetaleId(
					this.activity.getOrnidroidService().getNbPetaleId(
							parent.getItemAtPosition(pos).toString()));
			break;
		case PILOSITE_FEUILLE:
			this.activity.getFormBean().setPilositeFeuilleId(
					this.activity.getOrnidroidService().getPilositeFeuilleId(
							parent.getItemAtPosition(pos).toString()));
			break;
		case PILOSITE_TIGE:
			this.activity.getFormBean().setPilositeTigeId(
					this.activity.getOrnidroidService().getPilositeTigeId(
							parent.getItemAtPosition(pos).toString()));
			break;
		case COLOUR:
			this.activity.getFormBean().setColourId(
					this.activity.getOrnidroidService().getColourId(
							parent.getItemAtPosition(pos).toString()));
			break;

		case LEAF_DISPOSITION:
			this.activity.getFormBean().setLeafDispositionId(
					this.activity.getOrnidroidService().getLeafDispositionId(
							parent.getItemAtPosition(pos).toString()));
			break;

		}

		this.activity.updateSearchCountResults(this.activity
				.getOrnidroidService().getMultiSearchCriteriaCountResults(
						this.activity.getFormBean()));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.AdapterView.OnItemSelectedListener#onNothingSelected
	 * (android .widget.AdapterView)
	 */
	public void onNothingSelected(final AdapterView<?> arg0) {
		// not implemented
	}
}
