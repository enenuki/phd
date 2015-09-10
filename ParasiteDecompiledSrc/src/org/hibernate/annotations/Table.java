package org.hibernate.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Table
{
  String appliesTo();
  
  Index[] indexes() default {};
  
  String comment() default "";
  
  ForeignKey foreignKey() default @ForeignKey(name="");
  
  FetchMode fetch() default FetchMode.JOIN;
  
  boolean inverse() default false;
  
  boolean optional() default true;
  
  SQLInsert sqlInsert() default @SQLInsert(sql="");
  
  SQLUpdate sqlUpdate() default @SQLUpdate(sql="");
  
  SQLDelete sqlDelete() default @SQLDelete(sql="");
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.Table
 * JD-Core Version:    0.7.0.1
 */