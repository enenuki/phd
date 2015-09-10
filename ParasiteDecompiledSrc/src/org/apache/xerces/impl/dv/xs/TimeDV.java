package org.apache.xerces.impl.dv.xs;

import java.math.BigDecimal;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidationContext;

public class TimeDV
  extends AbstractDateTimeDV
{
  public Object getActualValue(String paramString, ValidationContext paramValidationContext)
    throws InvalidDatatypeValueException
  {
    try
    {
      return parse(paramString);
    }
    catch (Exception localException)
    {
      throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[] { paramString, "time" });
    }
  }
  
  protected AbstractDateTimeDV.DateTimeData parse(String paramString)
    throws SchemaDateTimeException
  {
    AbstractDateTimeDV.DateTimeData localDateTimeData = new AbstractDateTimeDV.DateTimeData(paramString, this);
    int i = paramString.length();
    localDateTimeData.year = 2000;
    localDateTimeData.month = 1;
    localDateTimeData.day = 15;
    getTime(paramString, 0, i, localDateTimeData);
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
    StringBuffer localStringBuffer = new StringBuffer(16);
    append(localStringBuffer, paramDateTimeData.hour, 2);
    localStringBuffer.append(':');
    append(localStringBuffer, paramDateTimeData.minute, 2);
    localStringBuffer.append(':');
    append(localStringBuffer, paramDateTimeData.second);
    append(localStringBuffer, (char)paramDateTimeData.utc, 0);
    return localStringBuffer.toString();
  }
  
  protected XMLGregorianCalendar getXMLGregorianCalendar(AbstractDateTimeDV.DateTimeData paramDateTimeData)
  {
    return this.factory.newXMLGregorianCalendar(null, -2147483648, -2147483648, paramDateTimeData.unNormHour, paramDateTimeData.unNormMinute, (int)paramDateTimeData.unNormSecond, paramDateTimeData.unNormSecond != 0.0D ? new BigDecimal(paramDateTimeData.unNormSecond - (int)paramDateTimeData.unNormSecond) : null, paramDateTimeData.timezoneHr * 60 + paramDateTimeData.timezoneMin);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dv.xs.TimeDV
 * JD-Core Version:    0.7.0.1
 */