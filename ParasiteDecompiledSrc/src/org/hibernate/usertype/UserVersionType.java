package org.hibernate.usertype;

import java.util.Comparator;
import org.hibernate.engine.spi.SessionImplementor;

public abstract interface UserVersionType
  extends UserType, Comparator
{
  public abstract Object seed(SessionImplementor paramSessionImplementor);
  
  public abstract Object next(Object paramObject, SessionImplementor paramSessionImplementor);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.usertype.UserVersionType
 * JD-Core Version:    0.7.0.1
 */