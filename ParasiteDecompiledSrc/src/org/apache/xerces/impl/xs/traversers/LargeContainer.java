package org.apache.xerces.impl.xs.traversers;

import java.util.Hashtable;

class LargeContainer
  extends Container
{
  Hashtable items;
  
  LargeContainer(int paramInt)
  {
    this.items = new Hashtable(paramInt * 2 + 1);
    this.values = new OneAttr[paramInt];
  }
  
  void put(String paramString, OneAttr paramOneAttr)
  {
    this.items.put(paramString, paramOneAttr);
    this.values[(this.pos++)] = paramOneAttr;
  }
  
  OneAttr get(String paramString)
  {
    OneAttr localOneAttr = (OneAttr)this.items.get(paramString);
    return localOneAttr;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.traversers.LargeContainer
 * JD-Core Version:    0.7.0.1
 */