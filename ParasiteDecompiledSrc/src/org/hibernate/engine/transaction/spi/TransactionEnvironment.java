package org.hibernate.engine.transaction.spi;

import org.hibernate.engine.jdbc.spi.JdbcServices;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.service.jta.platform.spi.JtaPlatform;
import org.hibernate.stat.spi.StatisticsImplementor;

public abstract interface TransactionEnvironment
{
  public abstract SessionFactoryImplementor getSessionFactory();
  
  public abstract JdbcServices getJdbcServices();
  
  public abstract JtaPlatform getJtaPlatform();
  
  public abstract TransactionFactory getTransactionFactory();
  
  public abstract StatisticsImplementor getStatisticsImplementor();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.transaction.spi.TransactionEnvironment
 * JD-Core Version:    0.7.0.1
 */