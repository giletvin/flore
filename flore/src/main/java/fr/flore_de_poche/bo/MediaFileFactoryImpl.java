package fr.flore_de_poche.bo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import fr.flore_de_poche.helper.BasicConstants;
import fr.flore_de_poche.helper.StringHelper;

/**
 * The Class OrnidroidFileFactoryImpl.
 */
public class MediaFileFactoryImpl {

	/** The Constant DEFAULT_VALUE. */
	private static final String DEFAULT_VALUE = "";

	/** The factory. */
	private static MediaFileFactoryImpl factory;

	/**
	 * Gets the OrnidroidFileFactoryImpl factory.
	 * 
	 * @return the OrnidroidFileFactoryImpl factory
	 */
	public static MediaFileFactoryImpl getFactory() {
		if (factory == null) {
			factory = new MediaFileFactoryImpl();
		}
		return factory;
	}

	/**
	 * Instantiates a new ornidroid file factory impl.
	 */
	private MediaFileFactoryImpl() {

	}

	/**
	 * Creates the ornidroid file.
	 * 
	 * @param path
	 *            the path
	 * @param fileType
	 *            the file type
	 * @param lang
	 *            the lang
	 * @return the ornidroid file
	 * @throws FileNotFoundException
	 *             if the file has no properties file: this reveals a bad
	 *             installation or a pb in the downloading of the files
	 */
	public MediaFile createOrnidroidFile(final String path,
			final MediaFileType fileType, final String lang)
			throws FileNotFoundException {
		MediaFile file = null;
		switch (fileType) {

		case PICTURE:
			file = new PictureFile();
			break;
		case WIKIPEDIA_PAGE:
			file = new MediaFile();
			break;
		}
		file.setPath(path);
		file.setType(fileType);

		file.setProperties(handleProperties(file, lang));

		return file;
	}

	/**
	 * Handle properties of the newly created File.
	 * 
	 * @param file
	 *            the file
	 * @param lang
	 *            the lang
	 * @return the map containing the keys and values of the properties of the
	 *         file
	 * @throws FileNotFoundException
	 *             if the file has no properties file
	 */
	private Map<String, String> handleProperties(final MediaFile file,
			final String lang) throws FileNotFoundException {
		Map<String, String> properties = new HashMap<String, String>();
		switch (file.getType()) {

		case PICTURE:
			properties = initImageProperties(file, lang);
			break;
		case WIKIPEDIA_PAGE:
			// do nothing
			break;
		}
		return properties;
	}

	/**
	 * Inits the image properties.
	 * 
	 * @param file
	 *            the file
	 * @param lang
	 *            the lang
	 * @return the map, never null but can be empty
	 * @throws FileNotFoundException
	 *             if the file has no properties file
	 */
	private Map<String, String> initImageProperties(final MediaFile file,
			final String lang) throws FileNotFoundException {
		final Map<String, String> ornidroidFileProperties = new HashMap<String, String>();
		final Properties properties = loadPropertiesFile(file);
		if (properties != null) {
			final String description = StringHelper.defaultStringIfEmpty(properties
					.getProperty(PictureFile.IMAGE_DESCRIPTION_PROPERTY
							+ MediaFile.LANGUAGE_SEPARATOR + lang,
							DEFAULT_VALUE),
					getDefaultCommentValueForImage(file));
			ornidroidFileProperties.put(PictureFile.IMAGE_DESCRIPTION_PROPERTY,
					description);
			final String source = properties.getProperty(
					PictureFile.IMAGE_SOURCE_PROPERTY, DEFAULT_VALUE);
			ornidroidFileProperties.put(PictureFile.IMAGE_SOURCE_PROPERTY,
					source);
			final String author = properties.getProperty(
					PictureFile.IMAGE_AUTHOR_PROPERTY, DEFAULT_VALUE);
			ornidroidFileProperties.put(PictureFile.IMAGE_AUTHOR_PROPERTY,
					author);
			final String licence = properties.getProperty(
					PictureFile.IMAGE_LICENCE_PROPERTY, DEFAULT_VALUE);
			ornidroidFileProperties.put(PictureFile.IMAGE_LICENCE_PROPERTY,
					licence);
		}
		return ornidroidFileProperties;
	}

	private String getDefaultCommentValueForImage(MediaFile file) {
		String filename = file.getPath().substring(
				file.getPath().lastIndexOf(BasicConstants.SLASH_STRING));
		filename = filename
				.replace(BasicConstants.SLASH_STRING,
						BasicConstants.EMPTY_STRING)
				.replace("1HR_", BasicConstants.EMPTY_STRING)
				.replace("HR_", BasicConstants.EMPTY_STRING)
				.replace(BasicConstants.UNDERSCORE_STRING,
						BasicConstants.BLANK_STRING);

		return filename;
	}

	/**
	 * Load properties file.
	 * 
	 * @param file
	 *            the file
	 * @return the properties
	 * @throws FileNotFoundException
	 *             if the file has no properties file
	 */
	private Properties loadPropertiesFile(final MediaFile file)
			throws FileNotFoundException {
		Properties properties = null;
		final File propertiesFile = new File(file.getPath()
				+ MediaFile.PROPERTIES_SUFFIX);
		if (propertiesFile.exists()) {
			try {
				final FileInputStream fis = new FileInputStream(propertiesFile);
				properties = new Properties();
				properties.load(fis);
			} catch (final FileNotFoundException e) {
				// should not occur
				throw new RuntimeException(e);
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			throw new FileNotFoundException("properties file not found "
					+ file.getPath() + MediaFile.PROPERTIES_SUFFIX);
		}
		return properties;
	}

}
