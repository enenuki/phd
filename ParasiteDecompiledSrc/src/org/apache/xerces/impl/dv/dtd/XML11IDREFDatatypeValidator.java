package org.apache.xerces.impl.dv.dtd;

import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidationContext;
import org.apache.xerces.util.XML11Char;

public class XML11IDREFDatatypeValidator
  extends IDREFDatatypeValidator
{
  public void validate(String paramString, ValidationContext paramValidationContext)
    throws InvalidDatatypeValueException
  {
    if (paramValidationContext.useNamespaces())
    {
      if (!XML11Char.isXML11ValidNCName(paramString)) {
        throw new InvalidDatatypeValueException("IDREFInvalidWithNamespaces", new Object[] { paramString });
      }
    }
    else if (!XML11Char.isXML11ValidName(paramString)) {
      throw new InvalidDatatypeValueException("IDREFInvalid", new Object[] { paramString });
    }
    paramValidationContext.addIdRef(paramString);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dv.dtd.XML11IDREFDatatypeValidator
 * JD-Core Version:    0.7.0.1
 */