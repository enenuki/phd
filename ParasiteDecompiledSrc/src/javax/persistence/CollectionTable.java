package javax.persistence;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CollectionTable
{
  String name() default "";
  
  String catalog() default "";
  
  String schema() default "";
  
  JoinColumn[] joinColumns() default {};
  
  UniqueConstraint[] uniqueConstraints() default {};
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.CollectionTable
 * JD-Core Version:    0.7.0.1
 */