package org.apache.xerces.xni;

import java.util.Enumeration;

public abstract interface Augmentations
{
  public abstract Object putItem(String paramString, Object paramObject);
  
  public abstract Object getItem(String paramString);
  
  public abstract Object removeItem(String paramString);
  
  public abstract Enumeration keys();
  
  public abstract void removeAllItems();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xni.Augmentations
 * JD-Core Version:    0.7.0.1
 */