package org.apache.xerces.impl.dv.xs;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidationContext;

public class DurationDV
  extends AbstractDateTimeDV
{
  public static final int DURATION_TYPE = 0;
  public static final int YEARMONTHDURATION_TYPE = 1;
  public static final int DAYTIMEDURATION_TYPE = 2;
  private static final AbstractDateTimeDV.DateTimeData[] DATETIMES = { new AbstractDateTimeDV.DateTimeData(1696, 9, 1, 0, 0, 0.0D, 90, null, true, null), new AbstractDateTimeDV.DateTimeData(1697, 2, 1, 0, 0, 0.0D, 90, null, true, null), new AbstractDateTimeDV.DateTimeData(1903, 3, 1, 0, 0, 0.0D, 90, null, true, null), new AbstractDateTimeDV.DateTimeData(1903, 7, 1, 0, 0, 0.0D, 90, null, true, null) };
  
  public Object getActualValue(String paramString, ValidationContext paramValidationContext)
    throws InvalidDatatypeValueException
  {
    try
    {
      return parse(paramString, 0);
    }
    catch (Exception localException)
    {
      throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[] { paramString, "duration" });
    }
  }
  
  protected AbstractDateTimeDV.DateTimeData parse(String paramString, int paramInt)
    throws SchemaDateTimeException
  {
    int i = paramString.length();
    AbstractDateTimeDV.DateTimeData localDateTimeData = new AbstractDateTimeDV.DateTimeData(paramString, this);
    int j = 0;
    int k = paramString.charAt(j++);
    if ((k != 80) && (k != 45)) {
      throw new SchemaDateTimeException();
    }
    localDateTimeData.utc = (k == 45 ? 45 : 0);
    if ((k == 45) && (paramString.charAt(j++) != 'P')) {
      throw new SchemaDateTimeException();
    }
    int m = 1;
    if (localDateTimeData.utc == 45) {
      m = -1;
    }
    int n = 0;
    int i1 = indexOf(paramString, j, i, 'T');
    if (i1 == -1) {
      i1 = i;
    } else if (paramInt == 1) {
      throw new SchemaDateTimeException();
    }
    int i2 = indexOf(paramString, j, i1, 'Y');
    if (i2 != -1)
    {
      if (paramInt == 2) {
        throw new SchemaDateTimeException();
      }
      localDateTimeData.year = (m * parseInt(paramString, j, i2));
      j = i2 + 1;
      n = 1;
    }
    i2 = indexOf(paramString, j, i1, 'M');
    if (i2 != -1)
    {
      if (paramInt == 2) {
        throw new SchemaDateTimeException();
      }
      localDateTimeData.month = (m * parseInt(paramString, j, i2));
      j = i2 + 1;
      n = 1;
    }
    i2 = indexOf(paramString, j, i1, 'D');
    if (i2 != -1)
    {
      if (paramInt == 1) {
        throw new SchemaDateTimeException();
      }
      localDateTimeData.day = (m * parseInt(paramString, j, i2));
      j = i2 + 1;
      n = 1;
    }
    if ((i == i1) && (j != i)) {
      throw new SchemaDateTimeException();
    }
    if (i != i1)
    {
      i2 = indexOf(paramString, ++j, i, 'H');
      if (i2 != -1)
      {
        localDateTimeData.hour = (m * parseInt(paramString, j, i2));
        j = i2 + 1;
        n = 1;
      }
      i2 = indexOf(paramString, j, i, 'M');
      if (i2 != -1)
      {
        localDateTimeData.minute = (m * parseInt(paramString, j, i2));
        j = i2 + 1;
        n = 1;
      }
      i2 = indexOf(paramString, j, i, 'S');
      if (i2 != -1)
      {
        localDateTimeData.second = (m * parseSecond(paramString, j, i2));
        j = i2 + 1;
        n = 1;
      }
      if ((j != i) || (paramString.charAt(--j) == 'T')) {
        throw new SchemaDateTimeException();
      }
    }
    if (n == 0) {
      throw new SchemaDateTimeException();
    }
    return localDateTimeData;
  }
  
  protected short compareDates(AbstractDateTimeDV.DateTimeData paramDateTimeData1, AbstractDateTimeDV.DateTimeData paramDateTimeData2, boolean paramBoolean)
  {
    short s2 = 2;
    short s1 = compareOrder(paramDateTimeData1, paramDateTimeData2);
    if (s1 == 0) {
      return 0;
    }
    AbstractDateTimeDV.DateTimeData[] arrayOfDateTimeData = new AbstractDateTimeDV.DateTimeData[2];
    arrayOfDateTimeData[0] = new AbstractDateTimeDV.DateTimeData(null, this);
    arrayOfDateTimeData[1] = new AbstractDateTimeDV.DateTimeData(null, this);
    AbstractDateTimeDV.DateTimeData localDateTimeData1 = addDuration(paramDateTimeData1, DATETIMES[0], arrayOfDateTimeData[0]);
    AbstractDateTimeDV.DateTimeData localDateTimeData2 = addDuration(paramDateTimeData2, DATETIMES[0], arrayOfDateTimeData[1]);
    s1 = compareOrder(localDateTimeData1, localDateTimeData2);
    if (s1 == 2) {
      return 2;
    }
    localDateTimeData1 = addDuration(paramDateTimeData1, DATETIMES[1], arrayOfDateTimeData[0]);
    localDateTimeData2 = addDuration(paramDateTimeData2, DATETIMES[1], arrayOfDateTimeData[1]);
    s2 = compareOrder(localDateTimeData1, localDateTimeData2);
    s1 = compareResults(s1, s2, paramBoolean);
    if (s1 == 2) {
      return 2;
    }
    localDateTimeData1 = addDuration(paramDateTimeData1, DATETIMES[2], arrayOfDateTimeData[0]);
    localDateTimeData2 = addDuration(paramDateTimeData2, DATETIMES[2], arrayOfDateTimeData[1]);
    s2 = compareOrder(localDateTimeData1, localDateTimeData2);
    s1 = compareResults(s1, s2, paramBoolean);
    if (s1 == 2) {
      return 2;
    }
    localDateTimeData1 = addDuration(paramDateTimeData1, DATETIMES[3], arrayOfDateTimeData[0]);
    localDateTimeData2 = addDuration(paramDateTimeData2, DATETIMES[3], arrayOfDateTimeData[1]);
    s2 = compareOrder(localDateTimeData1, localDateTimeData2);
    s1 = compareResults(s1, s2, paramBoolean);
    return s1;
  }
  
  private short compareResults(short paramShort1, short paramShort2, boolean paramBoolean)
  {
    if (paramShort2 == 2) {
      return 2;
    }
    if ((paramShort1 != paramShort2) && (paramBoolean)) {
      return 2;
    }
    if ((paramShort1 != paramShort2) && (!paramBoolean))
    {
      if ((paramShort1 != 0) && (paramShort2 != 0)) {
        return 2;
      }
      return paramShort1 != 0 ? paramShort1 : paramShort2;
    }
    return paramShort1;
  }
  
  private AbstractDateTimeDV.DateTimeData addDuration(AbstractDateTimeDV.DateTimeData paramDateTimeData1, AbstractDateTimeDV.DateTimeData paramDateTimeData2, AbstractDateTimeDV.DateTimeData paramDateTimeData3)
  {
    resetDateObj(paramDateTimeData3);
    int i = paramDateTimeData2.month + paramDateTimeData1.month;
    paramDateTimeData3.month = modulo(i, 1, 13);
    int j = fQuotient(i, 1, 13);
    paramDateTimeData3.year = (paramDateTimeData2.year + paramDateTimeData1.year + j);
    double d = paramDateTimeData2.second + paramDateTimeData1.second;
    j = (int)Math.floor(d / 60.0D);
    paramDateTimeData3.second = (d - j * 60);
    i = paramDateTimeData2.minute + paramDateTimeData1.minute + j;
    j = fQuotient(i, 60);
    paramDateTimeData3.minute = mod(i, 60, j);
    i = paramDateTimeData2.hour + paramDateTimeData1.hour + j;
    j = fQuotient(i, 24);
    paramDateTimeData3.hour = mod(i, 24, j);
    paramDateTimeData3.day = (paramDateTimeData2.day + paramDateTimeData1.day + j);
    for (;;)
    {
      i = maxDayInMonthFor(paramDateTimeData3.year, paramDateTimeData3.month);
      if (paramDateTimeData3.day < 1)
      {
        paramDateTimeData3.day += maxDayInMonthFor(paramDateTimeData3.year, paramDateTimeData3.month - 1);
        j = -1;
      }
      else
      {
        if (paramDateTimeData3.day <= i) {
          break;
        }
        paramDateTimeData3.day -= i;
        j = 1;
      }
      i = paramDateTimeData3.month + j;
      paramDateTimeData3.month = modulo(i, 1, 13);
      paramDateTimeData3.year += fQuotient(i, 1, 13);
    }
    paramDateTimeData3.utc = 90;
    return paramDateTimeData3;
  }
  
  protected double parseSecond(String paramString, int paramInt1, int paramInt2)
    throws NumberFormatException
  {
    int i = -1;
    for (int j = paramInt1; j < paramInt2; j++)
    {
      int k = paramString.charAt(j);
      if (k == 46) {
        i = j;
      } else if ((k > 57) || (k < 48)) {
        throw new NumberFormatException("'" + paramString + "' has wrong format");
      }
    }
    if (i + 1 == paramInt2) {
      throw new NumberFormatException("'" + paramString + "' has wrong format");
    }
    double d = Double.parseDouble(paramString.substring(paramInt1, paramInt2));
    if (d == (1.0D / 0.0D)) {
      throw new NumberFormatException("'" + paramString + "' has wrong format");
    }
    return d;
  }
  
  protected String dateToString(AbstractDateTimeDV.DateTimeData paramDateTimeData)
  {
    StringBuffer localStringBuffer = new StringBuffer(30);
    if ((paramDateTimeData.year < 0) || (paramDateTimeData.month < 0) || (paramDateTimeData.day < 0) || (paramDateTimeData.hour < 0) || (paramDateTimeData.minute < 0) || (paramDateTimeData.second < 0.0D)) {
      localStringBuffer.append('-');
    }
    localStringBuffer.append('P');
    localStringBuffer.append((paramDateTimeData.year < 0 ? -1 : 1) * paramDateTimeData.year);
    localStringBuffer.append('Y');
    localStringBuffer.append((paramDateTimeData.month < 0 ? -1 : 1) * paramDateTimeData.month);
    localStringBuffer.append('M');
    localStringBuffer.append((paramDateTimeData.day < 0 ? -1 : 1) * paramDateTimeData.day);
    localStringBuffer.append('D');
    localStringBuffer.append('T');
    localStringBuffer.append((paramDateTimeData.hour < 0 ? -1 : 1) * paramDateTimeData.hour);
    localStringBuffer.append('H');
    localStringBuffer.append((paramDateTimeData.minute < 0 ? -1 : 1) * paramDateTimeData.minute);
    localStringBuffer.append('M');
    append2(localStringBuffer, (paramDateTimeData.second < 0.0D ? -1.0D : 1.0D) * paramDateTimeData.second);
    localStringBuffer.append('S');
    return localStringBuffer.toString();
  }
  
  protected Duration getDuration(AbstractDateTimeDV.DateTimeData paramDateTimeData)
  {
    int i = 1;
    if ((paramDateTimeData.year < 0) || (paramDateTimeData.month < 0) || (paramDateTimeData.day < 0) || (paramDateTimeData.hour < 0) || (paramDateTimeData.minute < 0) || (paramDateTimeData.second < 0.0D)) {
      i = -1;
    }
    return this.factory.newDuration(i == 1, paramDateTimeData.year != -2147483648 ? BigInteger.valueOf(i * paramDateTimeData.year) : null, paramDateTimeData.month != -2147483648 ? BigInteger.valueOf(i * paramDateTimeData.month) : null, paramDateTimeData.day != -2147483648 ? BigInteger.valueOf(i * paramDateTimeData.day) : null, paramDateTimeData.hour != -2147483648 ? BigInteger.valueOf(i * paramDateTimeData.hour) : null, paramDateTimeData.minute != -2147483648 ? BigInteger.valueOf(i * paramDateTimeData.minute) : null, paramDateTimeData.second != -2147483648.0D ? new BigDecimal(String.valueOf(i * paramDateTimeData.second)) : null);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dv.xs.DurationDV
 * JD-Core Version:    0.7.0.1
 */