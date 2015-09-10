package org.hibernate.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.PACKAGE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FetchProfile
{
  String name();
  
  FetchOverride[] fetchOverrides();
  
  @Target({java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.PACKAGE})
  @Retention(RetentionPolicy.RUNTIME)
  public static @interface FetchOverride
  {
    Class<?> entity();
    
    String association();
    
    FetchMode mode();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.FetchProfile
 * JD-Core Version:    0.7.0.1
 */