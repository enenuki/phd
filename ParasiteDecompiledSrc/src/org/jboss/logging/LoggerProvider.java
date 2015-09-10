package org.jboss.logging;

import java.util.Map;

public abstract interface LoggerProvider
{
  public abstract Logger getLogger(String paramString);
  
  public abstract Object putMdc(String paramString, Object paramObject);
  
  public abstract Object getMdc(String paramString);
  
  public abstract void removeMdc(String paramString);
  
  public abstract Map<String, Object> getMdcMap();
  
  public abstract void clearNdc();
  
  public abstract String getNdc();
  
  public abstract int getNdcDepth();
  
  public abstract String popNdc();
  
  public abstract String peekNdc();
  
  public abstract void pushNdc(String paramString);
  
  public abstract void setNdcMaxDepth(int paramInt);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.logging.LoggerProvider
 * JD-Core Version:    0.7.0.1
 */