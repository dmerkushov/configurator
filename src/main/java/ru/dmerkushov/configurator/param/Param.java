/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dmerkushov.configurator.param;

/**
 *
 * @author dmerkushov
 * @param <T>
 */
public interface Param<T extends ParamType> {

	JParamEditor<T> getNewEditor ();

}
