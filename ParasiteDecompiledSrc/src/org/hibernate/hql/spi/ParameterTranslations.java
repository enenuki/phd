package org.hibernate.hql.spi;

import java.util.Set;
import org.hibernate.type.Type;

public abstract interface ParameterTranslations
{
  public abstract boolean supportsOrdinalParameterMetadata();
  
  public abstract int getOrdinalParameterCount();
  
  public abstract int getOrdinalParameterSqlLocation(int paramInt);
  
  public abstract Type getOrdinalParameterExpectedType(int paramInt);
  
  public abstract Set getNamedParameterNames();
  
  public abstract int[] getNamedParameterSqlLocations(String paramString);
  
  public abstract Type getNamedParameterExpectedType(String paramString);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.spi.ParameterTranslations
 * JD-Core Version:    0.7.0.1
 */