package org.hibernate.persister.collection;

public abstract interface SQLLoadableCollection
  extends QueryableCollection
{
  public abstract String[] getCollectionPropertyColumnAliases(String paramString1, String paramString2);
  
  public abstract String getIdentifierColumnName();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.collection.SQLLoadableCollection
 * JD-Core Version:    0.7.0.1
 */