package org.hibernate.property;

import org.hibernate.PropertyNotFoundException;

public abstract interface PropertyAccessor
{
  public abstract Getter getGetter(Class paramClass, String paramString)
    throws PropertyNotFoundException;
  
  public abstract Setter getSetter(Class paramClass, String paramString)
    throws PropertyNotFoundException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.property.PropertyAccessor
 * JD-Core Version:    0.7.0.1
 */