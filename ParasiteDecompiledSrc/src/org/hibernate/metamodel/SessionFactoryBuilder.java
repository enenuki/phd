package org.hibernate.metamodel;

import org.hibernate.Interceptor;
import org.hibernate.SessionFactory;
import org.hibernate.proxy.EntityNotFoundDelegate;

public abstract interface SessionFactoryBuilder
{
  public abstract SessionFactoryBuilder with(Interceptor paramInterceptor);
  
  public abstract SessionFactoryBuilder with(EntityNotFoundDelegate paramEntityNotFoundDelegate);
  
  public abstract SessionFactory buildSessionFactory();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.SessionFactoryBuilder
 * JD-Core Version:    0.7.0.1
 */