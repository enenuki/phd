package org.hibernate.annotations.common.reflection;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Map;

public abstract interface ReflectionManager
{
  public abstract <T> XClass toXClass(Class<T> paramClass);
  
  public abstract Class toClass(XClass paramXClass);
  
  public abstract Method toMethod(XMethod paramXMethod);
  
  public abstract <T> XClass classForName(String paramString, Class<T> paramClass)
    throws ClassNotFoundException;
  
  public abstract XPackage packageForName(String paramString)
    throws ClassNotFoundException;
  
  public abstract <T> boolean equals(XClass paramXClass, Class<T> paramClass);
  
  public abstract AnnotationReader buildAnnotationReader(AnnotatedElement paramAnnotatedElement);
  
  public abstract Map getDefaults();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.reflection.ReflectionManager
 * JD-Core Version:    0.7.0.1
 */