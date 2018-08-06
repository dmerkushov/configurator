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
public class TcpUdpPortParamType implements ParamType {

	////////////////////////////////////////////////////////////////////////////
	// TcpUdpPortParamType is a singleton class
	////////////////////////////////////////////////////////////////////////////
	private static TcpUdpPortParamType _instance;

	/**
	 * Get the single instance of TcpUdpPortParamType
	 *
	 * @return The same instance of TcpUdpPortParamType every time the method is
	 * called
	 */
	public static synchronized TcpUdpPortParamType getInstance () {
		if (_instance == null) {
			_instance = new TcpUdpPortParamType ();
		}
		return _instance;
	}

	private TcpUdpPortParamType () {
	}
	////////////////////////////////////////////////////////////////////////////

	@Override
	public boolean checkValue (String value) {
		Objects.requireNonNull (value, "value");

		int val;
		try {
			val = Integer.parseInt (value);
		} catch (NumberFormatException ex) {
			return false;
		}
		return (0 <= val && val <= 65535);
	}

	@Override
	public Class<? extends JParamEditor> getParamEditorClass () {
		return StringParamEditor.class;
	}

}
