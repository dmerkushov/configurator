/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dmerkushov.configurator.param.jullevel;

import java.awt.Dimension;
import javax.json.JsonObject;
import javax.swing.JComboBox;
import ru.dmerkushov.configurator.configadapter.ConfigAdapterFactory;
import ru.dmerkushov.configurator.param.JParamEditor;

/**
 *
 * @author dmerkushov
 */
public class JulLoggingLevelParamEditor extends JParamEditor {

	JComboBox<JulLoggingLevelParamType.PossibleValues> comboBox = new JComboBox<> (JulLoggingLevelParamType.PossibleValues.values ());

	public JulLoggingLevelParamEditor (JsonObject param) {
		super (param);

		this.add (comboBox);

		String curval = ConfigAdapterFactory.getInstance ().getAdapter (param).loadParamValue (param);
		comboBox.setSelectedItem (JulLoggingLevelParamType.PossibleValues.valueOf (curval));

		Dimension cbSize = new Dimension (150, 20);
		comboBox.setSize (cbSize);
		comboBox.setMinimumSize (cbSize);
		comboBox.setMaximumSize (cbSize);
		comboBox.setPreferredSize (cbSize);

		Dimension mySize = new Dimension (160, 30);
		this.setSize (mySize);
		this.setMinimumSize (mySize);
		this.setMaximumSize (mySize);
		this.setPreferredSize (mySize);
	}

	@Override
	public String getValue () {
		return comboBox.getSelectedItem ().toString ();
	}

	@Override
	public void setValue (String value) {
		comboBox.setSelectedItem (JulLoggingLevelParamType.PossibleValues.valueOf (value));
	}

	@Override
	public void setDescription (String description) {
		comboBox.setToolTipText (description);
	}

}
