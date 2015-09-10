package org.hibernate.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.persistence.Column;
import javax.persistence.FetchType;

@Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Any
{
  String metaDef() default "";
  
  Column metaColumn();
  
  FetchType fetch() default FetchType.EAGER;
  
  boolean optional() default true;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.Any
 * JD-Core Version:    0.7.0.1
 */