package org.hibernate.type.descriptor.java;

import java.io.Serializable;

public abstract interface MutabilityPlan<T>
  extends Serializable
{
  public abstract boolean isMutable();
  
  public abstract T deepCopy(T paramT);
  
  public abstract Serializable disassemble(T paramT);
  
  public abstract T assemble(Serializable paramSerializable);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.MutabilityPlan
 * JD-Core Version:    0.7.0.1
 */