/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dmerkushov.configurator.param.string;

import java.util.Objects;
import java.util.UUID;
import ru.dmerkushov.configurator.param.JParamEditor;
import ru.dmerkushov.configurator.param.ParamType;

/**
 *
 * @author dmerkushov
 */
public class UuidParamType implements ParamType {

	////////////////////////////////////////////////////////////////////////////
	// StringParamType is a singleton class
	////////////////////////////////////////////////////////////////////////////
	private static UuidParamType _instance;

	/**
	 * Get the single instance of StringParamType
	 *
	 * @return The same instance of StringParamType every time the method is
	 * called
	 */
	public static synchronized UuidParamType getInstance () {
		if (_instance == null) {
			_instance = new UuidParamType ();
		}
		return _instance;
	}

	private UuidParamType () {
	}
	////////////////////////////////////////////////////////////////////////////

	@Override
	public boolean checkValue (String value) {
		Objects.requireNonNull (value, "value");

		try {
			UUID.fromString (value);
		} catch (IllegalArgumentException ex) {
			return false;
		}

		return true;
	}

	@Override
	public Class<? extends JParamEditor> getParamEditorClass () {
		return StringParamEditor.class;
	}

}
