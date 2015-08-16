package fr.flore_de_poche.bo;

import fr.flore_de_poche.helper.BasicConstants;

public enum MediaFileType {

	PICTURE,

	WIKIPEDIA_PAGE;

	public final static String FILE_TYPE_INTENT_PARAM_NAME = "FILE_TYPE";

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
				code = BasicConstants.PICTURE_TAB;
				break;

			case WIKIPEDIA_PAGE:
				code = BasicConstants.WIKIPEDIA_TAB;
				break;

			}
		}
		return code;
	}

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
