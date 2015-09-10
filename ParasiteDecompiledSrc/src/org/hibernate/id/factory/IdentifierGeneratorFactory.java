package org.hibernate.id.factory;

import java.util.Properties;
import org.hibernate.dialect.Dialect;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.type.Type;

public abstract interface IdentifierGeneratorFactory
{
  public abstract Dialect getDialect();
  
  /**
   * @deprecated
   */
  public abstract void setDialect(Dialect paramDialect);
  
  public abstract IdentifierGenerator createIdentifierGenerator(String paramString, Type paramType, Properties paramProperties);
  
  public abstract Class getIdentifierGeneratorClass(String paramString);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.factory.IdentifierGeneratorFactory
 * JD-Core Version:    0.7.0.1
 */