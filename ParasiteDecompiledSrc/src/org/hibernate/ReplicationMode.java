package org.hibernate;

import java.util.Comparator;
import org.hibernate.type.VersionType;

public enum ReplicationMode
{
  EXCEPTION,  IGNORE,  OVERWRITE,  LATEST_VERSION;
  
  private ReplicationMode() {}
  
  public abstract boolean shouldOverwriteCurrentVersion(Object paramObject1, Object paramObject2, Object paramObject3, VersionType paramVersionType);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.ReplicationMode
 * JD-Core Version:    0.7.0.1
 */