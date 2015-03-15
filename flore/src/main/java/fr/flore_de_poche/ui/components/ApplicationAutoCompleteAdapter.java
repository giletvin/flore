package fr.flore_de_poche.ui.components;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import fr.flore_de_poche.bo.SimpleSubject;

/**
 * The Class OrnidroidAutoCompleteAdapter.
 */
public class ApplicationAutoCompleteAdapter extends ArrayAdapter<SimpleSubject> {

	/** The m birds list. */
	private List<SimpleSubject> mBirdsList;

	/**
	 * Instantiates a new ornidroid auto complete adapter.
	 * 
	 * @param context
	 *            the context
	 * @param resource
	 *            the resource
	 * @param birdsList
	 *            the birds list
	 */
	public ApplicationAutoCompleteAdapter(Context context, int resource,
			List<SimpleSubject> birdsList) {
		super(context, resource, birdsList);
		this.mBirdsList = birdsList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ArrayAdapter#getFilter()
	 */
	@Override
	public Filter getFilter() {
		Filter myFilter = new Filter() {

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				// Do not filter anything. Everything is okay : the filtering is
				// done is the SQL query
				// the user can type 'mesange' --> we should not drop 'mésange'.
				FilterResults results = new FilterResults();
				results.values = mBirdsList;
				results.count = mBirdsList.size();
				return results;

			}

			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				// Now we have to inform the adapter about the new list filtered
				if (results.count == 0)
					notifyDataSetInvalidated();
				else {
					mBirdsList = (List<SimpleSubject>) results.values;
					notifyDataSetChanged();
				}

			}

		};
		return myFilter;
	}
}
