/**
 * 
 */
package org.bitframework.statemachine.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(METHOD)

/**
 * @author ganzhou
 *
 */
@Repeatable(Actions.class)
public @interface Action {
	String name() default "";
	String from() ;
	String to();
}
