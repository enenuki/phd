package org.apache.xerces.impl.dv.xs;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidationContext;

class DayTimeDurationDV
  extends DurationDV
{
  public Object getActualValue(String paramString, ValidationContext paramValidationContext)
    throws InvalidDatatypeValueException
  {
    try
    {
      return parse(paramString, 2);
    }
    catch (Exception localException)
    {
      throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[] { paramString, "dayTimeDuration" });
    }
  }
  
  protected Duration getDuration(AbstractDateTimeDV.DateTimeData paramDateTimeData)
  {
    int i = 1;
    if ((paramDateTimeData.day < 0) || (paramDateTimeData.hour < 0) || (paramDateTimeData.minute < 0) || (paramDateTimeData.second < 0.0D)) {
      i = -1;
    }
    return this.factory.newDuration(i == 1, null, null, paramDateTimeData.day != -2147483648 ? BigInteger.valueOf(i * paramDateTimeData.day) : null, paramDateTimeData.hour != -2147483648 ? BigInteger.valueOf(i * paramDateTimeData.hour) : null, paramDateTimeData.minute != -2147483648 ? BigInteger.valueOf(i * paramDateTimeData.minute) : null, paramDateTimeData.second != -2147483648.0D ? new BigDecimal(String.valueOf(i * paramDateTimeData.second)) : null);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dv.xs.DayTimeDurationDV
 * JD-Core Version:    0.7.0.1
 */