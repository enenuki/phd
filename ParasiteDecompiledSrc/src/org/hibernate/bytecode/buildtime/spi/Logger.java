package org.hibernate.bytecode.buildtime.spi;

public abstract interface Logger
{
  public abstract void trace(String paramString);
  
  public abstract void debug(String paramString);
  
  public abstract void info(String paramString);
  
  public abstract void warn(String paramString);
  
  public abstract void error(String paramString);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.buildtime.spi.Logger
 * JD-Core Version:    0.7.0.1
 */