package org.hibernate.persister.entity;

import org.hibernate.type.Type;

public abstract interface DiscriminatorMetadata
{
  public abstract String getSqlFragment(String paramString);
  
  public abstract Type getResolutionType();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.entity.DiscriminatorMetadata
 * JD-Core Version:    0.7.0.1
 */