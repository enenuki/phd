package org.apache.xerces.impl.dv.dtd;

import org.apache.xerces.impl.dv.DatatypeValidator;
import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidationContext;
import org.apache.xerces.util.XMLChar;

public class IDREFDatatypeValidator
  implements DatatypeValidator
{
  public void validate(String paramString, ValidationContext paramValidationContext)
    throws InvalidDatatypeValueException
  {
    if (paramValidationContext.useNamespaces())
    {
      if (!XMLChar.isValidNCName(paramString)) {
        throw new InvalidDatatypeValueException("IDREFInvalidWithNamespaces", new Object[] { paramString });
      }
    }
    else if (!XMLChar.isValidName(paramString)) {
      throw new InvalidDatatypeValueException("IDREFInvalid", new Object[] { paramString });
    }
    paramValidationContext.addIdRef(paramString);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dv.dtd.IDREFDatatypeValidator
 * JD-Core Version:    0.7.0.1
 */