/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dmerkushov.configurator.param;

/**
 *
 * @author dmerkushov
 */
public interface ParamType {

	Class<? extends JParamEditor> getParamEditorClass ();

	boolean checkValue (String value);

}
