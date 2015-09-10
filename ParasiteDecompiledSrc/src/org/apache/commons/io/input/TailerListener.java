package org.apache.commons.io.input;

public abstract interface TailerListener
{
  public abstract void init(Tailer paramTailer);
  
  public abstract void fileNotFound();
  
  public abstract void fileRotated();
  
  public abstract void handle(String paramString);
  
  public abstract void handle(Exception paramException);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.input.TailerListener
 * JD-Core Version:    0.7.0.1
 */