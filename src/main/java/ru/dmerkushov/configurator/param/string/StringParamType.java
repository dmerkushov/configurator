/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dmerkushov.configurator.param.string;

import java.util.Objects;
import java.util.regex.Pattern;
import ru.dmerkushov.configurator.param.JParamEditor;
import ru.dmerkushov.configurator.param.ParamType;

/**
 *
 * @author dmerkushov
 */
public class StringParamType implements ParamType {

	////////////////////////////////////////////////////////////////////////////
	// StringParamType is a singleton class
	////////////////////////////////////////////////////////////////////////////
	private static StringParamType _instance;

	/**
	 * Get the single instance of StringParamType
	 *
	 * @return The same instance of StringParamType every time the method is
	 * called
	 */
	public static synchronized StringParamType getInstance () {
		if (_instance == null) {
			_instance = new StringParamType ();
		}
		return _instance;
	}

	private StringParamType () {
	}
	////////////////////////////////////////////////////////////////////////////

	Pattern pattern = Pattern.compile (".*");

	@Override
	public boolean checkValue (String value) {
		Objects.requireNonNull (value, "value");

		return pattern.matcher (value).matches ();
	}

	@Override
	public Class<? extends JParamEditor> getParamEditorClass () {
		return StringParamEditor.class;
	}

}
