package org.hibernate.persister.collection;

import org.hibernate.FetchMode;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.persister.entity.Joinable;
import org.hibernate.persister.entity.PropertyMapping;

public abstract interface QueryableCollection
  extends PropertyMapping, Joinable, CollectionPersister
{
  public abstract String selectFragment(String paramString1, String paramString2);
  
  public abstract String[] getIndexColumnNames();
  
  public abstract String[] getIndexFormulas();
  
  public abstract String[] getIndexColumnNames(String paramString);
  
  public abstract String[] getElementColumnNames(String paramString);
  
  public abstract String[] getElementColumnNames();
  
  public abstract String getSQLOrderByString(String paramString);
  
  public abstract String getManyToManyOrderByString(String paramString);
  
  public abstract boolean hasWhere();
  
  public abstract EntityPersister getElementPersister();
  
  public abstract FetchMode getFetchMode();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.collection.QueryableCollection
 * JD-Core Version:    0.7.0.1
 */