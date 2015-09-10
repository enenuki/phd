package org.hibernate;

import java.io.Serializable;

public abstract interface Cache
{
  public abstract boolean containsEntity(Class paramClass, Serializable paramSerializable);
  
  public abstract boolean containsEntity(String paramString, Serializable paramSerializable);
  
  public abstract void evictEntity(Class paramClass, Serializable paramSerializable);
  
  public abstract void evictEntity(String paramString, Serializable paramSerializable);
  
  public abstract void evictEntityRegion(Class paramClass);
  
  public abstract void evictEntityRegion(String paramString);
  
  public abstract void evictEntityRegions();
  
  public abstract boolean containsCollection(String paramString, Serializable paramSerializable);
  
  public abstract void evictCollection(String paramString, Serializable paramSerializable);
  
  public abstract void evictCollectionRegion(String paramString);
  
  public abstract void evictCollectionRegions();
  
  public abstract boolean containsQuery(String paramString);
  
  public abstract void evictDefaultQueryRegion();
  
  public abstract void evictQueryRegion(String paramString);
  
  public abstract void evictQueryRegions();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.Cache
 * JD-Core Version:    0.7.0.1
 */