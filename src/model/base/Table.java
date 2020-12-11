package model.base;

import java.lang.annotation.Target;
import java.lang.reflect.Field;

import model.example.Employee;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})  
@Retention(RetentionPolicy.RUNTIME)  
public @interface Table {
	String column() default "";
	String name() default "";
	String list() default "";
	
//	Class target();
}
