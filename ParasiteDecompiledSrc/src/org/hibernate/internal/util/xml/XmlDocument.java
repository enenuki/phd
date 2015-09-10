package org.hibernate.internal.util.xml;

import java.io.Serializable;
import org.dom4j.Document;

public abstract interface XmlDocument
  extends Serializable
{
  public abstract Document getDocumentTree();
  
  public abstract Origin getOrigin();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.xml.XmlDocument
 * JD-Core Version:    0.7.0.1
 */