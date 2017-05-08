package com.bolyartech.forge.server.misc;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;


/**
 * Annotation that defines parameters, return values and fields that they <b>might be</b> null
 * You need to configure your IDE's inspections in order to get use of this annotation.
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({METHOD, PARAMETER, LOCAL_VARIABLE, FIELD})
public @interface Nullable {
}