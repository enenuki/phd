package javax.xml.transform.sax;

import javax.xml.transform.Templates;
import org.xml.sax.ContentHandler;

public abstract interface TemplatesHandler
  extends ContentHandler
{
  public abstract Templates getTemplates();
  
  public abstract void setSystemId(String paramString);
  
  public abstract String getSystemId();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.xml.transform.sax.TemplatesHandler
 * JD-Core Version:    0.7.0.1
 */