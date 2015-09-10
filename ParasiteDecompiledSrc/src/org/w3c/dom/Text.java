package org.w3c.dom;

public abstract interface Text
  extends CharacterData
{
  public abstract Text splitText(int paramInt)
    throws DOMException;
  
  public abstract boolean isElementContentWhitespace();
  
  public abstract String getWholeText();
  
  public abstract Text replaceWholeText(String paramString)
    throws DOMException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.w3c.dom.Text
 * JD-Core Version:    0.7.0.1
 */