package org.hibernate.engine.spi;

public abstract interface UnsavedValueStrategy
{
  public abstract Boolean isUnsaved(Object paramObject);
  
  public abstract Object getDefaultValue(Object paramObject);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.spi.UnsavedValueStrategy
 * JD-Core Version:    0.7.0.1
 */