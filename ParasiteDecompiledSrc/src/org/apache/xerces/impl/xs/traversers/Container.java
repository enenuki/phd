package org.apache.xerces.impl.xs.traversers;

abstract class Container
{
  static final int THRESHOLD = 5;
  OneAttr[] values;
  int pos = 0;
  
  static Container getContainer(int paramInt)
  {
    if (paramInt > 5) {
      return new LargeContainer(paramInt);
    }
    return new SmallContainer(paramInt);
  }
  
  abstract void put(String paramString, OneAttr paramOneAttr);
  
  abstract OneAttr get(String paramString);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.traversers.Container
 * JD-Core Version:    0.7.0.1
 */