package org.hibernate.engine.jdbc.batch.spi;

import java.sql.PreparedStatement;

public abstract interface Batch
{
  public abstract BatchKey getKey();
  
  public abstract void addObserver(BatchObserver paramBatchObserver);
  
  public abstract PreparedStatement getBatchStatement(String paramString, boolean paramBoolean);
  
  public abstract void addToBatch();
  
  public abstract void execute();
  
  public abstract void release();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.batch.spi.Batch
 * JD-Core Version:    0.7.0.1
 */