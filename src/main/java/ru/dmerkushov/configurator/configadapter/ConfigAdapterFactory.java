/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dmerkushov.configurator.configadapter;

import java.util.HashMap;
import java.util.Map;
import javax.json.JsonObject;

/**
 *
 * @author dmerkushov
 */
public class ConfigAdapterFactory {

	////////////////////////////////////////////////////////////////////////////
	// ConfigAdapterFactory is a singleton class
	////////////////////////////////////////////////////////////////////////////
	private static ConfigAdapterFactory _instance;

	/**
	 * Get the single instance of ConfigAdapterFactory
	 *
	 * @return The same instance of ConfigAdapterFactory every time the method
	 * is called
	 */
	public static synchronized ConfigAdapterFactory getInstance () {
		if (_instance == null) {
			_instance = new ConfigAdapterFactory ();
		}
		return _instance;
	}

	private ConfigAdapterFactory () {
	}
	////////////////////////////////////////////////////////////////////////////

	Map<String, ConfigAdapter> adapters = new HashMap<> ();

	public ConfigAdapter getAdapter (String configurationType) {
		synchronized (adapters) {
			if (adapters.containsKey (configurationType)) {
				return adapters.get (configurationType);
			}

			ConfigAdapter adapter;
			switch (configurationType) {
				case "Preferences":
					adapter = new PreferencesConfigAdapter ();
					break;
				default:
					throw new RuntimeException ("Unknown configuration type: " + configurationType);
			}
			adapters.put (configurationType, adapter);

			return adapter;
		}
	}

	public ConfigAdapter getAdapter (JsonObject param) {
		return getAdapter (param.getString ("configurationType", "Preferences"));
	}

}
