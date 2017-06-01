package com.github.zhou6ang.statemachine;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.github.zhou6ang.statemachine.engine.StateEngine;
import com.github.zhou6ang.statemachine.parsing.AnnotationParser;
import com.google.common.reflect.ClassPath;

public class StateMachineApp 
{
	private static final Logger logger = Logger.getLogger(StateMachineApp.class.getName());
	/**
	 * 
	 */
	private StateMachineApp() {
	}
    
    public static void registry(Class<?> clazz) throws IOException{
    	String pkgName = clazz.getPackage().getName();
    	ClassPath cp = ClassPath.from(clazz.getClassLoader());
    	List<Class<?>> list = new ArrayList<>();
    	cp.getTopLevelClassesRecursive(pkgName).forEach(e->{
    		Class<?> clz = e.load();
			if (clz.isInterface() || clz.isEnum() || clz.isAnnotation() || Modifier.isAbstract(clz.getModifiers())
					|| Modifier.isStatic(clz.getModifiers()) || Modifier.isStrict(clz.getModifiers())) {
				logger.warning("Only public class can be loading !!! skiping "+clz.getName());
				return;
			}
			list.add(clz);
    	});
    	
		try {
			AnnotationParser ann = new AnnotationParser(list.toArray(new Class<?>[]{}));
			StateEngine stateEngine = new StateEngine(ann);
			stateEngine.start();
		} catch (Exception e2) {
			throw new RuntimeException(e2);
		}
		
    }

	
}
