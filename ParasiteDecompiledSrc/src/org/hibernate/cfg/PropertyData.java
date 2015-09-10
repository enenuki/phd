package org.hibernate.cfg;

import org.hibernate.MappingException;
import org.hibernate.annotations.common.reflection.XClass;
import org.hibernate.annotations.common.reflection.XProperty;

public abstract interface PropertyData
{
  public abstract AccessType getDefaultAccess();
  
  public abstract String getPropertyName()
    throws MappingException;
  
  public abstract XClass getClassOrElement()
    throws MappingException;
  
  public abstract XClass getPropertyClass()
    throws MappingException;
  
  public abstract String getClassOrElementName()
    throws MappingException;
  
  public abstract String getTypeName()
    throws MappingException;
  
  public abstract XProperty getProperty();
  
  public abstract XClass getDeclaringClass();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.PropertyData
 * JD-Core Version:    0.7.0.1
 */