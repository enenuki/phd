package org.hibernate.stat.spi;

import org.hibernate.engine.spi.SessionFactoryImplementor;

public abstract interface StatisticsFactory
{
  public abstract StatisticsImplementor buildStatistics(SessionFactoryImplementor paramSessionFactoryImplementor);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.stat.spi.StatisticsFactory
 * JD-Core Version:    0.7.0.1
 */