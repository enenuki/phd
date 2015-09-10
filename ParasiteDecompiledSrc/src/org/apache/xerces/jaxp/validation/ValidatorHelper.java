package org.apache.xerces.jaxp.validation;

import java.io.IOException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import org.xml.sax.SAXException;

abstract interface ValidatorHelper
{
  public abstract void validate(Source paramSource, Result paramResult)
    throws SAXException, IOException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.jaxp.validation.ValidatorHelper
 * JD-Core Version:    0.7.0.1
 */