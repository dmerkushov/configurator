/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dmerkushov.configurator.configadapter;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.json.JsonObject;
import ru.dmerkushov.prefconf.PrefConf;

/**
 *
 * @author dmerkushov
 */
public class PreferencesConfigAdapter implements ConfigAdapter {

	@Override
	public String loadParamValue (JsonObject param) {

		String paramName = param.getString ("paramName", "");

		if (paramName.equals ("")) {
			throw new RuntimeException ("paramName must not be empty: " + param.toString ());
		}
		String paramDefault = param.getString ("defaultValue", "");

		Preferences prefs = getPreferencesNode (param);

		String value = prefs.get (paramName, paramDefault);

		return value;
	}

	@Override
	public void saveParamValue (JsonObject param, String paramValue) {
		String paramName = param.getString ("paramName", "");

		if (paramName.equals ("")) {
			throw new RuntimeException ("paramName must not be empty: " + param.toString ());
		}

		Preferences prefs = getPreferencesNode (param);

		prefs.put (paramName, paramValue);
		try {
			prefs.sync ();
		} catch (BackingStoreException ex) {
			throw new RuntimeException ("Could not flush prefs for param: " + param.toString () + ", value: " + paramValue, ex);
		}
	}

	private Preferences getPreferencesNode (JsonObject param) {
		boolean prefconf = param.getBoolean ("prefconf", false);

		String sysuser = param.getString ("sysuser", "");

		String packagePath = param.getString ("packagePath", "").replaceAll ("\\.", "/");
		String envname = param.getString ("prefconfEnvironment", "default");

		Preferences prefs;

		if (prefconf) {
			if (sysuser.equals ("user")) {
				prefs = PrefConf.getInstance ().getUserConfigurationForEnvironment (packagePath, envname);
			} else if (sysuser.equals ("system")) {
				prefs = PrefConf.getInstance ().getSystemConfigurationForEnvironment (packagePath, envname);
			} else {
				throw new RuntimeException ("sysuser should be either \"user\" or \"system\": " + param.toString ());
			}
		} else {
			if (sysuser.equals ("user")) {
				prefs = Preferences.userRoot ().node (packagePath);
			} else if (sysuser.equals ("system")) {
				prefs = Preferences.systemRoot ().node (packagePath);
			} else {
				throw new RuntimeException ("sysuser should be either \"user\" or \"system\": " + param.toString ());
			}
		}

		return prefs;
	}

}
