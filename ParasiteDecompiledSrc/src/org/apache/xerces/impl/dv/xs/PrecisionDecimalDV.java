package org.apache.xerces.impl.dv.xs;

import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidationContext;

class PrecisionDecimalDV
  extends TypeValidator
{
  public short getAllowedFacets()
  {
    return 4088;
  }
  
  public Object getActualValue(String paramString, ValidationContext paramValidationContext)
    throws InvalidDatatypeValueException
  {
    try
    {
      return new XPrecisionDecimal(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[] { paramString, "precisionDecimal" });
    }
  }
  
  public int compare(Object paramObject1, Object paramObject2)
  {
    return ((XPrecisionDecimal)paramObject1).compareTo((XPrecisionDecimal)paramObject2);
  }
  
  public int getFractionDigits(Object paramObject)
  {
    return ((XPrecisionDecimal)paramObject).fracDigits;
  }
  
  public int getTotalDigits(Object paramObject)
  {
    return ((XPrecisionDecimal)paramObject).totalDigits;
  }
  
  public boolean isIdentical(Object paramObject1, Object paramObject2)
  {
    if ((!(paramObject2 instanceof XPrecisionDecimal)) || (!(paramObject1 instanceof XPrecisionDecimal))) {
      return false;
    }
    return ((XPrecisionDecimal)paramObject1).isIdentical((XPrecisionDecimal)paramObject2);
  }
  
  static class XPrecisionDecimal
  {
    int sign = 1;
    int totalDigits = 0;
    int intDigits = 0;
    int fracDigits = 0;
    String ivalue = "";
    String fvalue = "";
    int pvalue = 0;
    private String canonical;
    
    XPrecisionDecimal(String paramString)
      throws NumberFormatException
    {
      if (paramString.equals("NaN"))
      {
        this.ivalue = paramString;
        this.sign = 0;
      }
      if ((paramString.equals("+INF")) || (paramString.equals("INF")) || (paramString.equals("-INF")))
      {
        this.ivalue = (paramString.charAt(0) == '+' ? paramString.substring(1) : paramString);
        return;
      }
      initD(paramString);
    }
    
    void initD(String paramString)
      throws NumberFormatException
    {
      int i = paramString.length();
      if (i == 0) {
        throw new NumberFormatException();
      }
      int j = 0;
      int k = 0;
      int m = 0;
      int n = 0;
      if (paramString.charAt(0) == '+')
      {
        j = 1;
      }
      else if (paramString.charAt(0) == '-')
      {
        j = 1;
        this.sign = -1;
      }
      for (int i1 = j; (i1 < i) && (paramString.charAt(i1) == '0'); i1++) {}
      for (k = i1; (k < i) && (TypeValidator.isDigit(paramString.charAt(k))); k++) {}
      if (k < i)
      {
        if ((paramString.charAt(k) != '.') && (paramString.charAt(k) != 'E') && (paramString.charAt(k) != 'e')) {
          throw new NumberFormatException();
        }
        if (paramString.charAt(k) == '.')
        {
          m = k + 1;
          n = m;
          do
          {
            n++;
            if (n >= i) {
              break;
            }
          } while (TypeValidator.isDigit(paramString.charAt(n)));
        }
        else
        {
          this.pvalue = Integer.parseInt(paramString.substring(k + 1, i));
        }
      }
      if ((j == k) && (m == n)) {
        throw new NumberFormatException();
      }
      for (int i2 = m; i2 < n; i2++) {
        if (!TypeValidator.isDigit(paramString.charAt(i2))) {
          throw new NumberFormatException();
        }
      }
      this.intDigits = (k - i1);
      this.fracDigits = (n - m);
      if (this.intDigits > 0) {
        this.ivalue = paramString.substring(i1, k);
      }
      if (this.fracDigits > 0)
      {
        this.fvalue = paramString.substring(m, n);
        if (n < i) {
          this.pvalue = Integer.parseInt(paramString.substring(n + 1, i));
        }
      }
      this.totalDigits = (this.intDigits + this.fracDigits);
    }
    
    public boolean equals(Object paramObject)
    {
      if (paramObject == this) {
        return true;
      }
      if (!(paramObject instanceof XPrecisionDecimal)) {
        return false;
      }
      XPrecisionDecimal localXPrecisionDecimal = (XPrecisionDecimal)paramObject;
      return compareTo(localXPrecisionDecimal) == 0;
    }
    
    private int compareFractionalPart(XPrecisionDecimal paramXPrecisionDecimal)
    {
      if (this.fvalue.equals(paramXPrecisionDecimal.fvalue)) {
        return 0;
      }
      StringBuffer localStringBuffer1 = new StringBuffer(this.fvalue);
      StringBuffer localStringBuffer2 = new StringBuffer(paramXPrecisionDecimal.fvalue);
      truncateTrailingZeros(localStringBuffer1, localStringBuffer2);
      return localStringBuffer1.toString().compareTo(localStringBuffer2.toString());
    }
    
    private void truncateTrailingZeros(StringBuffer paramStringBuffer1, StringBuffer paramStringBuffer2)
    {
      for (int i = paramStringBuffer1.length() - 1; i >= 0; i--)
      {
        if (paramStringBuffer1.charAt(i) != '0') {
          break;
        }
        paramStringBuffer1.deleteCharAt(i);
      }
      for (int j = paramStringBuffer2.length() - 1; j >= 0; j--)
      {
        if (paramStringBuffer2.charAt(j) != '0') {
          break;
        }
        paramStringBuffer2.deleteCharAt(j);
      }
    }
    
    public int compareTo(XPrecisionDecimal paramXPrecisionDecimal)
    {
      if (this.sign == 0) {
        return 2;
      }
      if ((this.ivalue.equals("INF")) || (paramXPrecisionDecimal.ivalue.equals("INF")))
      {
        if (this.ivalue.equals(paramXPrecisionDecimal.ivalue)) {
          return 0;
        }
        if (this.ivalue.equals("INF")) {
          return 1;
        }
        return -1;
      }
      if ((this.ivalue.equals("-INF")) || (paramXPrecisionDecimal.ivalue.equals("-INF")))
      {
        if (this.ivalue.equals(paramXPrecisionDecimal.ivalue)) {
          return 0;
        }
        if (this.ivalue.equals("-INF")) {
          return -1;
        }
        return 1;
      }
      if (this.sign != paramXPrecisionDecimal.sign) {
        return this.sign > paramXPrecisionDecimal.sign ? 1 : -1;
      }
      return this.sign * compare(paramXPrecisionDecimal);
    }
    
    private int compare(XPrecisionDecimal paramXPrecisionDecimal)
    {
      if ((this.pvalue != 0) || (paramXPrecisionDecimal.pvalue != 0))
      {
        if (this.pvalue == paramXPrecisionDecimal.pvalue) {
          return intComp(paramXPrecisionDecimal);
        }
        if (this.intDigits + this.pvalue != paramXPrecisionDecimal.intDigits + paramXPrecisionDecimal.pvalue) {
          return this.intDigits + this.pvalue > paramXPrecisionDecimal.intDigits + paramXPrecisionDecimal.pvalue ? 1 : -1;
        }
        if (this.pvalue > paramXPrecisionDecimal.pvalue)
        {
          i = this.pvalue - paramXPrecisionDecimal.pvalue;
          localStringBuffer1 = new StringBuffer(this.ivalue);
          localStringBuffer2 = new StringBuffer(this.fvalue);
          for (j = 0; j < i; j++) {
            if (j < this.fracDigits)
            {
              localStringBuffer1.append(this.fvalue.charAt(j));
              localStringBuffer2.deleteCharAt(j);
            }
            else
            {
              localStringBuffer1.append('0');
            }
          }
          return compareDecimal(localStringBuffer1.toString(), paramXPrecisionDecimal.ivalue, localStringBuffer2.toString(), paramXPrecisionDecimal.fvalue);
        }
        int i = paramXPrecisionDecimal.pvalue - this.pvalue;
        StringBuffer localStringBuffer1 = new StringBuffer(paramXPrecisionDecimal.ivalue);
        StringBuffer localStringBuffer2 = new StringBuffer(paramXPrecisionDecimal.fvalue);
        for (int j = 0; j < i; j++) {
          if (j < paramXPrecisionDecimal.fracDigits)
          {
            localStringBuffer1.append(paramXPrecisionDecimal.fvalue.charAt(j));
            localStringBuffer2.deleteCharAt(j);
          }
          else
          {
            localStringBuffer1.append('0');
          }
        }
        return compareDecimal(this.ivalue, localStringBuffer1.toString(), this.fvalue, localStringBuffer2.toString());
      }
      return intComp(paramXPrecisionDecimal);
    }
    
    private int intComp(XPrecisionDecimal paramXPrecisionDecimal)
    {
      if (this.intDigits != paramXPrecisionDecimal.intDigits) {
        return this.intDigits > paramXPrecisionDecimal.intDigits ? 1 : -1;
      }
      return compareDecimal(this.ivalue, paramXPrecisionDecimal.ivalue, this.fvalue, paramXPrecisionDecimal.fvalue);
    }
    
    private int compareDecimal(String paramString1, String paramString2, String paramString3, String paramString4)
    {
      int i = paramString1.compareTo(paramString3);
      if (i != 0) {
        return i > 0 ? 1 : -1;
      }
      if (paramString2.equals(paramString4)) {
        return 0;
      }
      StringBuffer localStringBuffer1 = new StringBuffer(paramString2);
      StringBuffer localStringBuffer2 = new StringBuffer(paramString4);
      truncateTrailingZeros(localStringBuffer1, localStringBuffer2);
      i = localStringBuffer1.toString().compareTo(localStringBuffer2.toString());
      return i > 0 ? 1 : i == 0 ? 0 : -1;
    }
    
    public synchronized String toString()
    {
      if (this.canonical == null) {
        makeCanonical();
      }
      return this.canonical;
    }
    
    private void makeCanonical()
    {
      this.canonical = "TBD by Working Group";
    }
    
    public boolean isIdentical(XPrecisionDecimal paramXPrecisionDecimal)
    {
      if ((this.ivalue.equals(paramXPrecisionDecimal.ivalue)) && ((this.ivalue.equals("INF")) || (this.ivalue.equals("-INF")) || (this.ivalue.equals("NaN")))) {
        return true;
      }
      return (this.sign == paramXPrecisionDecimal.sign) && (this.intDigits == paramXPrecisionDecimal.intDigits) && (this.fracDigits == paramXPrecisionDecimal.fracDigits) && (this.pvalue == paramXPrecisionDecimal.pvalue) && (this.ivalue.equals(paramXPrecisionDecimal.ivalue)) && (this.fvalue.equals(paramXPrecisionDecimal.fvalue));
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dv.xs.PrecisionDecimalDV
 * JD-Core Version:    0.7.0.1
 */