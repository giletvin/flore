package fr.flore_de_poche.service;

import java.io.File;
import java.util.List;

import fr.flore_de_poche.bo.MediaFile;
import fr.flore_de_poche.bo.MediaFileType;
import fr.flore_de_poche.bo.Subject;
import fr.flore_de_poche.bo.ZipPackage;
import fr.flore_de_poche.helper.ApplicationException;

/**
 * The Interface IOrnidroidIOService.
 */
public interface IIOService {

	/**
	 * Adds the custom media file.
	 * 
	 * @param birdDirectory
	 *            the bird directory
	 * @param fileType
	 *            the file type
	 * @param selectedFileName
	 *            the selected file name
	 * @param selectedFile
	 *            the selected file
	 * @param comment
	 *            the comment
	 * @throws ApplicationException
	 *             the ornidroid exception
	 */
	public void addCustomMediaFile(String birdDirectory,
			MediaFileType fileType, String selectedFileName, File selectedFile,
			String comment) throws ApplicationException;

	/**
	 * Check and create directory if necessary.
	 * 
	 * @param fileDirectory
	 *            the file directory
	 * @throws ApplicationException
	 */
	public void checkAndCreateDirectory(File fileDirectory)
			throws ApplicationException;

	/**
	 * Download media files.
	 * 
	 * @param mediaHomeDirectory
	 *            the media home directory
	 * @param bird
	 *            the bird
	 * @param fileType
	 *            the file type
	 */
	public void downloadMediaFiles(String mediaHomeDirectory, Subject bird,
			MediaFileType fileType) throws ApplicationException;

	/**
	 * Load media files from the local directory.
	 * 
	 * @param mediaHomeDirectory
	 *            directory of images or sounds
	 * @param bird
	 *            the bird
	 * @param fileType
	 *            the file type
	 */
	public void loadMediaFiles(String mediaHomeDirectory, Subject bird,
			MediaFileType fileType) throws ApplicationException;

	/**
	 * Check ornidroid home. If the directory doesn't exist, try to create it.
	 * Check if the subdirectories images exist too. If not, try to create it.
	 * 
	 * @param ornidroidHome
	 *            the ornidroid home : the local path where ornidroid files
	 *            should be
	 * @throws ApplicationException
	 *             the ornidroid exception
	 */
	void checkOrnidroidHome(String ornidroidHome) throws ApplicationException;

	/**
	 * Checks if there are updates for the given bird and file type. This
	 * methods only checks for new files - it doesn't detect any file deletion.
	 * 
	 * @param mediaHomeDirectory
	 *            the media home directory
	 * @param bird
	 *            the bird
	 * @param fileType
	 *            the file type
	 * @return the list of files to download, never null
	 * @throws ApplicationException
	 *             the ornidroid exception
	 */
	List<String> filesToUpdate(String mediaHomeDirectory, Subject bird,
			MediaFileType fileType) throws ApplicationException;

	/**
	 * Removes the custom media file.
	 * 
	 * @param ornidroidFile
	 *            the file name
	 * @throws ApplicationException
	 *             the ornidroid exception
	 */
	void removeCustomMediaFile(MediaFile ornidroidFile)
			throws ApplicationException;

	/**
	 * Download zip package.
	 * 
	 * @param zipname
	 *            zipname
	 * @param mediaHomeDirectory
	 *            the media home directory
	 * @param fileType
	 *            the file type
	 * 
	 * @throws ApplicationException
	 *             the ornidroid exception
	 */
	public void downloadZipPackage(String zipname, String mediaHomeDirectory)
			throws ApplicationException;

	/**
	 * Checks if is enough free space.
	 * 
	 * @param fileType
	 *            the file type
	 * @return true, if is enough free space
	 */
	public boolean isEnoughFreeSpace(MediaFileType fileType);

	/**
	 * Gets the zipname according to the filetype
	 * 
	 * @param fileType
	 *            the file type
	 * @return the ZipPackage
	 */
	public ZipPackage getZipname(MediaFileType fileType);

	/**
	 * Gets the download progress in percent of the zip package downloading.
	 * 
	 * @param fileType
	 * @param folderSizeBeforeDownload
	 * @return the zip download progress percent
	 */
	public int getZipDownloadProgressPercent(MediaFileType fileType,
			int folderSizeBeforeDownload);

	/**
	 * Gets the install progress percent.
	 * 
	 * @param fileType
	 *            the file type
	 * @return the install progress percent
	 */
	public int getInstallProgressPercent(MediaFileType fileType);

}
