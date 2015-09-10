package org.hibernate.type;

import java.util.Comparator;
import org.hibernate.engine.spi.SessionImplementor;

public abstract interface VersionType<T>
  extends Type
{
  public abstract T seed(SessionImplementor paramSessionImplementor);
  
  public abstract T next(T paramT, SessionImplementor paramSessionImplementor);
  
  public abstract Comparator<T> getComparator();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.VersionType
 * JD-Core Version:    0.7.0.1
 */