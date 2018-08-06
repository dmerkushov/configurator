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
public class IpParamType implements ParamType {

	////////////////////////////////////////////////////////////////////////////
	// IpParamType is a singleton class
	////////////////////////////////////////////////////////////////////////////
	private static IpParamType _instance;

	/**
	 * Get the single instance of IpParamType
	 *
	 * @return The same instance of IpParamType every time the method is called
	 */
	public static synchronized IpParamType getInstance () {
		if (_instance == null) {
			_instance = new IpParamType ();
		}
		return _instance;
	}

	private IpParamType () {
	}
	////////////////////////////////////////////////////////////////////////////

	@Override
	public boolean checkValue (String value) {
		Objects.requireNonNull (value, "value");

		return Ipv4ParamType.getInstance ().checkValue (value) || Ipv6ParamType.getInstance ().checkValue (value);
	}

	@Override
	public Class<? extends JParamEditor> getParamEditorClass () {
		return StringParamEditor.class;
	}

}
