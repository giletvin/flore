package fr.flore_de_poche.download;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import fr.flore.R;
import fr.flore_de_poche.helper.BasicConstants;
import fr.flore_de_poche.helper.Constants;
import fr.flore_de_poche.helper.StringHelper;

/**
 * The Class DownloadConstants.
 */
public class DownloadConstants {

	/** The Constant DEFAULT_DOWNLOAD_SITE. */
	private static final String DEFAULT_DOWNLOAD_SITE = "http://ornidroid.free.fr/flore";

	/** The Constant DOWNLOAD_SITE_PROPERTY_KEY. */
	private static final String DOWNLOAD_SITE_PROPERTY_KEY = "ornidroid_download_site";

	/** The Constant ORNIDROID_WEB_SITE_ROOT. */
	private static String ORNIDROID_WEB_SITE_ROOT;

	/**
	 * Gets the ornidroid web site.
	 * 
	 * @return the ornidroid web site
	 */
	public static String getOrnidroidWebSite() {
		loadWebSiteRoot();
		return ORNIDROID_WEB_SITE_ROOT;
	}

	/**
	 * Gets the ornidroid web site wikipedia.
	 * 
	 * @return the ornidroid web site wikipedia
	 */
	public static String getOrnidroidWebSiteWikipedia() {
		loadWebSiteRoot();
		return ORNIDROID_WEB_SITE_ROOT + File.separator
				+ BasicConstants.WIKIPEDIA_DIRECTORY;
	}

	/**
	 * Gets the ornidroid web site images.
	 * 
	 * @return the ornidroid web site images
	 */
	public static String getOrnidroidWebSiteImages() {
		loadWebSiteRoot();
		return ORNIDROID_WEB_SITE_ROOT + File.separator
				+ BasicConstants.IMAGES_DIRECTORY;
	}

	/**
	 * Load web site root.
	 */
	private static void loadWebSiteRoot() {
		if (StringHelper.isBlank(ORNIDROID_WEB_SITE_ROOT)) {
			try {
				final InputStream rawResource = Constants.getCONTEXT()
						.getResources().openRawResource(R.raw.ornidroid);
				final Properties properties = new Properties();
				properties.load(rawResource);
				ORNIDROID_WEB_SITE_ROOT = properties.getProperty(
						DOWNLOAD_SITE_PROPERTY_KEY, DEFAULT_DOWNLOAD_SITE);
			} catch (final Exception e) {

			} finally {
				if (StringHelper.isBlank(ORNIDROID_WEB_SITE_ROOT)) {
					ORNIDROID_WEB_SITE_ROOT = DEFAULT_DOWNLOAD_SITE;
				}
			}
		}
	}
}
