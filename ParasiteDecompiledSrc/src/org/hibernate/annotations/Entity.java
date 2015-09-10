package org.hibernate.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Deprecated
public @interface Entity
{
  /**
   * @deprecated
   */
  boolean mutable() default true;
  
  /**
   * @deprecated
   */
  boolean dynamicInsert() default false;
  
  /**
   * @deprecated
   */
  boolean dynamicUpdate() default false;
  
  /**
   * @deprecated
   */
  boolean selectBeforeUpdate() default false;
  
  /**
   * @deprecated
   */
  PolymorphismType polymorphism() default PolymorphismType.IMPLICIT;
  
  /**
   * @deprecated
   */
  OptimisticLockType optimisticLock() default OptimisticLockType.VERSION;
  
  /**
   * @deprecated
   */
  String persister() default "";
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.Entity
 * JD-Core Version:    0.7.0.1
 */