/**
 * 
 */
package com.github.zhou6ang.statemachine.pojo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author ganzhou
 *
 */
public class Action {

	private String name;
	private String from;
	private String to;
	private Object instance;
	private Method method;
	private Parameter[] parameters;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public Object getInstance() {
		return instance;
	}
	public void setInstance(Object instance) {
		this.instance = instance;
	}
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	public Parameter[] getParameters() {
		return parameters;
	}
	public void setParameters(Parameter[] parameters) {
		this.parameters = parameters;
	}
	
	public Object invoke(Object... param)throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (param == null || param.length == 0 || parameters.length == 0) {
			return method.invoke(instance);
		}
		return method.invoke(instance, param);
	}
	@Override
	public String toString() {
		return "Action name:"+name+", from:"+from+" to:"+to+",method:"+method.getName()+",instance:"+instance.getClass().getName()+", parameter length:"+parameters.length;
	}
	
	
}
