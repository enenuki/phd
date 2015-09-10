package org.hibernate.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.PACKAGE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NamedQuery
{
  String name();
  
  String query();
  
  FlushModeType flushMode() default FlushModeType.PERSISTENCE_CONTEXT;
  
  boolean cacheable() default false;
  
  String cacheRegion() default "";
  
  int fetchSize() default -1;
  
  int timeout() default -1;
  
  String comment() default "";
  
  CacheModeType cacheMode() default CacheModeType.NORMAL;
  
  boolean readOnly() default false;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.NamedQuery
 * JD-Core Version:    0.7.0.1
 */