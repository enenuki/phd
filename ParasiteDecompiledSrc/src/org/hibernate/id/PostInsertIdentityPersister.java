package org.hibernate.id;

import org.hibernate.persister.entity.EntityPersister;

public abstract interface PostInsertIdentityPersister
  extends EntityPersister
{
  public abstract String getSelectByUniqueKeyString(String paramString);
  
  public abstract String getIdentitySelectString();
  
  public abstract String[] getRootTableKeyColumnNames();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.PostInsertIdentityPersister
 * JD-Core Version:    0.7.0.1
 */