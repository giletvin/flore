package fr.flore_de_poche.service;

import android.app.Activity;

/**
 * A factory for creating OrnidroidService objects.
 */
public class ServiceFactory {

	/**
	 * Gets the service.
	 * 
	 * @param activity
	 *            the activity
	 * @return the service
	 */
	public static IService getService(Activity activity) {
		return ServiceImpl.getInstance(activity);
	}
}
