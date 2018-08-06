/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dmerkushov.configurator.param.bool;

import ru.dmerkushov.configurator.param.JParamEditor;
import ru.dmerkushov.configurator.param.ParamType;

/**
 *
 * @author dmerkushov
 */
public class BooleanParamType implements ParamType {

	////////////////////////////////////////////////////////////////////////////
	// JulLoggingLevelParamType is a singleton class
	////////////////////////////////////////////////////////////////////////////
	private static BooleanParamType _instance;

	/**
	 * Get the single instance of JulLoggingLevelParamType
	 *
	 * @return The same instance of JulLoggingLevelParamType every time the
	 * method is called
	 */
	public static synchronized BooleanParamType getInstance () {
		if (_instance == null) {
			_instance = new BooleanParamType ();
		}
		return _instance;
	}

	private BooleanParamType () {
	}
	////////////////////////////////////////////////////////////////////////////

	@Override
	public Class<? extends JParamEditor> getParamEditorClass () {
		return BooleanParamEditor.class;
	}

	@Override
	public boolean checkValue (String value) {
		return (value != null && (value.equals ("true") || value.equals ("false")));
	}

}
