package org.jboss.logging;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({java.lang.annotation.ElementType.METHOD})
@Documented
public @interface LogMessage
{
  Logger.Level level() default Logger.Level.INFO;
  
  Class<?> loggingClass() default Void.class;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.logging.LogMessage
 * JD-Core Version:    0.7.0.1
 */