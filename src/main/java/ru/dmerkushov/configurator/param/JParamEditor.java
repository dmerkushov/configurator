/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dmerkushov.configurator.param;

import java.util.Objects;
import javax.json.JsonObject;
import javax.swing.JPanel;
import ru.dmerkushov.configurator.configadapter.ConfigAdapter;
import ru.dmerkushov.configurator.configadapter.ConfigAdapterFactory;

/**
 *
 * @author dmerkushov
 */
public abstract class JParamEditor<T extends ParamType> extends JPanel {

	protected final JsonObject param;

	public JParamEditor (JsonObject param) {
		Objects.requireNonNull (param, "param");

		this.param = param;
	}

	public abstract String getValue ();

	public abstract void setValue (String value);

	public void saveValue () {
		ConfigAdapter configAdapter = ConfigAdapterFactory.getInstance ().getAdapter (param);
		configAdapter.saveParamValue (param, getValue ());
	}

	public abstract void setDescription (String description);

}
