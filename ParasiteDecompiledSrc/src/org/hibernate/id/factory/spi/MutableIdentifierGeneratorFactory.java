package org.hibernate.id.factory.spi;

import org.hibernate.id.factory.IdentifierGeneratorFactory;
import org.hibernate.service.Service;

public abstract interface MutableIdentifierGeneratorFactory
  extends IdentifierGeneratorFactory, Service
{
  public abstract void register(String paramString, Class paramClass);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.factory.spi.MutableIdentifierGeneratorFactory
 * JD-Core Version:    0.7.0.1
 */