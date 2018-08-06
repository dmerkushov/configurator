/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dmerkushov.configurator.param.bool;

import java.awt.Dimension;
import javax.json.JsonObject;
import javax.swing.JCheckBox;
import ru.dmerkushov.configurator.configadapter.ConfigAdapterFactory;
import ru.dmerkushov.configurator.param.JParamEditor;

/**
 *
 * @author dmerkushov
 */
public class BooleanParamEditor extends JParamEditor {

	JCheckBox checkBox = new JCheckBox ();

	public BooleanParamEditor (JsonObject param) {
		super (param);

		this.add (checkBox);

		String curval = ConfigAdapterFactory.getInstance ().getAdapter (param).loadParamValue (param);
		setValue (curval);

		Dimension mySize = new Dimension (160, 30);
		this.setSize (mySize);
		this.setMinimumSize (mySize);
		this.setMaximumSize (mySize);
		this.setPreferredSize (mySize);

		this.setAlignmentY (0.0f);
	}

	@Override
	public String getValue () {
		return String.valueOf (checkBox.isSelected ());
	}

	@Override

	public void setValue (String value) {
		checkBox.setSelected (value.equals ("true"));
	}

	@Override
	public void setDescription (String description) {
		checkBox.setToolTipText (description);
	}

}
