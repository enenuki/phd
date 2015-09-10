package org.hibernate.metadata;

import org.hibernate.type.Type;

public abstract interface CollectionMetadata
{
  public abstract Type getKeyType();
  
  public abstract Type getElementType();
  
  public abstract Type getIndexType();
  
  public abstract boolean hasIndex();
  
  public abstract String getRole();
  
  public abstract boolean isArray();
  
  public abstract boolean isPrimitiveArray();
  
  public abstract boolean isLazy();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metadata.CollectionMetadata
 * JD-Core Version:    0.7.0.1
 */