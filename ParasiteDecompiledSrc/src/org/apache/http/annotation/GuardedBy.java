package org.apache.http.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
public @interface GuardedBy
{
  String value();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.annotation.GuardedBy
 * JD-Core Version:    0.7.0.1
 */