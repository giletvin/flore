package fr.flore_de_poche.ui.adapter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import fr.flore.R;
import fr.flore_de_poche.bo.MediaFileType;
import fr.flore_de_poche.bo.SimpleSubject;
import fr.flore_de_poche.helper.BasicConstants;
import fr.flore_de_poche.helper.Constants;

/**
 * The Class SearchResultsAdapter.
 */
public class SearchResultsAdapter extends BaseAdapter {

	/** The objects. */
	private List<SimpleSubject> objects;

	/** The context. */
	private Context context;

	/**
	 * Instantiates a new search results adapter.
	 * 
	 * @param context
	 *            the context
	 * @param birds
	 *            the birds
	 */
	public SearchResultsAdapter(Context context, List<SimpleSubject> birds) {
		this.context = context;
		this.objects = birds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return objects.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public SimpleSubject getItem(int position) {
		return objects.get(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * The Class ViewHolder.
	 */
	private static class ViewHolder {

		/** The bird icon. */
		ImageView birdIcon;

		/** The bird name. */
		TextView birdName;

		/** The bird scientific name. */
		TextView birdScientificName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.row_result, parent, false);

			viewHolder = new ViewHolder();
			viewHolder.birdScientificName = (TextView) convertView
					.findViewById(R.id.scientific_name);
			viewHolder.birdName = (TextView) convertView
					.findViewById(R.id.taxon);
			viewHolder.birdIcon = (ImageView) convertView
					.findViewById(R.id.bird_icon);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		SimpleSubject bird = getItem(position);

		viewHolder.birdName.setText(bird.getTaxon());
		viewHolder.birdScientificName.setText(bird.getScientificName());

		final AssetManager assetManager = Constants.getCONTEXT().getAssets();
		InputStream ins;
		Bitmap bMap;
		try {
			ins = assetManager.open(BasicConstants.ICONS_DIRECTORY
					+ File.separator + bird.getDirectoryName()
					+ MediaFileType.PICTURE_EXTENSION);
			bMap = BitmapFactory.decodeStream(ins);

		} catch (final IOException e) {
			Log.e(BasicConstants.LOG_TAG, e.getMessage());
			bMap = BitmapFactory.decodeResource(Constants.getCONTEXT()
					.getResources(), R.drawable.ic_default_bird_icon);
		}
		viewHolder.birdIcon.setImageBitmap(bMap);

		return convertView;
	}

}
