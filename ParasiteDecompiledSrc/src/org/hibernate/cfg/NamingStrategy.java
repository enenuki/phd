package org.hibernate.cfg;

public abstract interface NamingStrategy
{
  public abstract String classToTableName(String paramString);
  
  public abstract String propertyToColumnName(String paramString);
  
  public abstract String tableName(String paramString);
  
  public abstract String columnName(String paramString);
  
  public abstract String collectionTableName(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);
  
  public abstract String joinKeyColumnName(String paramString1, String paramString2);
  
  public abstract String foreignKeyColumnName(String paramString1, String paramString2, String paramString3, String paramString4);
  
  public abstract String logicalColumnName(String paramString1, String paramString2);
  
  public abstract String logicalCollectionTableName(String paramString1, String paramString2, String paramString3, String paramString4);
  
  public abstract String logicalCollectionColumnName(String paramString1, String paramString2, String paramString3);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.NamingStrategy
 * JD-Core Version:    0.7.0.1
 */