package org.hibernate.usertype;

import org.hibernate.engine.spi.SessionFactoryImplementor;

public abstract interface LoggableUserType
{
  public abstract String toLoggableString(Object paramObject, SessionFactoryImplementor paramSessionFactoryImplementor);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.usertype.LoggableUserType
 * JD-Core Version:    0.7.0.1
 */