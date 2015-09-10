package org.hibernate;

import java.util.Properties;
import org.hibernate.type.BasicType;
import org.hibernate.type.Type;

public abstract interface TypeHelper
{
  public abstract BasicType basic(String paramString);
  
  public abstract BasicType basic(Class paramClass);
  
  public abstract Type heuristicType(String paramString);
  
  public abstract Type entity(Class paramClass);
  
  public abstract Type entity(String paramString);
  
  public abstract Type custom(Class paramClass);
  
  public abstract Type custom(Class paramClass, Properties paramProperties);
  
  public abstract Type any(Type paramType1, Type paramType2);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.TypeHelper
 * JD-Core Version:    0.7.0.1
 */