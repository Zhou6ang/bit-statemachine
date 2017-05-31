/**
 * 
 */
package org.bitframework.statemachine.parsing;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.bitframework.statemachine.annotation.Action;
import org.bitframework.statemachine.annotation.Condition;
import org.bitframework.statemachine.annotation.State;

/**
 * @author ganzhou
 *
 */
public class AnnotationParser {
	private static final Logger logger = Logger.getLogger(AnnotationParser.class.getName());
	private List<org.bitframework.statemachine.pojo.Action> actions = new ArrayList<>();
	private List<org.bitframework.statemachine.pojo.Condition> conditions = new ArrayList<>();
	private List<org.bitframework.statemachine.pojo.State> states = new ArrayList<>();
	
	public AnnotationParser(Class<?>[] clzs) throws Exception{
		if(clzs == null || clzs.length == 0){
			return;
		}
		for (Class<?> clz : clzs) {
			parsing(clz);
		}
		
	}
	
	public List<org.bitframework.statemachine.pojo.Action> getActions() {
		return actions;
	}

	public List<org.bitframework.statemachine.pojo.Condition> getConditions() {
		return conditions;
	}
	
	public List<org.bitframework.statemachine.pojo.State> getStates() {
		return states;
	}

	private void parsing(Class<?> clz) throws Exception{
		logger.info("["+clz.getName()+"] will be parsing...");
		Object instance = null;
		for(Method m : clz.getDeclaredMethods()){
			for(Action act : m.getDeclaredAnnotationsByType(Action.class)){
				String name = act.name().isEmpty() ? m.getName() : act.name();
				validate(name,m,false,false,Arrays.asList(Object.class),Arrays.asList(Object.class));
				org.bitframework.statemachine.pojo.Action action = new org.bitframework.statemachine.pojo.Action();
				action.setFrom(act.from());
				action.setTo(act.to());
				instance = instance == null ? newInstance(clz) : instance;
				action.setInstance(instance);
				action.setMethod(m);
				action.setName(name);
				action.setParameters(m.getParameters());
				actions.add(action);
			}
			
			for(Condition cond : m.getDeclaredAnnotationsByType(Condition.class)){
				String name = cond.name().isEmpty() ? m.getName() : cond.name();
				validate(name,m,false,true,Arrays.asList(Object.class),Arrays.asList(boolean.class,Boolean.class));
				org.bitframework.statemachine.pojo.Condition condition = new org.bitframework.statemachine.pojo.Condition();
				condition.setFrom(cond.from());
				condition.setTo(cond.to());
				instance = instance == null ? newInstance(clz) : instance;
				condition.setInstance(instance);
				condition.setMethod(m);
				condition.setName(name);
				condition.setParameters(m.getParameters());
				conditions.add(condition);
			}
			
			for(State sta : m.getDeclaredAnnotationsByType(State.class)){
				String name = sta.value().isEmpty() ? m.getName() : sta.value();
				validate(name,m,false,false,Arrays.asList(Object.class),Arrays.asList(Object.class));
				org.bitframework.statemachine.pojo.State state = new org.bitframework.statemachine.pojo.State();
				instance = instance == null ? newInstance(clz) : instance;
				state.setInstance(instance);
				state.setMethod(m);
				state.setName(name);
				state.setParameters(m.getParameters());
				states.add(state);
			}
		}
		
		logger.info("Parsing ["+clz.getName()+"] done.");
	}
	
	/**
	 * @param clz
	 * @return
	 * @throws InstantiationException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	private Object newInstance(Class<?> clz) throws InstantiationException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object instance = null;
		try {
			instance = clz.newInstance();
		} catch (IllegalAccessException e) {
			Constructor<?> constructor = clz.getDeclaredConstructor();
			constructor.setAccessible(true);
			logger.warning("Could not newInstance,trying to set accessible for constructor.");
			instance = constructor.newInstance();
		} catch (InstantiationException e) {
			String msg = "Class definition error:"+clz.getName()+", plz check.";
			logger.severe(msg);
			throw new RuntimeException(msg,e);
		}
		
		return instance;
	}

	/**
	 * @param annName
	 * @param method
	 * @param mustPresentInput
	 * @param mustPresentOutput
	 * @param inputType
	 * @param outputType
	 */
	private void validate(String annName,Method method,boolean mustPresentInput, boolean mustPresentOutput, List<Class<?>> inputType,List<Class<?>> outputType) {
		String info = method.getDeclaringClass().getName()+"."+method.getName();
		if (method.getParameterCount() > 1) {
			throw new RuntimeException("Annotation: ["+annName+"].The max size of parameter should be 1 in Method-" + method.getName()
					+ ", current is " + method.getParameterCount());
		}
		if(mustPresentInput){
			if(method.getParameters().length == 0){
				throw new RuntimeException("Annotation: ["+annName+"].The [" + info + "] must have one of "+inputType+" parameter type.");
			}
		}
		
		for (Parameter para : method.getParameters()) {
			if(inputType!= null && (!inputType.isEmpty()) && (!inputType.contains(para.getType()))){
				throw new RuntimeException("Annotation: ["+annName+"].The parameter type of method [" + info+ "] should be one of "+inputType+", but current is [" + para.getType()+"].");
			}
		}
		
		if(mustPresentOutput){
			if(method.getReturnType() == void.class){
				throw new RuntimeException("Annotation: ["+annName+"].The method [" + info+ "] must have one of "+outputType+" return parameter.");
			}
		}
		
		if(outputType!= null && (!outputType.isEmpty()) && (method.getReturnType() != void.class) &&(!outputType.contains(method.getReturnType()))){
			throw new RuntimeException("Annotation: ["+annName+"].The parameter type of method [" + info+ "] should be one of "+outputType+", but current is [" + method.getReturnType()+"].");
		}
		
		method.setAccessible(true);
		
		logger.info("Validating parameter and return type of ["+info+"] done.");
	}
}
