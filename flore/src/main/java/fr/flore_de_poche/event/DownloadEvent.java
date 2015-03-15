package fr.flore_de_poche.event;

import fr.flore_de_poche.bo.MediaFileType;

public class DownloadEvent extends GenericEvent {

	public final MediaFileType fileType;

	public DownloadEvent(EventType eventType, Exception pException,
			MediaFileType pFileType) {
		super(eventType, pException);
		fileType = pFileType;
	}
}
