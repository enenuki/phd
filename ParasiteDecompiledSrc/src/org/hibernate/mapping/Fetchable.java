package org.hibernate.mapping;

import org.hibernate.FetchMode;

public abstract interface Fetchable
{
  public abstract FetchMode getFetchMode();
  
  public abstract void setFetchMode(FetchMode paramFetchMode);
  
  public abstract boolean isLazy();
  
  public abstract void setLazy(boolean paramBoolean);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.Fetchable
 * JD-Core Version:    0.7.0.1
 */