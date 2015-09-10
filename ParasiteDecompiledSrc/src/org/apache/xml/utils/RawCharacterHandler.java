package org.apache.xml.utils;

import javax.xml.transform.TransformerException;

public abstract interface RawCharacterHandler
{
  public abstract void charactersRaw(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws TransformerException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.RawCharacterHandler
 * JD-Core Version:    0.7.0.1
 */