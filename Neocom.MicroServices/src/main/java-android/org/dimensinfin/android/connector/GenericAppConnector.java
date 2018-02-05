//	PROJECT:        Core.Android (C.A)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API16.
//	DESCRIPTION:		Library to define core interfaces and code for standard development of native Android
//									applications. Will provide the contants and Decorators to isolate implementations
//									and generate the request to implement the correct methods.
package org.dimensinfin.android.connector;

import java.io.File;
import java.util.logging.Logger;

import org.joda.time.Instant;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.preference.PreferenceManager;

// - CLASS IMPLEMENTATION ...................................................................................
public class GenericAppConnector extends AndroidAppConnector implements IGenericAppConnector {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger								logger	= Logger.getLogger("GenericAppConnector");

	// - F I E L D - S E C T I O N ............................................................................
	private final IGenericAppConnector	_connector;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public GenericAppConnector(final IGenericAppConnector application) {
		super(application);
		this._connector = application;
	}

	// - M E T H O D - S E C T I O N ..........................................................................
//	/**
//	 * Checks that the current parameter timestamp is still on the frame of the window.
//	 * 
//	 * @param timestamp
//	 *          the current and last timestamp of the object.
//	 * @param window
//	 *          time span window in milliseconds.
//	 */
//	public boolean checkExpiration(final long timestamp, final long window) {
//		// logger.info("-- Checking expiration for " + timestamp + ". Window " + window);
//		if (0 == timestamp) return true;
//		final long now = Instant.now().getMillis();
//		final long endWindow = timestamp + window;
//		if (now < endWindow)
//			return false;
//		else
//			return true;
//	}
//
//	public boolean checkNetworkAccess() {
//		final ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//		final NetworkInfo netInfo = cm.getActiveNetworkInfo();
//		if ((netInfo != null) && netInfo.isConnectedOrConnecting()) return true;
//		return false;
//	}

//	/**
//	 * Return the file that points to the application folder on the external (SDCARD) storage.
//	 */
//	public File getAppDirectory(final int appname) {
//		return new File(Environment.getExternalStorageDirectory(), getResourceString(appname));
//	}
//
//	public boolean getBooleanPreference(final String preferenceName, final boolean defaultValue) {
//		// Read the flag values from the preferences.
//		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//		boolean pref = sharedPrefs.getBoolean(preferenceName, defaultValue);
//		return pref;
//	}

//	public String getResourceString(final int reference) {
//		//		logger.info(">< [GenericAppConnector.getResourceString]> Accessing resource: " + reference);
//		return getResources().getString(reference);
//	}

//	/**
//	 * This method checks if the application has access to the external disk (SDCARD) and if that access
//	 * included the writing operations.<br>
//	 * This method should be called before any expected access to the filesystem by the minor number of classes
//	 * because it is a method strictly related to the Android OS. The execution may change the state of some
//	 * external variables but taking on account that this state may change dynamically I would prefer to call
//	 * repeatedly the method than storing the initial call results.
//	 * 
//	 * @return if the FS is writable. This also implies that the SDCARD is available.
//	 */
//	public boolean sdcardAvailable() {
//		boolean mExternalStorageAvailable = false;
//		boolean mExternalStorageWriteable = false;
//		final String state = Environment.getExternalStorageState();
//
//		if (Environment.MEDIA_MOUNTED.equals(state))
//			// We can read and write the media
//			mExternalStorageAvailable = mExternalStorageWriteable = true;
//		else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
//			// We can only read the media
//			mExternalStorageAvailable = true;
//			mExternalStorageWriteable = false;
//		} else
//			// Something else is wrong. It may be one of many other states, but all we need
//			// to know is we can neither read nor write
//			mExternalStorageAvailable = mExternalStorageWriteable = false;
//		return mExternalStorageWriteable;
//	}
}

// - UNUSED CODE ............................................................................................
