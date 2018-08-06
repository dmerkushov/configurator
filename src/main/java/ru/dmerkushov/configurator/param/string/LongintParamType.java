/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dmerkushov.configurator.param.string;

import java.util.Objects;
import ru.dmerkushov.configurator.param.JParamEditor;
import ru.dmerkushov.configurator.param.ParamType;

/**
 *
 * @author dmerkushov
 */
public class LongintParamType implements ParamType {

	////////////////////////////////////////////////////////////////////////////
	// StringParamType is a singleton class
	////////////////////////////////////////////////////////////////////////////
	private static LongintParamType _instance;

	/**
	 * Get the single instance of StringParamType
	 *
	 * @return The same instance of StringParamType every time the method is
	 * called
	 */
	public static synchronized LongintParamType getInstance () {
		if (_instance == null) {
			_instance = new LongintParamType ();
		}
		return _instance;
	}

	private LongintParamType () {
	}
	////////////////////////////////////////////////////////////////////////////

	@Override
	public boolean checkValue (String value) {
		Objects.requireNonNull (value, "value");

		try {
			Long.parseLong (value);
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}

	@Override
	public Class<? extends JParamEditor> getParamEditorClass () {
		return StringParamEditor.class;
	}

}
