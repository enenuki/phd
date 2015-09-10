package org.hibernate.metamodel.source.binder;

public abstract interface JpaCallbackClass
{
  public abstract String getCallbackMethod(Class<?> paramClass);
  
  public abstract String getName();
  
  public abstract boolean isListener();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.binder.JpaCallbackClass
 * JD-Core Version:    0.7.0.1
 */