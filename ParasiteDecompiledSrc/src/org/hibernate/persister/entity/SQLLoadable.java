package org.hibernate.persister.entity;

import org.hibernate.type.Type;

public abstract interface SQLLoadable
  extends Loadable
{
  public abstract String[] getSubclassPropertyColumnAliases(String paramString1, String paramString2);
  
  public abstract String[] getSubclassPropertyColumnNames(String paramString);
  
  public abstract String selectFragment(String paramString1, String paramString2);
  
  public abstract Type getType();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.entity.SQLLoadable
 * JD-Core Version:    0.7.0.1
 */