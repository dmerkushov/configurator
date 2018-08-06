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
public class Ipv4ParamType implements ParamType {

	////////////////////////////////////////////////////////////////////////////
	// Ipv4ParamType is a singleton class
	////////////////////////////////////////////////////////////////////////////
	private static Ipv4ParamType _instance;

	/**
	 * Get the single instance of Ipv4ParamType
	 *
	 * @return The same instance of Ipv4ParamType every time the method is
	 * called
	 */
	public static synchronized Ipv4ParamType getInstance () {
		if (_instance == null) {
			_instance = new Ipv4ParamType ();
		}
		return _instance;
	}

	private Ipv4ParamType () {
	}
	////////////////////////////////////////////////////////////////////////////

	Pattern pattern = Pattern.compile ("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");

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
