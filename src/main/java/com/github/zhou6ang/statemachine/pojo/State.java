/**
 * 
 */
package com.github.zhou6ang.statemachine.pojo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author ganzhou
 * @since 2017-05-26
 */
public class State {
	private String name;
	private Object instance;
	private Method method;
	private Parameter[] parameters;
	
	public Parameter[] getParameters() {
		return parameters;
	}
	public void setParameters(Parameter[] parameters) {
		this.parameters = parameters;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Object invoke(Object ...param) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		if(param == null || param.length == 0 || parameters.length == 0){
			return method.invoke(instance);
		}
		return method.invoke(instance, param);
	}
	@Override
	public String toString() {
		return "{name:"+name+", method:"+method+", instance:"+instance+", parameter:"+(parameters!= null?parameters.length:parameters)+"}";
	}
	
}
