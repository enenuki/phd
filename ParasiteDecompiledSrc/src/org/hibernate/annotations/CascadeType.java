package org.hibernate.annotations;

public enum CascadeType
{
  ALL,  PERSIST,  MERGE,  REMOVE,  REFRESH,  DELETE,  SAVE_UPDATE,  REPLICATE,  DELETE_ORPHAN,  LOCK,  EVICT,  DETACH;
  
  private CascadeType() {}
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.CascadeType
 * JD-Core Version:    0.7.0.1
 */