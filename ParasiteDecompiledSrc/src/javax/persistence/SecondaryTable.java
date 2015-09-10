package javax.persistence;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SecondaryTable
{
  String name();
  
  String catalog() default "";
  
  String schema() default "";
  
  PrimaryKeyJoinColumn[] pkJoinColumns() default {};
  
  UniqueConstraint[] uniqueConstraints() default {};
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.SecondaryTable
 * JD-Core Version:    0.7.0.1
 */