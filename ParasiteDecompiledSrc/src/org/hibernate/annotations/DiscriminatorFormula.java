package org.hibernate.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DiscriminatorFormula
{
  String value();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.DiscriminatorFormula
 * JD-Core Version:    0.7.0.1
 */