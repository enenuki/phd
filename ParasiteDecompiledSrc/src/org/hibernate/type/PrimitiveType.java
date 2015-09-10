package org.hibernate.type;

import java.io.Serializable;

public abstract interface PrimitiveType<T>
  extends LiteralType<T>
{
  public abstract Class getPrimitiveClass();
  
  public abstract String toString(T paramT);
  
  public abstract Serializable getDefaultValue();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.PrimitiveType
 * JD-Core Version:    0.7.0.1
 */