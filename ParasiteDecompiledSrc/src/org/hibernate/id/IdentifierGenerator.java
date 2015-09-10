package org.hibernate.id;

import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;

public abstract interface IdentifierGenerator
{
  public static final String ENTITY_NAME = "entity_name";
  
  public abstract Serializable generate(SessionImplementor paramSessionImplementor, Object paramObject)
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.IdentifierGenerator
 * JD-Core Version:    0.7.0.1
 */