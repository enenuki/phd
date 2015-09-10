package org.apache.xerces.impl.dv.xs;

import java.math.BigInteger;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidationContext;

class YearMonthDurationDV
  extends DurationDV
{
  public Object getActualValue(String paramString, ValidationContext paramValidationContext)
    throws InvalidDatatypeValueException
  {
    try
    {
      return parse(paramString, 1);
    }
    catch (Exception localException)
    {
      throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[] { paramString, "yearMonthDuration" });
    }
  }
  
  protected Duration getDuration(AbstractDateTimeDV.DateTimeData paramDateTimeData)
  {
    int i = 1;
    if ((paramDateTimeData.year < 0) || (paramDateTimeData.month < 0)) {
      i = -1;
    }
    return this.factory.newDuration(i == 1, paramDateTimeData.year != -2147483648 ? BigInteger.valueOf(i * paramDateTimeData.year) : null, paramDateTimeData.month != -2147483648 ? BigInteger.valueOf(i * paramDateTimeData.month) : null, null, null, null, null);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dv.xs.YearMonthDurationDV
 * JD-Core Version:    0.7.0.1
 */