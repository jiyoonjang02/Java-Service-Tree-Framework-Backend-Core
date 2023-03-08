package com.egovframework.ple.serviceframework.validation.custom.constraints;

import com.egovframework.ple.serviceframework.validation.custom.validator.ContainedValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ContainedValidator.class)
public @interface Contained {
	
	String message() default "The target field's mapping value is not contained in the given string array.";
	
	String[] values() default {};
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}