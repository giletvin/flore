package fr.flore_de_poche.event;

import fr.flore_de_poche.bo.MediaFileType;

public class CheckForUpdateEvent extends GenericEvent {

	public final boolean updatesAvailable;
	public final boolean manualCheck;
	public final MediaFileType fileType;

	public CheckForUpdateEvent(Exception pException, boolean pUpdatesAvailable,
			boolean pManualCheck, MediaFileType pFileType) {
		super(EventType.CHECK_FOR_UPDATE, pException);
		updatesAvailable = pUpdatesAvailable;
		manualCheck = pManualCheck;
		fileType = pFileType;
	}
}
