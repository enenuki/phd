package org.hibernate.persister.entity;

public abstract interface Lockable
  extends EntityPersister
{
  public abstract String getRootTableName();
  
  public abstract String getRootTableAlias(String paramString);
  
  public abstract String[] getRootTableIdentifierColumnNames();
  
  public abstract String getVersionColumnName();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.entity.Lockable
 * JD-Core Version:    0.7.0.1
 */