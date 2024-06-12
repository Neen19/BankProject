package ru.sarmosov.bankstarter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME )
public @interface  Logging {
    String value() default "";
    boolean entering() default true;
    boolean exiting() default true;
    String level() default "FATAL";
}
