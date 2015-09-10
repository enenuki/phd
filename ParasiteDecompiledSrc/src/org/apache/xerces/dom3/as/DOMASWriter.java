package org.apache.xerces.dom3.as;

import java.io.OutputStream;
import org.w3c.dom.ls.LSSerializer;

/**
 * @deprecated
 */
public abstract interface DOMASWriter
  extends LSSerializer
{
  public abstract void writeASModel(OutputStream paramOutputStream, ASModel paramASModel)
    throws Exception;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.dom3.as.DOMASWriter
 * JD-Core Version:    0.7.0.1
 */