/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dmerkushov.configurator.param.string;

import java.io.StringReader;
import java.util.Objects;
import javax.json.Json;
import javax.json.JsonReader;
import ru.dmerkushov.configurator.param.JParamEditor;
import ru.dmerkushov.configurator.param.ParamType;

/**
 *
 * @author dmerkushov
 */
public class JsonObjectParamType implements ParamType {

	////////////////////////////////////////////////////////////////////////////
	// StringParamType is a singleton class
	////////////////////////////////////////////////////////////////////////////
	private static JsonObjectParamType _instance;

	/**
	 * Get the single instance of StringParamType
	 *
	 * @return The same instance of StringParamType every time the method is
	 * called
	 */
	public static synchronized JsonObjectParamType getInstance () {
		if (_instance == null) {
			_instance = new JsonObjectParamType ();
		}
		return _instance;
	}

	private JsonObjectParamType () {
	}
	////////////////////////////////////////////////////////////////////////////

	@Override
	public boolean checkValue (String value) {
		Objects.requireNonNull (value, "value");

		JsonReader jr = Json.createReader (new StringReader (value));
		try {
			jr.readObject ();
		} catch (RuntimeException ex) {
			return false;
		}

		return true;
	}

	@Override
	public Class<? extends JParamEditor> getParamEditorClass () {
		return StringParamEditor.class;
	}

}
