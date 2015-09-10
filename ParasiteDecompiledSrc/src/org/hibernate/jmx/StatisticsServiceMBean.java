package org.hibernate.jmx;

import org.hibernate.stat.Statistics;

@Deprecated
public abstract interface StatisticsServiceMBean
  extends Statistics
{
  public abstract void setSessionFactoryJNDIName(String paramString);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.jmx.StatisticsServiceMBean
 * JD-Core Version:    0.7.0.1
 */