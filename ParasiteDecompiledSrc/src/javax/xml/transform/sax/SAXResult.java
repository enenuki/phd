package javax.xml.transform.sax;

import javax.xml.transform.Result;
import org.xml.sax.ContentHandler;
import org.xml.sax.ext.LexicalHandler;

public class SAXResult
  implements Result
{
  public static final String FEATURE = "http://javax.xml.transform.sax.SAXResult/feature";
  private ContentHandler handler;
  private LexicalHandler lexhandler;
  private String systemId;
  
  public SAXResult() {}
  
  public SAXResult(ContentHandler paramContentHandler)
  {
    setHandler(paramContentHandler);
  }
  
  public void setHandler(ContentHandler paramContentHandler)
  {
    this.handler = paramContentHandler;
  }
  
  public ContentHandler getHandler()
  {
    return this.handler;
  }
  
  public void setLexicalHandler(LexicalHandler paramLexicalHandler)
  {
    this.lexhandler = paramLexicalHandler;
  }
  
  public LexicalHandler getLexicalHandler()
  {
    return this.lexhandler;
  }
  
  public void setSystemId(String paramString)
  {
    this.systemId = paramString;
  }
  
  public String getSystemId()
  {
    return this.systemId;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.xml.transform.sax.SAXResult
 * JD-Core Version:    0.7.0.1
 */