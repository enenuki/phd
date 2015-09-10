package org.hibernate.persister.entity;

import java.util.Map;
import org.hibernate.MappingException;

public abstract interface Joinable
{
  public abstract String getName();
  
  public abstract String getTableName();
  
  public abstract String selectFragment(Joinable paramJoinable, String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean);
  
  public abstract String whereJoinFragment(String paramString, boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract String fromJoinFragment(String paramString, boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract String[] getKeyColumnNames();
  
  public abstract String filterFragment(String paramString, Map paramMap)
    throws MappingException;
  
  public abstract String oneToManyFilterFragment(String paramString)
    throws MappingException;
  
  public abstract boolean isCollection();
  
  public abstract boolean consumesEntityAlias();
  
  public abstract boolean consumesCollectionAlias();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.entity.Joinable
 * JD-Core Version:    0.7.0.1
 */