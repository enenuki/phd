package org.apache.xerces.impl.dv.dtd;

import org.apache.xerces.impl.dv.DatatypeValidator;
import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidationContext;

public class ENTITYDatatypeValidator
  implements DatatypeValidator
{
  public void validate(String paramString, ValidationContext paramValidationContext)
    throws InvalidDatatypeValueException
  {
    if (!paramValidationContext.isEntityUnparsed(paramString)) {
      throw new InvalidDatatypeValueException("ENTITYNotUnparsed", new Object[] { paramString });
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dv.dtd.ENTITYDatatypeValidator
 * JD-Core Version:    0.7.0.1
 */