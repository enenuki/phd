package org.hibernate.annotations.common.reflection.java.generics;

import java.lang.reflect.Type;

public abstract interface TypeEnvironment
{
  public abstract Type bind(Type paramType);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.reflection.java.generics.TypeEnvironment
 * JD-Core Version:    0.7.0.1
 */