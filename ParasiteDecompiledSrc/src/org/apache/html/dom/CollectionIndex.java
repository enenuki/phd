package org.apache.html.dom;

class CollectionIndex
{
  private int _index;
  
  int getIndex()
  {
    return this._index;
  }
  
  void decrement()
  {
    this._index -= 1;
  }
  
  boolean isZero()
  {
    return this._index <= 0;
  }
  
  CollectionIndex(int paramInt)
  {
    this._index = paramInt;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.html.dom.CollectionIndex
 * JD-Core Version:    0.7.0.1
 */