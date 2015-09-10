package org.dom4j.jaxb;

import javax.xml.bind.Element;

public abstract interface JAXBObjectModifier
{
  public abstract Element modifyObject(Element paramElement)
    throws Exception;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.jaxb.JAXBObjectModifier
 * JD-Core Version:    0.7.0.1
 */