/**
 * 
 */
package com.github.zhou6ang.statemachine.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
/**
 * @author ganzhou
 *
 */
public @interface Condition {
	String name() default "";
	String from();
	String to();
}
