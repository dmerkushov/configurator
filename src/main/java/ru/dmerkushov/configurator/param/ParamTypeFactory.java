/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dmerkushov.configurator.param;

import ru.dmerkushov.configurator.param.bool.BooleanParamType;
import ru.dmerkushov.configurator.param.jullevel.JulLoggingLevelParamType;
import ru.dmerkushov.configurator.param.string.DoubleParamType;
import ru.dmerkushov.configurator.param.string.IpParamType;
import ru.dmerkushov.configurator.param.string.Ipv4ParamType;
import ru.dmerkushov.configurator.param.string.Ipv6ParamType;
import ru.dmerkushov.configurator.param.string.JsonArrayParamType;
import ru.dmerkushov.configurator.param.string.JsonObjectParamType;
import ru.dmerkushov.configurator.param.string.LongintParamType;
import ru.dmerkushov.configurator.param.string.StringParamType;
import ru.dmerkushov.configurator.param.string.TcpUdpPortParamType;
import ru.dmerkushov.configurator.param.string.UrlParamType;
import ru.dmerkushov.configurator.param.string.UuidParamType;

/**
 *
 * @author dmerkushov
 */
public class ParamTypeFactory {

	////////////////////////////////////////////////////////////////////////////
	// ParamTypeFactory is a singleton class
	////////////////////////////////////////////////////////////////////////////
	private static ParamTypeFactory _instance;

	/**
	 * Get the single instance of ParamTypeFactory
	 *
	 * @return The same instance of ParamTypeFactory every time the method is
	 * called
	 */
	public static synchronized ParamTypeFactory getInstance () {
		if (_instance == null) {
			_instance = new ParamTypeFactory ();
		}
		return _instance;
	}

	private ParamTypeFactory () {
	}
	////////////////////////////////////////////////////////////////////////////

	public ParamType getParamType (String paramTypeName) {
		switch (paramTypeName) {
			case "String":
				return StringParamType.getInstance ();
			case "Long integer":
				return LongintParamType.getInstance ();
			case "Floating point, double precision":
				return DoubleParamType.getInstance ();
			case "Boolean":
				return BooleanParamType.getInstance ();
			case "URL":
				return UrlParamType.getInstance ();
			case "UUID":
				return UuidParamType.getInstance ();
			case "JSON Object":
				return JsonObjectParamType.getInstance ();
			case "JSON Array":
				return JsonArrayParamType.getInstance ();
			case "IP Address":
				return IpParamType.getInstance ();
			case "IPv4 Address":
				return Ipv4ParamType.getInstance ();
			case "IPv6 Address":
				return Ipv6ParamType.getInstance ();
			case "TCP or UDP Port":
				return TcpUdpPortParamType.getInstance ();
			case "JUL logging level":
				return JulLoggingLevelParamType.getInstance ();
			default:
				return StringParamType.getInstance ();
		}
	}

}
