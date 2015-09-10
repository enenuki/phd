package org.hibernate.mapping;

import java.util.Map;

public abstract interface Filterable
{
  public abstract void addFilter(String paramString1, String paramString2);
  
  public abstract Map getFilterMap();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.Filterable
 * JD-Core Version:    0.7.0.1
 */