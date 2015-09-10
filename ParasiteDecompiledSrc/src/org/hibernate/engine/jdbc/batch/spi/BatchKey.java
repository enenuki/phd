package org.hibernate.engine.jdbc.batch.spi;

import org.hibernate.jdbc.Expectation;

public abstract interface BatchKey
{
  public abstract int getBatchedStatementCount();
  
  public abstract Expectation getExpectation();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.batch.spi.BatchKey
 * JD-Core Version:    0.7.0.1
 */