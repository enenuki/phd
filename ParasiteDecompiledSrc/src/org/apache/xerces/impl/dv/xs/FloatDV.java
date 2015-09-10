package org.apache.xerces.impl.dv.xs;

import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidationContext;
import org.apache.xerces.xs.datatypes.XSFloat;

public class FloatDV
  extends TypeValidator
{
  public short getAllowedFacets()
  {
    return 2552;
  }
  
  public Object getActualValue(String paramString, ValidationContext paramValidationContext)
    throws InvalidDatatypeValueException
  {
    try
    {
      return new XFloat(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[] { paramString, "float" });
    }
  }
  
  public int compare(Object paramObject1, Object paramObject2)
  {
    return ((XFloat)paramObject1).compareTo((XFloat)paramObject2);
  }
  
  public boolean isIdentical(Object paramObject1, Object paramObject2)
  {
    if ((paramObject2 instanceof XFloat)) {
      return ((XFloat)paramObject1).isIdentical((XFloat)paramObject2);
    }
    return false;
  }
  
  private static final class XFloat
    implements XSFloat
  {
    private final float value;
    private String canonical;
    
    public XFloat(String paramString)
      throws NumberFormatException
    {
      if (DoubleDV.isPossibleFP(paramString)) {
        this.value = Float.parseFloat(paramString);
      } else if (paramString.equals("INF")) {
        this.value = (1.0F / 1.0F);
      } else if (paramString.equals("-INF")) {
        this.value = (1.0F / -1.0F);
      } else if (paramString.equals("NaN")) {
        this.value = (0.0F / 0.0F);
      } else {
        throw new NumberFormatException(paramString);
      }
    }
    
    public boolean equals(Object paramObject)
    {
      if (paramObject == this) {
        return true;
      }
      if (!(paramObject instanceof XFloat)) {
        return false;
      }
      XFloat localXFloat = (XFloat)paramObject;
      if (this.value == localXFloat.value) {
        return true;
      }
      return (this.value != this.value) && (localXFloat.value != localXFloat.value);
    }
    
    public int hashCode()
    {
      return this.value == 0.0F ? 0 : Float.floatToIntBits(this.value);
    }
    
    public boolean isIdentical(XFloat paramXFloat)
    {
      if (paramXFloat == this) {
        return true;
      }
      if (this.value == paramXFloat.value) {
        return (this.value != 0.0F) || (Float.floatToIntBits(this.value) == Float.floatToIntBits(paramXFloat.value));
      }
      return (this.value != this.value) && (paramXFloat.value != paramXFloat.value);
    }
    
    private int compareTo(XFloat paramXFloat)
    {
      float f = paramXFloat.value;
      if (this.value < f) {
        return -1;
      }
      if (this.value > f) {
        return 1;
      }
      if (this.value == f) {
        return 0;
      }
      if (this.value != this.value)
      {
        if (f != f) {
          return 0;
        }
        return 2;
      }
      return 2;
    }
    
    public synchronized String toString()
    {
      if (this.canonical == null) {
        if (this.value == (1.0F / 1.0F))
        {
          this.canonical = "INF";
        }
        else if (this.value == (1.0F / -1.0F))
        {
          this.canonical = "-INF";
        }
        else if (this.value != this.value)
        {
          this.canonical = "NaN";
        }
        else if (this.value == 0.0F)
        {
          this.canonical = "0.0E1";
        }
        else
        {
          this.canonical = Float.toString(this.value);
          if (this.canonical.indexOf('E') == -1)
          {
            int i = this.canonical.length();
            char[] arrayOfChar = new char[i + 3];
            this.canonical.getChars(0, i, arrayOfChar, 0);
            int j = arrayOfChar[0] == '-' ? 2 : 1;
            int k;
            int m;
            int n;
            if ((this.value >= 1.0F) || (this.value <= -1.0F))
            {
              k = this.canonical.indexOf('.');
              for (m = k; m > j; m--) {
                arrayOfChar[m] = arrayOfChar[(m - 1)];
              }
              arrayOfChar[j] = '.';
              while (arrayOfChar[(i - 1)] == '0') {
                i--;
              }
              if (arrayOfChar[(i - 1)] == '.') {
                i++;
              }
              arrayOfChar[(i++)] = 'E';
              n = k - j;
              arrayOfChar[(i++)] = ((char)(n + 48));
            }
            else
            {
              for (k = j + 1; arrayOfChar[k] == '0'; k++) {}
              arrayOfChar[(j - 1)] = arrayOfChar[k];
              arrayOfChar[j] = '.';
              m = k + 1;
              for (n = j + 1; m < i; n++)
              {
                arrayOfChar[n] = arrayOfChar[m];
                m++;
              }
              i -= k - j;
              if (i == j + 1) {
                arrayOfChar[(i++)] = '0';
              }
              arrayOfChar[(i++)] = 'E';
              arrayOfChar[(i++)] = '-';
              int i1 = k - j;
              arrayOfChar[(i++)] = ((char)(i1 + 48));
            }
            this.canonical = new String(arrayOfChar, 0, i);
          }
        }
      }
      return this.canonical;
    }
    
    public float getValue()
    {
      return this.value;
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dv.xs.FloatDV
 * JD-Core Version:    0.7.0.1
 */