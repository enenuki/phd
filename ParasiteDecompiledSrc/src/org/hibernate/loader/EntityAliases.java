package org.hibernate.loader;

import org.hibernate.persister.entity.Loadable;

public abstract interface EntityAliases
{
  public abstract String[] getSuffixedKeyAliases();
  
  public abstract String getSuffixedDiscriminatorAlias();
  
  public abstract String[] getSuffixedVersionAliases();
  
  public abstract String[][] getSuffixedPropertyAliases();
  
  public abstract String[][] getSuffixedPropertyAliases(Loadable paramLoadable);
  
  public abstract String getRowIdAlias();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.EntityAliases
 * JD-Core Version:    0.7.0.1
 */