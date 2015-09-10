package javax.persistence;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SequenceGenerator
{
  String name();
  
  String sequenceName() default "";
  
  String catalog() default "";
  
  String schema() default "";
  
  int initialValue() default 1;
  
  int allocationSize() default 50;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.SequenceGenerator
 * JD-Core Version:    0.7.0.1
 */