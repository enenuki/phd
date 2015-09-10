package org.apache.xpath;

import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xpath.functions.FuncExtFunction;

public abstract interface ExtensionsProvider
{
  public abstract boolean functionAvailable(String paramString1, String paramString2)
    throws TransformerException;
  
  public abstract boolean elementAvailable(String paramString1, String paramString2)
    throws TransformerException;
  
  public abstract Object extFunction(String paramString1, String paramString2, Vector paramVector, Object paramObject)
    throws TransformerException;
  
  public abstract Object extFunction(FuncExtFunction paramFuncExtFunction, Vector paramVector)
    throws TransformerException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.ExtensionsProvider
 * JD-Core Version:    0.7.0.1
 */