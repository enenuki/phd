package org.apache.xerces.impl.dv.xs;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidationContext;

public class DayDV
  extends AbstractDateTimeDV
{
  private static final int DAY_SIZE = 5;
  
  public Object getActualValue(String paramString, ValidationContext paramValidationContext)
    throws InvalidDatatypeValueException
  {
    try
    {
      return parse(paramString);
    }
    catch (Exception localException)
    {
      throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[] { paramString, "gDay" });
    }
  }
  
  protected AbstractDateTimeDV.DateTimeData parse(String paramString)
    throws SchemaDateTimeException
  {
    AbstractDateTimeDV.DateTimeData localDateTimeData = new AbstractDateTimeDV.DateTimeData(paramString, this);
    int i = paramString.length();
    if ((paramString.charAt(0) != '-') || (paramString.charAt(1) != '-') || (paramString.charAt(2) != '-')) {
      throw new SchemaDateTimeException("Error in day parsing");
    }
    localDateTimeData.year = 2000;
    localDateTimeData.month = 1;
    localDateTimeData.day = parseInt(paramString, 3, 5);
    if (5 < i)
    {
      if (!isNextCharUTCSign(paramString, 5, i)) {
        throw new SchemaDateTimeException("Error in day parsing");
      }
      getTimeZone(paramString, localDateTimeData, 5, i);
    }
    validateDateTime(localDateTimeData);
    saveUnnormalized(localDateTimeData);
    if ((localDateTimeData.utc != 0) && (localDateTimeData.utc != 90)) {
      normalize(localDateTimeData);
    }
    localDateTimeData.position = 2;
    return localDateTimeData;
  }
  
  protected String dateToString(AbstractDateTimeDV.DateTimeData paramDateTimeData)
  {
    StringBuffer localStringBuffer = new StringBuffer(6);
    localStringBuffer.append('-');
    localStringBuffer.append('-');
    localStringBuffer.append('-');
    append(localStringBuffer, paramDateTimeData.day, 2);
    append(localStringBuffer, (char)paramDateTimeData.utc, 0);
    return localStringBuffer.toString();
  }
  
  protected XMLGregorianCalendar getXMLGregorianCalendar(AbstractDateTimeDV.DateTimeData paramDateTimeData)
  {
    return this.factory.newXMLGregorianCalendar(-2147483648, -2147483648, paramDateTimeData.unNormDay, -2147483648, -2147483648, -2147483648, -2147483648, paramDateTimeData.timezoneHr * 60 + paramDateTimeData.timezoneMin);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dv.xs.DayDV
 * JD-Core Version:    0.7.0.1
 */