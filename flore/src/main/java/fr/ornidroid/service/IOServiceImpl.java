package fr.ornidroid.service;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.StatFs;
import android.util.Log;
import fr.ornidroid.bo.Subject;
import fr.ornidroid.bo.MediaFile;
import fr.ornidroid.bo.MediaFileFactoryImpl;
import fr.ornidroid.bo.MediaFileType;
import fr.ornidroid.bo.PictureFile;
import fr.ornidroid.download.DefaultDownloadable;
import fr.ornidroid.download.DownloadConstants;
import fr.ornidroid.download.DownloadHelperImpl;
import fr.ornidroid.download.DownloadHelperInterface;
import fr.ornidroid.helper.BasicConstants;
import fr.ornidroid.helper.Constants;
import fr.ornidroid.helper.FileHelper;
import fr.ornidroid.helper.I18nHelper;
import fr.ornidroid.helper.ApplicationError;
import fr.ornidroid.helper.ApplicationException;
import fr.ornidroid.helper.StringHelper;
import fr.ornidroid.helper.SupportedLanguage;

/**
 * The Class OrnidroidIOServiceImpl.
 */
public class IOServiceImpl implements IIOService {

	/** The Constant MIN_SIZE_REQUIRED_TO_DOWNLOAD_ZIP_PACKAGES. */
	// TODO : a mettre à jour ulterieurement
	private static final int MIN_SPACE_TO_DOWNLOAD_IMAGE_PACKAGE = 120;

	/** The Constant MIN_SPACE_TO_DOWNLOAD_WIKIPEDIA_PACKAGE. */
	private static final int MIN_SPACE_TO_DOWNLOAD_WIKIPEDIA_PACKAGE = 40;

	/**
	 * The Class OrnidroidFileFilter.
	 */
	private class OrnidroidFileFilter implements FileFilter {

		/** The file type. */
		private final MediaFileType fileType;

		/**
		 * Instantiates a new ornidroid file filter.
		 * 
		 * @param fileType
		 *            the file type
		 */
		OrnidroidFileFilter(final MediaFileType fileType) {
			this.fileType = fileType;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.io.FileFilter#accept(java.io.File)
		 */
		public boolean accept(final File pathname) {

			if (pathname.getAbsolutePath().endsWith(
					MediaFileType.getExtension(this.fileType))) {
				return true;
			}
			return false;
		}
	}

	/** The download helper. */
	private final DownloadHelperInterface downloadHelper;

