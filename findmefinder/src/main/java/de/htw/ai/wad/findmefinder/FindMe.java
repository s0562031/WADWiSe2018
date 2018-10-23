package de.htw.ai.wad.findmefinder;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;

/**
 * Annotation FindMe
 * @author Dustin
 */
@Retention(RetentionPolicy.RUNTIME) // waehrend Laufzeit verfuegbar
@Target({ElementType.METHOD, ElementType.FIELD}) // fuer Methoden und Attribute einsetzbar
@Documented
public @interface FindMe {
	String issueId() default "No issueID";
	String description() default "No Description";
	String author() default "Dustin";
	String date() default "Oct 23, 2018";
}