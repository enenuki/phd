package javax.persistence;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NamedQuery
{
  String name();
  
  String query();
  
  LockModeType lockMode() default LockModeType.NONE;
  
  QueryHint[] hints() default {};
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.NamedQuery
 * JD-Core Version:    0.7.0.1
 */