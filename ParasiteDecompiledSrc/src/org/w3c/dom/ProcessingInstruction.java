package org.w3c.dom;

public abstract interface ProcessingInstruction
  extends Node
{
  public abstract String getTarget();
  
  public abstract String getData();
  
  public abstract void setData(String paramString)
    throws DOMException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.w3c.dom.ProcessingInstruction
 * JD-Core Version:    0.7.0.1
 */