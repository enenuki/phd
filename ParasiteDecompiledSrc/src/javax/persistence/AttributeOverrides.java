package javax.persistence;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AttributeOverrides
{
  AttributeOverride[] value();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.AttributeOverrides
 * JD-Core Version:    0.7.0.1
 */