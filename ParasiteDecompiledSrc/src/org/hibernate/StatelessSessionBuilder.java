package org.hibernate;

import java.sql.Connection;

public abstract interface StatelessSessionBuilder
{
  public abstract StatelessSession openStatelessSession();
  
  public abstract StatelessSessionBuilder connection(Connection paramConnection);
  
  public abstract StatelessSessionBuilder tenantIdentifier(String paramString);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.StatelessSessionBuilder
 * JD-Core Version:    0.7.0.1
 */