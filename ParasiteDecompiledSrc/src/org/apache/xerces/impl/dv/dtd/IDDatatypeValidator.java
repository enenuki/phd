package org.apache.xerces.impl.dv.dtd;

import org.apache.xerces.impl.dv.DatatypeValidator;
import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidationContext;
import org.apache.xerces.util.XMLChar;

public class IDDatatypeValidator
  implements DatatypeValidator
{
  public void validate(String paramString, ValidationContext paramValidationContext)
    throws InvalidDatatypeValueException
  {
    if (paramValidationContext.useNamespaces())
    {
      if (!XMLChar.isValidNCName(paramString)) {
        throw new InvalidDatatypeValueException("IDInvalidWithNamespaces", new Object[] { paramString });
      }
    }
    else if (!XMLChar.isValidName(paramString)) {
      throw new InvalidDatatypeValueException("IDInvalid", new Object[] { paramString });
    }
    if (paramValidationContext.isIdDeclared(paramString)) {
      throw new InvalidDatatypeValueException("IDNotUnique", new Object[] { paramString });
    }
    paramValidationContext.addId(paramString);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dv.dtd.IDDatatypeValidator
 * JD-Core Version:    0.7.0.1
 */