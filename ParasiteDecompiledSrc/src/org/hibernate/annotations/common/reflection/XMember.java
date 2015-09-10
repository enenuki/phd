package org.hibernate.annotations.common.reflection;

import java.util.Collection;

public abstract interface XMember
  extends XAnnotatedElement
{
  public abstract String getName();
  
  public abstract boolean isCollection();
  
  public abstract boolean isArray();
  
  public abstract Class<? extends Collection> getCollectionClass();
  
  public abstract XClass getType();
  
  public abstract XClass getElementClass();
  
  public abstract XClass getClassOrElementClass();
  
  public abstract XClass getMapKey();
  
  public abstract int getModifiers();
  
  public abstract void setAccessible(boolean paramBoolean);
  
  public abstract Object invoke(Object paramObject, Object... paramVarArgs);
  
  public abstract boolean isTypeResolved();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.reflection.XMember
 * JD-Core Version:    0.7.0.1
 */