package fr.flore_de_poche.bo;

import java.util.List;

public class Subject extends SimpleSubject {

	/** The pictures. */
	private List<MediaFile> pictures;

	/** The wikipedia page. */
	private MediaFile wikipediaPage;

	/**
	 * Gets the wikipedia page.
	 * 
	 * @return the wikipedia page
	 */
	public MediaFile getWikipediaPage() {
		return wikipediaPage;
	}

	/**
	 * Sets the wikipedia page.
	 * 
	 * @param wikipediaPage
	 *            the new wikipedia page
	 */
	public void setWikipediaPage(MediaFile wikipediaPage) {
		this.wikipediaPage = wikipediaPage;
	}

	/**
	 * Instantiates a new bird.
	 */
	protected Subject() {
		super();
	}

	/**
	 * Gets the number of pictures.
	 * 
	 * @return the number of pictures, 0 if list of pictures is null
	 */
	public int getNumberOfPictures() {
		if (this.pictures != null) {
			return this.pictures.size();
		}
		return 0;
	}

	/**
	 * Gets the picture.
	 * 
	 * @param pictureNumber
	 *            the picture number
	 * @return the picture
	 */
	public MediaFile getPicture(final int pictureNumber) {
		if ((this.pictures != null) && (this.pictures.size() > pictureNumber)) {
			return this.pictures.get(pictureNumber);
		} else {
			return null;
		}
	}

	/**
	 * Gets the list of pictures.
	 * 
	 * @return the pictures.
	 */
	public List<MediaFile> getPictures() {
		// if (pictures == null) {
		// setPictures(new ArrayList<AbstractOrnidroidFile>());
		// }
		return this.pictures;
	}

	/**
	 * Sets the pictures.
	 * 
	 * @param pictures
	 *            the new pictures
	 */
	public void setPictures(final List<MediaFile> pictures) {
		this.pictures = pictures;
	}

}
