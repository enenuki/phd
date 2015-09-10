package org.apache.xml.dtm;

public abstract interface DTMAxisIterator
  extends Cloneable
{
  public static final int END = -1;
  
  public abstract int next();
  
  public abstract DTMAxisIterator reset();
  
  public abstract int getLast();
  
  public abstract int getPosition();
  
  public abstract void setMark();
  
  public abstract void gotoMark();
  
  public abstract DTMAxisIterator setStartNode(int paramInt);
  
  public abstract int getStartNode();
  
  public abstract boolean isReverse();
  
  public abstract DTMAxisIterator cloneIterator();
  
  public abstract void setRestartable(boolean paramBoolean);
  
  public abstract int getNodeByPosition(int paramInt);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.DTMAxisIterator
 * JD-Core Version:    0.7.0.1
 */