package javax.xml.transform.dom;

import javax.xml.transform.SourceLocator;
import org.w3c.dom.Node;

public abstract interface DOMLocator
  extends SourceLocator
{
  public abstract Node getOriginatingNode();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.xml.transform.dom.DOMLocator
 * JD-Core Version:    0.7.0.1
 */