	/**
	 * Instantiates a new ornidroid io service impl.
	 */
	public IOServiceImpl() {

		this.downloadHelper = new DownloadHelperImpl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.ornidroid.service.IOrnidroidIOService#addCustomMediaFile(java.lang
	 * .String, fr.ornidroid.bo.OrnidroidFileType, java.lang.String,
	 * java.io.File, java.lang.String)
	 */
	public void addCustomMediaFile(final String birdDirectory,
			final MediaFileType fileType, final String selectedFileName,
			final File selectedFile, final String comment)
			throws ApplicationException {
		String destinationDirectory;
		switch (fileType) {

		case PICTURE:
			destinationDirectory = Constants.getOrnidroidHomeImages()
					+ File.separator + birdDirectory;
			break;
		default:
			destinationDirectory = BasicConstants.EMPTY_STRING;
		}
		final String destFileName = BasicConstants.CUSTOM_MEDIA_FILE_PREFIX
				+ selectedFileName;
		final File destFile = new File(destinationDirectory + File.separator
				+ destFileName);
		final File propertiesFile = new File(destFile.getAbsolutePath()
				+ MediaFile.PROPERTIES_SUFFIX);
		doAddCustomMediaFiles(fileType, selectedFile, destFile, propertiesFile,
				comment);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.ornidroid.service.IOrnidroidIOService#checkAndCreateDirectory
	 * (java.io.File)
	 */
	public void checkAndCreateDirectory(final File fileDirectory)
			throws ApplicationException {
		try {
			if (!fileDirectory.exists()) {
				FileHelper.forceMkdir(fileDirectory);
			}
			// creates the .nomedia file in the root directory
			final File noMediaFile = new File(fileDirectory,
					BasicConstants.NO_MEDIA_FILENAME);
			FileHelper.createEmptyFile(noMediaFile);
		} catch (final IOException e) {

			throw new ApplicationException(
					ApplicationError.ORNIDROID_HOME_NOT_FOUND, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.ornidroid.service.IOrnidroidIOService#checkOrnidroidHome(
	 * java.lang.String)
	 */
	public void checkOrnidroidHome(final String ornidroidHome)
			throws ApplicationException {
		final File fileOrnidroidHome = new File(ornidroidHome);
		if (!fileOrnidroidHome.exists()) {
			checkAndCreateDirectory(fileOrnidroidHome);
		}
		checkAndCreateDirectory(new File(ornidroidHome + File.separator
				+ BasicConstants.IMAGES_DIRECTORY));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.ornidroid.service.IOrnidroidIOService#downloadMediaFiles(
	 * java.lang.String, fr.ornidroid.bo.Bird,
	 * fr.ornidroid.bo.OrnidroidFileType)
	 */
	public void downloadMediaFiles(final String mediaHomeDirectory,
			final Subject bird, final MediaFileType fileType)
			throws ApplicationException {
		switch (fileType) {
		case PICTURE:
			bird.setPictures(lookForOrnidroidFiles(mediaHomeDirectory,
					bird.getBirdDirectoryName(), MediaFileType.PICTURE,
					true));
			break;

		case WIKIPEDIA_PAGE:
			bird.setWikipediaPage(downloadWikipediaPage(bird));

			break;
		}

	}

	/**
	 * Gets the local wikipedia page or download it.
	 * 
	 * @param bird
	 *            the bird
	 * @return the local wikipedia page or download it
	 */
	private MediaFile downloadWikipediaPage(Subject bird) {
		// cherche s'il existe une page wiki locale

		try {
			downloadHelper.downloadFile(
					downloadHelper.getBaseUrl(I18nHelper.getLang().getCode(),
							MediaFileType.WIKIPEDIA_PAGE),
					bird.getScientificName().replace(
							BasicConstants.BLANK_STRING,
							BasicConstants.UNDERSCORE_STRING),
					Constants.getOrnidroidHomeWikipedia() + File.separator
							+ I18nHelper.getLang().getCode());
			return getLocalWikipediaPage(bird);
		} catch (ApplicationException e) {
			return null;
		}
	}

	/**
	 * Gets the local wikipedia page.
	 * 
	 * @param bird
	 *            the bird
	 * @return the local wikipedia page
	 */
	private MediaFile getLocalWikipediaPage(Subject bird) {
		// cherche s'il existe une page wiki locale
		try {
			File wikipediaFile = new File(getWikipediaPage(bird));
			if (wikipediaFile.exists()) {
				MediaFile wikipediaOrnidroidFile;
				try {
					wikipediaOrnidroidFile = MediaFileFactoryImpl
							.getFactory().createOrnidroidFile(
									wikipediaFile.getAbsolutePath(),
									MediaFileType.WIKIPEDIA_PAGE,
									I18nHelper.getLang().getCode());
					return wikipediaOrnidroidFile;
				} catch (FileNotFoundException e) {
					return null;
				}
			} else {
				return null;
			}
		} catch (NullPointerException e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.ornidroid.service.IOrnidroidIOService#areThereUpdates(java.lang.String
	 * , fr.ornidroid.bo.Bird, fr.ornidroid.bo.OrnidroidFileType)
	 */
	public List<String> filesToUpdate(final String mediaHomeDirectory,
			final Subject bird, final MediaFileType fileType)
			throws ApplicationException {
		final List<String> filesToUpdate = new ArrayList<String>();

		final List<String> localFilesList = loadContentFile(true,
				mediaHomeDirectory, bird, fileType);
		final List<String> remoteFilesList = loadContentFile(false,
				mediaHomeDirectory, bird, fileType);

		for (final String remoteFile : remoteFilesList) {
			if (!localFilesList.contains(remoteFile)) {
				filesToUpdate.add(remoteFile);
			}
		}
		return filesToUpdate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.ornidroid.service.IOrnidroidIOService#loadMediaFiles(java
	 * .io.File, fr.ornidroid.bo.Bird)
	 */
	public void loadMediaFiles(final String fileDirectory, final Subject bird,
			final MediaFileType fileType) throws ApplicationException {
		switch (fileType) {
		case PICTURE:
			bird.setPictures(lookForOrnidroidFiles(fileDirectory,
					bird.getBirdDirectoryName(), MediaFileType.PICTURE,
					false));
			break;

		case WIKIPEDIA_PAGE:
			bird.setWikipediaPage(getLocalWikipediaPage(bird));
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.ornidroid.service.IOrnidroidIOService#removeCustomMediaFile(fr.ornidroid
	 * .bo.AbstractOrnidroidFile)
	 */
	public void removeCustomMediaFile(final MediaFile ornidroidFile)
			throws ApplicationException {
		if (ornidroidFile.isCustomMediaFile()) {
			final File mediaFile = new File(ornidroidFile.getPath());
			final File mediaPropertiesFile = new File(ornidroidFile.getPath()
					+ MediaFile.PROPERTIES_SUFFIX);
			try {
				FileHelper.forceDelete(mediaFile);
				FileHelper.forceDelete(mediaPropertiesFile);
			} catch (final IOException e) {
				Log.e(BasicConstants.LOG_TAG, e.getMessage(), e);
				throw new ApplicationException(
						ApplicationError.ADD_CUSTOM_MEDIA_ERROR, e);
			}
		}
	}

	/**
	 * Do add custom media files.
	 * 
	 * @param fileType
	 *            the file type
	 * @param selectedFile
	 *            the selected file
	 * @param destFile
	 *            the dest file
	 * @param propertiesFile
	 *            the properties file
	 * @param comment
	 *            the comment
	 * @throws ApplicationException
	 *             the ornidroid exception
	 */
	protected void doAddCustomMediaFiles(final MediaFileType fileType,
			final File selectedFile, final File destFile,
			final File propertiesFile, final String comment)
			throws ApplicationException {
		try {
			FileHelper.doCopyFile(selectedFile, destFile);

			FileHelper.writeStringToFile(propertiesFile,
					getCustomPropertiesString(fileType, comment), null, false);
		} catch (final IOException e) {
			try {
				FileHelper.forceDelete(destFile);
				FileHelper.forceDelete(propertiesFile);
			} catch (final IOException e1) {
			}

			throw new ApplicationException(ApplicationError.ADD_CUSTOM_MEDIA_ERROR,
					e);

		}

	}

	/**
	 * Gets the custom properties string.
	 * 
	 * @param fileType
	 *            the file type
	 * @param comment
	 *            the comment
	 * @return the custom properties string
	 */
	private String getCustomPropertiesString(final MediaFileType fileType,
			final String comment) {
		String data = null;
		switch (fileType) {

		case PICTURE:
			data = PictureFile.IMAGE_DESCRIPTION_PROPERTY
					+ MediaFile.LANGUAGE_SEPARATOR
					+ SupportedLanguage.FRENCH.getCode()
					+ BasicConstants.EQUALS_STRING + comment
					+ BasicConstants.CARRIAGE_RETURN
					+ PictureFile.IMAGE_DESCRIPTION_PROPERTY
					+ MediaFile.LANGUAGE_SEPARATOR
					+ SupportedLanguage.ENGLISH.getCode()
					+ BasicConstants.EQUALS_STRING + comment;
			break;
		case WIKIPEDIA_PAGE:
			data = BasicConstants.EMPTY_STRING;
			break;
		}
		return data;
	}

	/**
	 * Load contents.properties file.
	 * 
	 * @param localContent
	 *            : if true parse the local file, otherwise download the remote
	 *            file from web site
	 * @param mediaHomeDirectory
	 *            the media home directory
	 * @param bird
	 *            the bird
	 * @param fileType
	 *            the file type
	 * @return the list of files parsed from contents.properties file
	 */
	private List<String> loadContentFile(final boolean localContent,
			final String mediaHomeDirectory, final Subject bird,
			final MediaFileType fileType) {
		String[] filesFromContentFile = null;
		if ((!localContent)) {
			// from web site
			final String birdDirectoryUrl = this.downloadHelper.getBaseUrl(
					bird.getBirdDirectoryName(), fileType);
			final String destinationPath = mediaHomeDirectory + File.separator
					+ bird.getBirdDirectoryName();
			try {
				filesFromContentFile = this.downloadHelper.readContentFile(
						birdDirectoryUrl, destinationPath);
			} catch (final ApplicationException e) {
				// no problemo
			}
		} else {
			// local
			try {
				final File localContentFile = new File(mediaHomeDirectory
						+ File.separator + bird.getBirdDirectoryName()
						+ File.separator + BasicConstants.CONTENTS_PROPERTIES);
				filesFromContentFile = FileHelper
						.parseContentFile(localContentFile);

			} catch (final IOException e) {
				// there is no contents.properties files locally
			}
		}
		return filesFromContentFile == null ? new ArrayList<String>() : Arrays
				.asList(filesFromContentFile);
	}

	/**
	 * Look for OrnidroidFiles.
	 * 
	 * @param ornidroidMediaHome
	 *            root path of the media (picture) : ornidroidHome + images
	 * @param directoryName
	 *            the directory name of the bird
	 * @param fileType
	 *            the file type
	 * @param downloadFromInternet
	 *            allow download from internet
	 * @return the list containing instances of OrnidroidFile, never null. If
	 *         there are no files, the list is empty. If one of the file misses
	 *         its properties file, the entire bird media directory is deleted
	 *         and the returned list is empty : the user will be given the
	 *         choice to try a download from the web site
	 * @throws ApplicationException
	 *             the ornidroid exception
	 */
	private List<MediaFile> lookForOrnidroidFiles(
			final String ornidroidMediaHome, final String directoryName,
			final MediaFileType fileType, final boolean downloadFromInternet)
			throws ApplicationException {
		final List<MediaFile> files = new ArrayList<MediaFile>();
		if (StringHelper.isNotBlank(directoryName)) {
			final File filesDirectory = new File(ornidroidMediaHome
					+ File.separator + directoryName);
			if (!filesDirectory.exists()) {
				try {
					FileHelper.forceMkdir(filesDirectory);
				} catch (final IOException e) {
					Log.e(BasicConstants.LOG_TAG, e.getMessage(), e);
					throw new ApplicationException(
							ApplicationError.ORNIDROID_HOME_NOT_FOUND, e);
				}
			}
			if (filesDirectory.isDirectory()) {
				List<File> filesList = Arrays.asList(filesDirectory
						.listFiles(new OrnidroidFileFilter(fileType)));
				// Try to download from internet
				if (downloadFromInternet) {
					filesList = this.downloadHelper.downloadFiles(
							ornidroidMediaHome, directoryName, fileType);
				}
				try {
					for (final File file : filesList) {
						final MediaFile ornidroidFile = MediaFileFactoryImpl
								.getFactory().createOrnidroidFile(
										file.getAbsolutePath(), fileType,
										I18nHelper.getLang().getCode());
						files.add(ornidroidFile);
					}
				} catch (final FileNotFoundException e) {
					// one of the media files doesnt have its properties.
					// this reveals a pb in the downloading : maybe the picture
					// or the sound file is corrupted
					// we remove the entire bird media directory and don't load
					// anything.
					files.clear();
					// Log.w(Constants.LOG_TAG, e.getMessage());
					// Log.w(Constants.LOG_TAG,
					// "The directory " + filesDirectory.getPath()
					// + " is going to be deleted");
					try {
						FileHelper.forceDelete(filesDirectory);
					} catch (final IOException e1) {
						// Log.e(Constants.LOG_TAG,
						// "Unable to delete the directory "
						// + filesDirectory.getPath());
					}

				}
			}
		}
		return files;
	}

	/**
	 * Gets the wikipedia page local path.
	 * 
	 * @param currentBird
	 *            the current bird
	 * @return the local path of the wikipedia page
	 */
	private String getWikipediaPage(Subject currentBird) {
		return Constants.getOrnidroidHomeWikipedia()
				+ File.separator
				+ I18nHelper.getLang().getCode()
				+ File.separator
				+ currentBird.getScientificName().replace(
						BasicConstants.BLANK_STRING,
						BasicConstants.UNDERSCORE_STRING);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.ornidroid.service.IOrnidroidIOService#downloadZipPackage(java.lang
	 * .String, java.lang.String)
	 */
	public void downloadZipPackage(String zipname, String mediaHomeDirectory)
			throws ApplicationException {
		ApplicationException exception = null;

		deleteTempFiles(mediaHomeDirectory, zipname);

		File zipPackageFile = downloadHelper.downloadFile(
				DownloadConstants.getOrnidroidWebSite(), zipname,
				mediaHomeDirectory);
		if (zipPackageFile == null) {
			RuntimeException runtimeException = new RuntimeException(
					"no media file downloaded with parameters : " + zipname
							+ BasicConstants.BLANK_STRING + mediaHomeDirectory);
			Log.e(BasicConstants.LOG_TAG, runtimeException.getMessage(),
					runtimeException);
			throw new ApplicationException(
					ApplicationError.ORNIDROID_DOWNLOAD_ERROR, runtimeException);
		}
		boolean success = FileHelper.unzipFile(zipPackageFile.getName(),
				mediaHomeDirectory);

		try {
			FileHelper.forceDelete(zipPackageFile);
		} catch (IOException e) {
			Log.e(BasicConstants.LOG_TAG, e.getMessage(), e);
			exception = new ApplicationException(ApplicationError.UNZIP_PACKAGE, e);
		}

		if (!success) {
			exception = new ApplicationException(ApplicationError.UNZIP_PACKAGE,
					null);
		}
		if (exception != null) {
			throw exception;
		}

	}

	/**
	 * Delete temp files from previous download tries.
	 * 
	 * @param mediaHomeDirectory
	 *            the media home directory
	 * @param zipname
	 *            the zipname
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void deleteTempFiles(String mediaHomeDirectory, String zipname) {
		File tempZipfile = new File(mediaHomeDirectory + File.separator
				+ zipname);
		if (tempZipfile.exists()) {
			try {
				FileHelper.forceDelete(tempZipfile);
			} catch (IOException e) {

			}
		}
		tempZipfile = new File(mediaHomeDirectory + File.separator + zipname
				+ DefaultDownloadable.SUFFIX_DOWNLOAD);
		if (tempZipfile.exists()) {
			try {
				FileHelper.forceDelete(tempZipfile);
			} catch (IOException e) {
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.ornidroid.service.IOrnidroidIOService#isEnoughFreeSpace()
	 * 
	 * Tip found :
	 * http://stackoverflow.com/questions/3394765/how-to-check-available
	 * -space-on-android-device-on-mini-sd-card
	 */
	public boolean isEnoughFreeSpace(MediaFileType fileType) {
		int requiredSpace = getRequiredSpaceToDownloadZip(fileType);
		File ornidroidHome = new File(Constants.getOrnidroidHome());
		StatFs stat = new StatFs(ornidroidHome.getPath());
		// TODO using deprecated method since api level is 11
		// non deprecated methods are : getBlockSizeLong and
		// getAvailableBlocksLong
		long bytesAvailable = (long) stat.getBlockSize()
				* (long) stat.getAvailableBlocks();
		float spaceAvailableInMb = bytesAvailable / (1024.f * 1024.f);
		return spaceAvailableInMb > requiredSpace;
	}

	/**
	 * Gets the required space to download zip.
	 * 
	 * @param fileType
	 *            the file type
	 * @return the required space to download zip
	 */
	private int getRequiredSpaceToDownloadZip(MediaFileType fileType) {
		int requiredSpace = 0;
		switch (fileType) {
		case WIKIPEDIA_PAGE:
			requiredSpace = MIN_SPACE_TO_DOWNLOAD_WIKIPEDIA_PACKAGE;
			break;

		case PICTURE:
			requiredSpace = MIN_SPACE_TO_DOWNLOAD_IMAGE_PACKAGE;
			break;
		}
		return requiredSpace;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.ornidroid.service.IOrnidroidIOService#getZipname(fr.ornidroid.bo.
	 * OrnidroidFileType)
	 */
	public String getZipname(MediaFileType fileType) {
		String zipname = null;
		switch (fileType) {

		case PICTURE:
			zipname = "images.zip";
			break;
		case WIKIPEDIA_PAGE:
			zipname = "wikipedia.zip";
			break;
		}
		return zipname;

	}

	/**
	 * Gets the zip download progress percent.
	 * 
	 * @param fileType
	 *            the file type
	 * @return the zip download progress percent
	 */
	public int getZipDownloadProgressPercent(MediaFileType fileType) {
		File downloadedFile = new File(Constants.getOrnidroidHome()
				+ File.separator + getZipname(fileType)
				+ DefaultDownloadable.SUFFIX_DOWNLOAD);
		if (downloadedFile.exists()) {
			int megaBytesDownloaded = FileHelper
					.getFileSizeInMb(downloadedFile);
			int result = ((megaBytesDownloaded * 200) / getRequiredSpaceToDownloadZip(fileType));
			return result;
		} else {
			downloadedFile = new File(Constants.getOrnidroidHome()
					+ File.separator + getZipname(fileType));
			if (downloadedFile.exists()) {
				return 100;
			} else {
				return 0;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.ornidroid.service.IOrnidroidIOService#getInstallProgressPercent(fr
	 * .ornidroid.bo.OrnidroidFileType)
	 */
	public int getInstallProgressPercent(MediaFileType fileType) {
		File mediaHome = new File(Constants.getOrnidroidHomeMedia(fileType));
		return FileHelper.getCountFiles(mediaHome);

	}
}
