/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dmerkushov.configurator.param.string;

import java.awt.Dimension;
import java.util.Objects;
import javax.json.JsonObject;
import javax.swing.JTextField;
import ru.dmerkushov.configurator.configadapter.ConfigAdapterFactory;
import ru.dmerkushov.configurator.param.JParamEditor;

/**
 *
 * @author dmerkushov
 */
public class StringParamEditor extends JParamEditor {

	JTextField textField = new JTextField ();

	public StringParamEditor (JsonObject param) {
		super (param);

		this.add (textField);

		String curval = ConfigAdapterFactory.getInstance ().getAdapter (param).loadParamValue (param);
		textField.setText (curval);

		Dimension tfSize = new Dimension (600, 20);
		textField.setSize (tfSize);
		textField.setMinimumSize (tfSize);
		textField.setMaximumSize (tfSize);
		textField.setPreferredSize (tfSize);

		Dimension mySize = new Dimension (610, 30);
		this.setSize (mySize);
		this.setMinimumSize (mySize);
		this.setMaximumSize (mySize);
		this.setPreferredSize (mySize);
	}

	@Override
	public String getValue () {
		return textField.getText ();
	}

	@Override
	public void setValue (String value) {
		Objects.requireNonNull (value, "value");

		textField.setText (value);
	}

	@Override
	public void setDescription (String description) {
		this.setToolTipText (description);
		textField.setToolTipText (description);
	}

}
