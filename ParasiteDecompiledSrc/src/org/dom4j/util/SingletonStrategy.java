package org.dom4j.util;

public abstract interface SingletonStrategy
{
  public abstract Object instance();
  
  public abstract void reset();
  
  public abstract void setSingletonClassName(String paramString);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.util.SingletonStrategy
 * JD-Core Version:    0.7.0.1
 */