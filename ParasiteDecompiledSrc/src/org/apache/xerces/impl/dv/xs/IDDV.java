package org.apache.xerces.impl.dv.xs;

import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidationContext;
import org.apache.xerces.util.XMLChar;

public class IDDV
  extends TypeValidator
{
  public short getAllowedFacets()
  {
    return 2079;
  }
  
  public Object getActualValue(String paramString, ValidationContext paramValidationContext)
    throws InvalidDatatypeValueException
  {
    if (!XMLChar.isValidNCName(paramString)) {
      throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[] { paramString, "NCName" });
    }
    return paramString;
  }
  
  public void checkExtraRules(Object paramObject, ValidationContext paramValidationContext)
    throws InvalidDatatypeValueException
  {
    String str = (String)paramObject;
    if (paramValidationContext.isIdDeclared(str)) {
      throw new InvalidDatatypeValueException("cvc-id.2", new Object[] { str });
    }
    paramValidationContext.addId(str);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dv.xs.IDDV
 * JD-Core Version:    0.7.0.1
 */