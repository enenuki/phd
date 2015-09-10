package org.hibernate.bytecode.internal.javassist;

public abstract interface FieldFilter
{
  public abstract boolean handleRead(String paramString1, String paramString2);
  
  public abstract boolean handleWrite(String paramString1, String paramString2);
  
  public abstract boolean handleReadAccess(String paramString1, String paramString2);
  
  public abstract boolean handleWriteAccess(String paramString1, String paramString2);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.internal.javassist.FieldFilter
 * JD-Core Version:    0.7.0.1
 */