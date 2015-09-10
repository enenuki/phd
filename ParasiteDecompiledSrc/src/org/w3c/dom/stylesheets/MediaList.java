package org.w3c.dom.stylesheets;

import org.w3c.dom.DOMException;

public abstract interface MediaList
{
  public abstract String getMediaText();
  
  public abstract void setMediaText(String paramString)
    throws DOMException;
  
  public abstract int getLength();
  
  public abstract String item(int paramInt);
  
  public abstract void deleteMedium(String paramString)
    throws DOMException;
  
  public abstract void appendMedium(String paramString)
    throws DOMException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.w3c.dom.stylesheets.MediaList
 * JD-Core Version:    0.7.0.1
 */