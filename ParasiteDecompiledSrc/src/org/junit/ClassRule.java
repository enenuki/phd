package org.junit;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD})
public @interface ClassRule {}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.ClassRule
 * JD-Core Version:    0.7.0.1
 */