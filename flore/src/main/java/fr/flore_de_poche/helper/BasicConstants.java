package fr.flore_de_poche.helper;

import fr.flore_de_poche.bo.MediaFileType;

/**
 * The Class BasicConstants.
 */
public class BasicConstants {
	// TODO : a mettre à jour ulterieuement
	private static final int FILES_COUNT_WIKIPEDIA_PACKAGE = 1332;
	// TODO : a mettre à jour ulterieuement
	private static final int FILES_COUNT_PICTURE_PACKAGE = 1856;

	/** The Constant BIRD_PARAMETER_NAME. */
	public static final String BIRD_DIRECTORY_PARAMETER_NAME = "BIRD_DIRECTORY";
	/** The Constant BIRD_ICONS_DIRECTORY. */
	public static final String BIRD_ICONS_DIRECTORY = "bird_icons";
	/** The Constant BIRD_ID_PARAMETER_NAME. */
	public static final String BIRD_ID_PARAMETER_NAME = "BIRD_ID";

	/** The Constant BLANK_STRING. */
	public static final String BLANK_STRING = " ";
	/** The Constant CARRIAGE_RETURN. */
	public static final String CARRIAGE_RETURN = "\n";

	/** The Constant COLUMN_STRING. */
	public static final String COLUMN_STRING = ": ";

	/** The Constant UNDERSCORE_STRING. */
	public static final String UNDERSCORE_STRING = "_";

	/** The Constant COMMA. */
	public static final String COMMA_STRING = ",";
	/** The Constant CONTENTS_PROPERTIES. */
	public static final String CONTENTS_PROPERTIES = "contents.properties";

	/** The Constant CUSTOM_MEDIA_FILE_PREFIX. */
	public static final String CUSTOM_MEDIA_FILE_PREFIX = "custom_";

	/**
	 * The D b_ name. It ends with a .jpg extension although it is a sqlite file
	 */
	public final static String DB_NAME = "flore.jpg";

	/** The Constant EMPTY_STRING. */
	public static final String EMPTY_STRING = "";

	/** The Constant EQUALS_STRING. */
	public static final String EQUALS_STRING = "=";

	/** The Constant IMAGES_DIRECTORY. */
	public static final String IMAGES_DIRECTORY = "images";

	/** The Constant WIKIPEDIA_DIRECTORY. */
	public static final String WIKIPEDIA_DIRECTORY = "wikipedia";
	/** The Constant LOG_TAG. */
	public static final String LOG_TAG = "Ornidroid";

	/** The Constant MP3_PATH. */
	public static final String MP3_PATH = "MP3_PATH";
	/** The Constant NO_MEDIA_FILENAME. */
	public static final String NO_MEDIA_FILENAME = ".nomedia";
	/** The Constant SLASH_STRING. */
	public static final String SLASH_STRING = "/";

	/** The Constant DASH_STRING. */
	public static final String DASH_STRING = "-";

	/** The Constant STAR_STRING. */
	public static final String STAR_STRING = "*";

	/**
	 * Gets the nb of files in package.
	 * 
	 * @param fileType
	 *            the file type
	 * @return the nb of files in package
	 */
	public static int getNbOfFilesInPackage(MediaFileType fileType) {
		switch (fileType) {

		case PICTURE:
			return FILES_COUNT_PICTURE_PACKAGE;
		case WIKIPEDIA_PAGE:
			return FILES_COUNT_WIKIPEDIA_PACKAGE;
		}
		return 0;
	}
}
