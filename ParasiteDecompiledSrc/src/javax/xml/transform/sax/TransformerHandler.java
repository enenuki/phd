package javax.xml.transform.sax;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.ext.LexicalHandler;

public abstract interface TransformerHandler
  extends ContentHandler, LexicalHandler, DTDHandler
{
  public abstract void setResult(Result paramResult)
    throws IllegalArgumentException;
  
  public abstract void setSystemId(String paramString);
  
  public abstract String getSystemId();
  
  public abstract Transformer getTransformer();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.xml.transform.sax.TransformerHandler
 * JD-Core Version:    0.7.0.1
 */