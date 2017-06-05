package com.github.zhou6ang.statemachine;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import com.github.zhou6ang.statemachine.engine.StateEngine;
import com.github.zhou6ang.statemachine.parsing.AnnotationParser;
import com.google.common.reflect.ClassPath;

public class StateMachineApp 
{
	private static final Logger logger = Logger.getLogger(StateMachineApp.class.getName());
	private static Map<Class<?>, Object> map = new ConcurrentHashMap<>();
	 // Dummy value to associate with an Object in the backing Map
    private static final Object PRESENT = new Object();
	/**
	 * 
	 */
	private StateMachineApp() {
	}
    
    public static void registry(Class<?> clazz) throws IOException{
    	String pkgName = clazz.getPackage().getName();
    	ClassPath cp = ClassPath.from(clazz.getClassLoader());
    	cp.getTopLevelClassesRecursive(pkgName).forEach(e->{
    		Class<?> clz = e.load();
			if (clz.isInterface() || clz.isEnum() || clz.isAnnotation() || Modifier.isAbstract(clz.getModifiers())
					|| Modifier.isStatic(clz.getModifiers()) || Modifier.isStrict(clz.getModifiers())) {
				logger.warning("Only public class can be loading !!! skiping "+clz.getName());
				return;
			}
			map.putIfAbsent(clz, PRESENT);
    	});
    	logger.info("Clazzes registry done. Clazzes size:"+map.size());
    }

    public static void start(Object inputObj){
    	try {
    		if(map.isEmpty()){
    			throw new RuntimeException("Please invoke StateMachineApp.registry(Class<?> clazz) firstly.");
    		}
			AnnotationParser ann = new AnnotationParser(map.keySet().toArray(new Class<?>[]{}));
			StateEngine stateEngine = new StateEngine(ann);
			stateEngine.start(inputObj);
		} catch (Exception e2) {
			throw new RuntimeException(e2);
		}
    	logger.info("State Machine is running done.");
    }
    
    public static void registryAndStart(Class<?> clazz) throws IOException{
    	registryAndStart(clazz,null);
    }
    
    public static void registryAndStart(Class<?> clazz, Object inputObj) throws IOException{
    	registry(clazz);
    	start(inputObj);
    }

    public static void start(){
    	start(null);
    }
}
