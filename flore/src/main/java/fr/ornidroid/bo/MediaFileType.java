package fr.ornidroid.bo;

import fr.ornidroid.helper.BasicConstants;

/**
 * The Enum OrnidroidFileType.
 */
public enum MediaFileType {

	/** The PICTURE. */
	PICTURE,

	/** The wikipedia page. */
	WIKIPEDIA_PAGE;

	/** The Constant FILE_TYPE_PARAM_NAME. */
	public final static String FILE_TYPE_INTENT_PARAM_NAME = "FILE_TYPE";
	/** The Constant PICTURE_EXTENSION. */
	public final static String PICTURE_EXTENSION = ".jpg";

	/**
	 * Gets the code. Must be the same as tab id
	 * 
	 * @param type
	 *            the type
	 * @return the code
	 */
	public static int getCode(final MediaFileType type) {
		int code = 0;
		if (null != type) {
			switch (type) {
			case PICTURE:
				code = 0;
				break;

			case WIKIPEDIA_PAGE:
				code = 3;
				break;

			}
		}
		return code;
	}

	/**
	 * Gets the extension.
	 * 
	 * @param type
	 *            the type
	 * @return the extension
	 */
	public static String getExtension(final MediaFileType type) {
		String extension = null;
		switch (type) {
		case PICTURE:
			extension = PICTURE_EXTENSION;
			break;

		case WIKIPEDIA_PAGE:
			extension = BasicConstants.EMPTY_STRING;
		}
		return extension;
	}
}
