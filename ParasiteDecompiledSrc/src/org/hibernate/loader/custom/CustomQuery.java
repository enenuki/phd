package org.hibernate.loader.custom;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract interface CustomQuery
{
  public abstract String getSQL();
  
  public abstract Set getQuerySpaces();
  
  public abstract Map getNamedParameterBindPoints();
  
  public abstract List getCustomQueryReturns();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.custom.CustomQuery
 * JD-Core Version:    0.7.0.1
 */