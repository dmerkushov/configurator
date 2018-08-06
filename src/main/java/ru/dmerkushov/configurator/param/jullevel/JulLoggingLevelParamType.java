/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dmerkushov.configurator.param.jullevel;

import ru.dmerkushov.configurator.param.JParamEditor;
import ru.dmerkushov.configurator.param.ParamType;

/**
 *
 * @author dmerkushov
 */
public class JulLoggingLevelParamType implements ParamType {

	////////////////////////////////////////////////////////////////////////////
	// JulLoggingLevelParamType is a singleton class
	////////////////////////////////////////////////////////////////////////////
	private static JulLoggingLevelParamType _instance;

	/**
	 * Get the single instance of JulLoggingLevelParamType
	 *
	 * @return The same instance of JulLoggingLevelParamType every time the
	 * method is called
	 */
	public static synchronized JulLoggingLevelParamType getInstance () {
		if (_instance == null) {
			_instance = new JulLoggingLevelParamType ();
		}
		return _instance;
	}

	private JulLoggingLevelParamType () {
	}
	////////////////////////////////////////////////////////////////////////////

	@Override
	public Class<? extends JParamEditor> getParamEditorClass () {
		return JulLoggingLevelParamEditor.class;
	}

	@Override
	public boolean checkValue (String value) {
		try {
			PossibleValues.valueOf (value);
		} catch (IllegalArgumentException ex) {
			return false;
		}
		return true;
	}

	static enum PossibleValues {
		ALL,
		FINEST,
		FINER,
		FINE,
		CONFIG,
		INFO,
		WARNING,
		SEVERE,
		OFF;

		public final java.util.logging.Level julLevel;

		PossibleValues () {
			this.julLevel = java.util.logging.Level.parse (this.name ());
		}
	}

}
