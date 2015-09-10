package org.hibernate.engine.spi;

import org.hibernate.MappingException;
import org.hibernate.id.factory.IdentifierGeneratorFactory;
import org.hibernate.type.Type;

public abstract interface Mapping
{
  /**
   * @deprecated
   */
  public abstract IdentifierGeneratorFactory getIdentifierGeneratorFactory();
  
  public abstract Type getIdentifierType(String paramString)
    throws MappingException;
  
  public abstract String getIdentifierPropertyName(String paramString)
    throws MappingException;
  
  public abstract Type getReferencedPropertyType(String paramString1, String paramString2)
    throws MappingException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.spi.Mapping
 * JD-Core Version:    0.7.0.1
 */