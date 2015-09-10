package org.hibernate.annotations.common.reflection;

import java.lang.annotation.Annotation;

public abstract interface AnnotationReader
{
  public abstract <T extends Annotation> T getAnnotation(Class<T> paramClass);
  
  public abstract <T extends Annotation> boolean isAnnotationPresent(Class<T> paramClass);
  
  public abstract Annotation[] getAnnotations();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.reflection.AnnotationReader
 * JD-Core Version:    0.7.0.1
 */