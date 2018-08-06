/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dmerkushov.configurator.configadapter;

import javax.json.JsonObject;

/**
 *
 * @author dmerkushov
 */
public interface ConfigAdapter {

	public String loadParamValue (JsonObject param);

	public void saveParamValue (JsonObject param, String paramValue);

}
