package org.apache.xerces.impl.xs.traversers;

class SmallContainer
  extends Container
{
  String[] keys;
  
  SmallContainer(int paramInt)
  {
    this.keys = new String[paramInt];
    this.values = new OneAttr[paramInt];
  }
  
  void put(String paramString, OneAttr paramOneAttr)
  {
    this.keys[this.pos] = paramString;
    this.values[(this.pos++)] = paramOneAttr;
  }
  
  OneAttr get(String paramString)
  {
    for (int i = 0; i < this.pos; i++) {
      if (this.keys[i].equals(paramString)) {
        return this.values[i];
      }
    }
    return null;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.traversers.SmallContainer
 * JD-Core Version:    0.7.0.1
 */