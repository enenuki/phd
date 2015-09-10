package org.hibernate.loader;

public abstract interface CollectionAliases
{
  public abstract String[] getSuffixedKeyAliases();
  
  public abstract String[] getSuffixedIndexAliases();
  
  public abstract String[] getSuffixedElementAliases();
  
  public abstract String getSuffixedIdentifierAlias();
  
  public abstract String getSuffix();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.CollectionAliases
 * JD-Core Version:    0.7.0.1
 */