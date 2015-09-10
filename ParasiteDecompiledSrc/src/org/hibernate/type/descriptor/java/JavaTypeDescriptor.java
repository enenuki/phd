package org.hibernate.type.descriptor.java;

import java.io.Serializable;
import java.util.Comparator;
import org.hibernate.type.descriptor.WrapperOptions;

public abstract interface JavaTypeDescriptor<T>
  extends Serializable
{
  public abstract Class<T> getJavaTypeClass();
  
  public abstract MutabilityPlan<T> getMutabilityPlan();
  
  public abstract Comparator<T> getComparator();
  
  public abstract int extractHashCode(T paramT);
  
  public abstract boolean areEqual(T paramT1, T paramT2);
  
  public abstract String extractLoggableRepresentation(T paramT);
  
  public abstract String toString(T paramT);
  
  public abstract T fromString(String paramString);
  
  public abstract <X> X unwrap(T paramT, Class<X> paramClass, WrapperOptions paramWrapperOptions);
  
  public abstract <X> T wrap(X paramX, WrapperOptions paramWrapperOptions);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.JavaTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */