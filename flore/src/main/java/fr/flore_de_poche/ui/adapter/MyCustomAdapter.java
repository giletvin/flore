package fr.flore_de_poche.ui.adapter;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import fr.flore.R;
import fr.flore_de_poche.ui.activity.MultiCriteriaSearchActivity;
import fr.flore_de_poche.ui.multicriteriasearch.MultiCriteriaSearchFieldType;
import fr.flore_de_poche.ui.multicriteriasearch.SpinnerIconSelector;

/**
 * The Class MyCustomAdapter. This class allows to print icons and text in a
 * spinner
 * 
 * @see http 
 *      ://android-er.blogspot.fr/2010/12/custom-arrayadapter-for-spinner-with
 *      .html
 */
public class MyCustomAdapter extends ArrayAdapter<String> {

	/** The activity. */
	private final MultiCriteriaSearchActivity activity;
	/** The items list. */
	private final List<String> itemsList;

	private final MultiCriteriaSearchFieldType selectFieldType;

	/**
	 * Instantiates a new my custom adapter.
	 * 
	 * @param pActivity
	 *            the activity
	 * @param textViewResourceId
	 *            the text view resource id
	 * @param pItemsList
	 *            the items list
	 * @param selectFieldType
	 *            the select field type
	 */
	public MyCustomAdapter(final MultiCriteriaSearchActivity pActivity,
			final int textViewResourceId, final List<String> pItemsList,
			final MultiCriteriaSearchFieldType pSelectFieldType) {
		super(pActivity, textViewResourceId, pItemsList);
		this.activity = pActivity;
		this.itemsList = pItemsList;
		this.selectFieldType = pSelectFieldType;

	}

	/**
	 * Gets the custom view.
	 * 
	 * @param position
	 *            the position
	 * @param convertView
	 *            the convert view
	 * @param parent
	 *            the parent
	 * @return the custom view
	 */
	public View getCustomView(final int position, final View convertView,
			final ViewGroup parent, boolean dropDownStyle) {

		final LayoutInflater inflater = this.activity.getLayoutInflater();
		View row;
		ImageView icon;
		if (dropDownStyle) {
			row = inflater.inflate(R.layout.row_spinner_icons_dropdown_list,
					parent, false);
			final TextView label = (TextView) row
					.findViewById(R.id.spinner_text_dropdown_list);
			label.setText(this.itemsList.get(position));

			icon = (ImageView) row
					.findViewById(R.id.spinner_icon_dropdown_list);
		} else {
			row = inflater.inflate(R.layout.row_spinner_icons, parent, false);
			final TextView label = (TextView) row
					.findViewById(R.id.spinner_text);
			label.setText(this.itemsList.get(position));

			icon = (ImageView) row.findViewById(R.id.spinner_icon);
		}

		int idRes = 0;
		switch (this.selectFieldType) {

		case LEAF_TYPE:
			final int leafTypeId = this.activity.getOrnidroidService()
					.getLeafTypeId(this.itemsList.get(position));
			idRes = SpinnerIconSelector
					.getIconResourceIdFromLeafTypeId(leafTypeId);
			break;
		case PILOSITE_FEUILLE:
			final int pilositeFeuilleId = this.activity.getOrnidroidService()
					.getPilositeFeuilleId(this.itemsList.get(position));
			idRes = SpinnerIconSelector
					.getIconResourceIdFromPilositeFeuilleId(pilositeFeuilleId);
			break;
		case INFLORESCENCE:
			final int inflorescenceId = this.activity.getOrnidroidService()
					.getInflorescenceId(this.itemsList.get(position));
			idRes = SpinnerIconSelector
					.getIconResourceIdFromInflorescenceId(inflorescenceId);
			break;
		case ASPECT:
			final int aspectId = this.activity.getOrnidroidService()
					.getAspectId(this.itemsList.get(position));
			idRes = SpinnerIconSelector.getIconResourceIdFromAspectId(aspectId);
			break;
		case LEAF_DISPOSITION:
			final int leafDispositionId = this.activity.getOrnidroidService()
					.getLeafDispositionId(this.itemsList.get(position));
			idRes = SpinnerIconSelector
					.getIconResourceIdFromLeafDispositionId(leafDispositionId);
			break;
		case PARTICULARITE:
			final int particulariteId = this.activity.getOrnidroidService()
					.getParticulariteId(this.itemsList.get(position));
			idRes = SpinnerIconSelector
					.getIconResourceIdFromParticulariteId(particulariteId);
			break;
		case PILOSITE_TIGE:
			final int pilositeTigeId = this.activity.getOrnidroidService()
					.getPilositeTigeId(this.itemsList.get(position));
			idRes = SpinnerIconSelector
					.getIconResourceIdFromPilositeTigeId(pilositeTigeId);
			break;
		default:
			break;

		}
		icon.setImageResource(idRes);
		return row;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ArrayAdapter#getDropDownView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getDropDownView(final int position, final View convertView,
			final ViewGroup parent) {
		View v = getCustomView(position, convertView, parent, true);
		v.setBackgroundResource(R.color.mcs_custom_spinner_items_dropdown_background);

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(final int position, final View convertView,
			final ViewGroup parent) {
		return getCustomView(position, convertView, parent, false);
	}
}