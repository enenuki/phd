package org.cyberneko.html;

import org.apache.xerces.xni.parser.XMLParseException;

public abstract interface HTMLErrorReporter
{
  public abstract String formatMessage(String paramString, Object[] paramArrayOfObject);
  
  public abstract void reportWarning(String paramString, Object[] paramArrayOfObject)
    throws XMLParseException;
  
  public abstract void reportError(String paramString, Object[] paramArrayOfObject)
    throws XMLParseException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.cyberneko.html.HTMLErrorReporter
 * JD-Core Version:    0.7.0.1
 */