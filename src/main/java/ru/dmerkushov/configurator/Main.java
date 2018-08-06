/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dmerkushov.configurator;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author dmerkushov
 */
public class Main {

	public static final String CONFIGURATOR_VERSION_EXPECTED = "1.0";

	public static void main (String[] args) {

		JFrame frame = new JFrame ("Конфигуратор");
		frame.setSize (1200, 800);
		frame.setResizable (false);
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

		SwingUtilities.invokeLater (() -> {

			JFileChooser fc = new JFileChooser ();
			fc.setDialogTitle ("Выберите файл с описанием настройки");
			fc.setFileFilter (new FileFilter () {
				@Override
				public boolean accept (File f) {
					return (f != null && (f.isDirectory () || f.getAbsolutePath ().toLowerCase ().endsWith (".json")));
				}

				@Override
				public String getDescription () {
					return "JSON";
				}
			});
			int retvalue = fc.showOpenDialog (null);
			if (retvalue == JFileChooser.CANCEL_OPTION) {
				return;
			}

			File confugurationDescriptionFile = fc.getSelectedFile ();
			System.out.println (confugurationDescriptionFile.getAbsolutePath ());

			JConfigurationEditor configurationEditor = new JConfigurationEditor (frame, confugurationDescriptionFile);

			frame.add (configurationEditor);

			frame.setVisible (true);
		});
	}
}
