package org.hibernate.id;

import java.util.Properties;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.type.Type;

public abstract interface Configurable
{
  public abstract void configure(Type paramType, Properties paramProperties, Dialect paramDialect)
    throws MappingException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.Configurable
 * JD-Core Version:    0.7.0.1
 */