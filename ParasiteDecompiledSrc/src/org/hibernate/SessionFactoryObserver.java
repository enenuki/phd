package org.hibernate;

import java.io.Serializable;

public abstract interface SessionFactoryObserver
  extends Serializable
{
  public abstract void sessionFactoryCreated(SessionFactory paramSessionFactory);
  
  public abstract void sessionFactoryClosed(SessionFactory paramSessionFactory);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.SessionFactoryObserver
 * JD-Core Version:    0.7.0.1
 */