package org.hibernate.annotations.common.reflection;

import java.lang.reflect.AnnotatedElement;
import java.util.Map;

public abstract interface MetadataProvider
{
  public abstract Map<Object, Object> getDefaults();
  
  public abstract AnnotationReader getAnnotationReader(AnnotatedElement paramAnnotatedElement);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.reflection.MetadataProvider
 * JD-Core Version:    0.7.0.1
 */