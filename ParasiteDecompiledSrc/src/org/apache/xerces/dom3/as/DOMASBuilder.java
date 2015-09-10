package org.apache.xerces.dom3.as;

import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSParser;

/**
 * @deprecated
 */
public abstract interface DOMASBuilder
  extends LSParser
{
  public abstract ASModel getAbstractSchema();
  
  public abstract void setAbstractSchema(ASModel paramASModel);
  
  public abstract ASModel parseASURI(String paramString)
    throws DOMASException, Exception;
  
  public abstract ASModel parseASInputSource(LSInput paramLSInput)
    throws DOMASException, Exception;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.dom3.as.DOMASBuilder
 * JD-Core Version:    0.7.0.1
 */