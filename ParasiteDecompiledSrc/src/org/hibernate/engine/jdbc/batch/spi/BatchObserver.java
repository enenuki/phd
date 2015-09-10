package org.hibernate.engine.jdbc.batch.spi;

public abstract interface BatchObserver
{
  public abstract void batchExplicitlyExecuted();
  
  public abstract void batchImplicitlyExecuted();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.batch.spi.BatchObserver
 * JD-Core Version:    0.7.0.1
 */