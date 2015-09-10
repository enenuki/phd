package org.hibernate.engine.jdbc.batch.spi;

import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
import org.hibernate.service.Service;
import org.hibernate.service.spi.Manageable;

public abstract interface BatchBuilder
  extends Service, Manageable
{
  public abstract Batch buildBatch(BatchKey paramBatchKey, JdbcCoordinator paramJdbcCoordinator);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.batch.spi.BatchBuilder
 * JD-Core Version:    0.7.0.1
 */