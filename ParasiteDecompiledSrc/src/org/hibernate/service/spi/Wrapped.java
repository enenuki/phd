package org.hibernate.service.spi;

public abstract interface Wrapped
{
  public abstract boolean isUnwrappableAs(Class paramClass);
  
  public abstract <T> T unwrap(Class<T> paramClass);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.spi.Wrapped
 * JD-Core Version:    0.7.0.1
 */