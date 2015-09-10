package org.hibernate.tuple;

import org.hibernate.property.Getter;

public abstract interface Tuplizer
{
  public abstract Object[] getPropertyValues(Object paramObject);
  
  public abstract void setPropertyValues(Object paramObject, Object[] paramArrayOfObject);
  
  public abstract Object getPropertyValue(Object paramObject, int paramInt);
  
  public abstract Object instantiate();
  
  public abstract boolean isInstance(Object paramObject);
  
  public abstract Class getMappedClass();
  
  public abstract Getter getGetter(int paramInt);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tuple.Tuplizer
 * JD-Core Version:    0.7.0.1
 */