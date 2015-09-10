package org.hibernate.bytecode.buildtime.spi;

public abstract interface FieldFilter
{
  public abstract boolean shouldInstrumentField(String paramString1, String paramString2);
  
  public abstract boolean shouldTransformFieldAccess(String paramString1, String paramString2, String paramString3);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.buildtime.spi.FieldFilter
 * JD-Core Version:    0.7.0.1
 */