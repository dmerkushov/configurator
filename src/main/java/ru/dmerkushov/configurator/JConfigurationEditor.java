/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dmerkushov.configurator;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import static ru.dmerkushov.configurator.Main.CONFIGURATOR_VERSION_EXPECTED;
import ru.dmerkushov.configurator.param.JParamEditor;
import ru.dmerkushov.configurator.param.ParamType;
import ru.dmerkushov.configurator.param.ParamTypeFactory;

/**
 *
 * @author dmerkushov
 */
public class JConfigurationEditor extends JPanel {

	public final JFrame frame;
	public final File configurationDescriptionFile;

	public static final int HINTFRAME_WIDTH = 400;
	public static final int HINTFRAME_HEIGHT = 150;

	JButton checkBtn = new JButton ("Проверить");
	JButton saveBtn = new JButton ("Сохранить");
	JButton cancelBtn = new JButton ("Закрыть");

	final List<JParamEditor> paramEditors = new ArrayList<> ();
	final Map<JParamEditor, ParamType> paramTypes = new HashMap<> ();
	final JDialog hintDia;

	public JConfigurationEditor (JFrame frame, File configurationDescriptionFile) {
		Objects.requireNonNull (frame, "frame");
		Objects.requireNonNull (configurationDescriptionFile, "configurationDescriptionFile");

		this.frame = frame;
		this.configurationDescriptionFile = configurationDescriptionFile;

		hintDia = new JDialog (frame);
		hintDia.setUndecorated (true);
		hintDia.setSize (HINTFRAME_WIDTH, HINTFRAME_HEIGHT);

		FileReader fr;
		try {
			fr = new FileReader (configurationDescriptionFile);
		} catch (FileNotFoundException ex) {
			throw new RuntimeException (ex);
		}

		JsonReader jr = Json.createReader (fr);

		JsonObject configJson = jr.readObject ();

		{	// Variable visibility block
			String configuratorVersionInFile = configJson.getString ("configuratorVersion", "");
			if (configuratorVersionInFile.equals ("")) {
				throw new RuntimeException ("Non-configurator file chosen: " + configurationDescriptionFile.getAbsolutePath ());
			}
			if (!configuratorVersionInFile.equals (CONFIGURATOR_VERSION_EXPECTED)) {
				throw new RuntimeException ("Configurator version mismatch: expected " + CONFIGURATOR_VERSION_EXPECTED + ", but found " + configuratorVersionInFile);
			}
		}

		JsonArray paramGroups = configJson.getJsonArray ("paramGroups");

		JPanel configurationPanel = new JPanel ();
		configurationPanel.setLayout (new GridBagLayout ());

		int rowIndex = 0;
		Font labelFont;
		Font groupLabelFont;
		Font softwareTitleFont;
		float labelFontSize;

		{	// Variable visibility block
			String softwareTitle = configJson.getString ("configuredSoftware", "");
			JLabel softwareTitleLabel = new JLabel (softwareTitle);
			labelFont = softwareTitleLabel.getFont ().deriveFont (Font.PLAIN);
			labelFontSize = labelFont.getSize ();
			groupLabelFont = labelFont.deriveFont (Font.BOLD).deriveFont (labelFontSize * 1.2f);
			softwareTitleFont = labelFont.deriveFont (Font.BOLD).deriveFont (labelFontSize * 1.5f);
			softwareTitleLabel.setFont (softwareTitleFont);
			GridBagConstraints softwareTitleConstraints = new GridBagConstraints ();
			softwareTitleConstraints.gridy = rowIndex;
			softwareTitleConstraints.anchor = GridBagConstraints.WEST;
			configurationPanel.add (softwareTitleLabel, softwareTitleConstraints);
		}

		for (JsonObject paramGroup : paramGroups.getValuesAs (JsonObject.class)) {
			rowIndex++;

			String paramGroupName = paramGroup.getString ("paramGroupName", "Группа параметров");

			{	// Variable visibility block
				JLabel groupLabel = new JLabel (paramGroupName);
				groupLabel.setFont (groupLabelFont);
				GridBagConstraints groupLabelConstraints = new GridBagConstraints ();
				groupLabelConstraints.gridy = rowIndex;
				groupLabelConstraints.gridwidth = 2;
				groupLabelConstraints.anchor = GridBagConstraints.WEST;
				configurationPanel.add (groupLabel, groupLabelConstraints);
			}

			JsonArray params = paramGroup.getJsonArray ("params");
			for (JsonObject param : params.getValuesAs (JsonObject.class)) {
				rowIndex++;

				{	// Variable visibility block
					String paramTitle = param.getString ("title", "(no title)");
					JLabel paramTitleLabel = new JLabel (paramTitle);
					paramTitleLabel.setFont (labelFont);
					GridBagConstraints paramTitleLabelConstraints = new GridBagConstraints ();
					paramTitleLabelConstraints.gridx = 0;
					paramTitleLabelConstraints.gridy = rowIndex;
					paramTitleLabelConstraints.anchor = GridBagConstraints.WEST;
					configurationPanel.add (paramTitleLabel, paramTitleLabelConstraints);
					final String description = param.getString ("description", "");
					if (!description.equals ("")) {
						final JButton descrButton = new JButton ("?");
						descrButton.addActionListener (new ActionListener () {
							@Override
							public void actionPerformed (ActionEvent e) {
								Point descrLoc = descrButton.getLocationOnScreen ();
								Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize ();
								if (descrLoc.x + HINTFRAME_WIDTH > screenSize.width) {
									descrLoc.x = screenSize.width - HINTFRAME_WIDTH;
								}
								if (descrLoc.y + HINTFRAME_HEIGHT > screenSize.height) {
									descrLoc.y = screenSize.height - HINTFRAME_HEIGHT;
								}
								hintDia.setLocation (descrLoc);

								Component[] components;
								synchronized (hintDia.getContentPane ().getTreeLock ()) {
									components = hintDia.getContentPane ().getComponents ();
								}
								for (Component component : components) {
									hintDia.remove (component);
								}

								JEditorPane hintPane = new JEditorPane ("text/html", description);
								hintPane.setEditable (false);

								JScrollPane scrollPane = new JScrollPane (hintPane);
								scrollPane.setVerticalScrollBarPolicy (JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
								scrollPane.setHorizontalScrollBarPolicy (JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

								hintDia.add (scrollPane);

								hintPane.addMouseListener (new MouseAdapter () {
									public void mouseClicked (MouseEvent e) {
										hintDia.setVisible (false);
									}
								});

								hintDia.setVisible (true);
							}
						});
						GridBagConstraints descrButtonConstraints = new GridBagConstraints ();
						descrButtonConstraints.gridx = 1;
						descrButtonConstraints.gridy = rowIndex;
						descrButtonConstraints.anchor = GridBagConstraints.WEST;
						configurationPanel.add (descrButton, descrButtonConstraints);
					}
				}
				{	// Variable visibility block
					String paramTypeStr = param.getString ("paramType", "String");
					ParamType paramType = ParamTypeFactory.getInstance ().getParamType (paramTypeStr);

					Class paramEditorClass = paramType.getParamEditorClass ();
					Constructor<JParamEditor> paramEditorConstructor;
					try {
						paramEditorConstructor = paramEditorClass.getConstructor (JsonObject.class);
					} catch (NoSuchMethodException | SecurityException ex) {
						throw new RuntimeException ("Could not find a JParamEditor constructor with JsonObject as parameter for param: " + param.toString (), ex);
					}
					JParamEditor paramEditor;
					try {
						paramEditor = paramEditorConstructor.newInstance (param);
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
						throw new RuntimeException ("Could not invoke the JParamEditor constructor with JsonObject as parameter for param: " + param.toString (), ex);
					}
					paramEditors.add (paramEditor);

					GridBagConstraints paramEditorConstraints = new GridBagConstraints ();
					paramEditorConstraints.gridx = 2;
					paramEditorConstraints.gridy = rowIndex;
					paramEditorConstraints.anchor = GridBagConstraints.WEST;
					configurationPanel.add (paramEditor, paramEditorConstraints);

					paramTypes.put (paramEditor, paramType);
				}
			}
		}

		JScrollPane scrollPane = new JScrollPane (configurationPanel);
		scrollPane.setHorizontalScrollBarPolicy (JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy (JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		scrollPane.setPreferredSize (new Dimension (frame.getWidth (), frame.getHeight () - 150));

		this.add (scrollPane);

		checkBtn.addActionListener (new CheckBtnActionListener ());
		saveBtn.addActionListener (new SaveBtnActionListener ());
		cancelBtn.addActionListener (new CancelBtnActionListener ());

		this.add (checkBtn);
		this.add (saveBtn);
		this.add (cancelBtn);
	}

	private boolean check () {
		boolean allOk = true;
		for (JParamEditor paramEditor : paramEditors) {
			String value = paramEditor.getValue ();
			boolean ok = paramTypes.get (paramEditor).checkValue (value);
			if (ok) {
				paramEditor.setBackground (Color.green);
			} else {
				paramEditor.setBackground (Color.red);
			}

			allOk &= ok;
		}

		return allOk;
	}

	private void save () {
		if (check ()) {
			for (JParamEditor paramEditor : paramEditors) {
				paramEditor.saveValue ();
			}
			JOptionPane.showMessageDialog (this, "Настройка сохранена", "Сохранено", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog (this, "Проверьте значения параметров.\nНекорректные значения отмечены красным.", "Не сохранено", JOptionPane.ERROR_MESSAGE);
		}
	}

	private class CheckBtnActionListener implements ActionListener {

		@Override
		public void actionPerformed (ActionEvent e) {
			check ();
		}
	}

	private class SaveBtnActionListener implements ActionListener {

		@Override
		public void actionPerformed (ActionEvent e) {
			save ();
		}
	}

	private class CancelBtnActionListener implements ActionListener {

		@Override
		public void actionPerformed (ActionEvent e) {
			frame.setVisible (false);
			frame.dispose ();
		}
	}
}
