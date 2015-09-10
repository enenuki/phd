package javax.persistence;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityResult
{
  Class entityClass();
  
  FieldResult[] fields() default {};
  
  String discriminatorColumn() default "";
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.EntityResult
 * JD-Core Version:    0.7.0.1
 */