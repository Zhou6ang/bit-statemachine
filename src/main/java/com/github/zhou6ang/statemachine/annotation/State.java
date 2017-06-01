/**
 * 
 */
package com.github.zhou6ang.statemachine.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target({TYPE,METHOD})
/**
 * @author ganzhou
 *
 */
public @interface State {
	String value() default "";
}
