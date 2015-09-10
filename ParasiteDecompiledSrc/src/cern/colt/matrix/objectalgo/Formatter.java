package cern.colt.matrix.objectalgo;

import cern.colt.matrix.ObjectMatrix1D;
import cern.colt.matrix.ObjectMatrix2D;
import cern.colt.matrix.ObjectMatrix3D;
import cern.colt.matrix.impl.AbstractFormatter;
import cern.colt.matrix.impl.AbstractMatrix1D;
import cern.colt.matrix.impl.AbstractMatrix2D;
import cern.colt.matrix.impl.Former;

public class Formatter
  extends AbstractFormatter
{
  public Formatter()
  {
    this("left");
  }
  
  public Formatter(String paramString)
  {
    setAlignment(paramString);
  }
  
  protected String form(AbstractMatrix1D paramAbstractMatrix1D, int paramInt, Former paramFormer)
  {
    return form((ObjectMatrix1D)paramAbstractMatrix1D, paramInt, paramFormer);
  }
  
  protected String form(ObjectMatrix1D paramObjectMatrix1D, int paramInt, Former paramFormer)
  {
    Object localObject = paramObjectMatrix1D.get(paramInt);
    if (localObject == null) {
      return "";
    }
    return String.valueOf(localObject);
  }
  
  protected String[][] format(AbstractMatrix2D paramAbstractMatrix2D)
  {
    return format((ObjectMatrix2D)paramAbstractMatrix2D);
  }
  
  protected String[][] format(ObjectMatrix2D paramObjectMatrix2D)
  {
    String[][] arrayOfString = new String[paramObjectMatrix2D.rows()][paramObjectMatrix2D.columns()];
    int i = paramObjectMatrix2D.rows();
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      arrayOfString[i] = formatRow(paramObjectMatrix2D.viewRow(i));
    }
    return arrayOfString;
  }
  
  public String toSourceCode(ObjectMatrix1D paramObjectMatrix1D)
  {
    Formatter localFormatter = (Formatter)clone();
    localFormatter.setPrintShape(false);
    localFormatter.setColumnSeparator(", ");
    String str1 = "{";
    String str2 = "};";
    return str1 + localFormatter.toString(paramObjectMatrix1D) + str2;
  }
  
  public String toSourceCode(ObjectMatrix2D paramObjectMatrix2D)
  {
    Formatter localFormatter = (Formatter)clone();
    String str1 = blanks(3);
    localFormatter.setPrintShape(false);
    localFormatter.setColumnSeparator(", ");
    localFormatter.setRowSeparator("},\n" + str1 + "{");
    String str2 = "{\n" + str1 + "{";
    String str3 = "}\n};";
    return str2 + localFormatter.toString(paramObjectMatrix2D) + str3;
  }
  
  public String toSourceCode(ObjectMatrix3D paramObjectMatrix3D)
  {
    Formatter localFormatter = (Formatter)clone();
    String str1 = blanks(3);
    String str2 = blanks(6);
    localFormatter.setPrintShape(false);
    localFormatter.setColumnSeparator(", ");
    localFormatter.setRowSeparator("},\n" + str2 + "{");
    localFormatter.setSliceSeparator("}\n" + str1 + "},\n" + str1 + "{\n" + str2 + "{");
    String str3 = "{\n" + str1 + "{\n" + str2 + "{";
    String str4 = "}\n" + str1 + "}\n}";
    return str3 + localFormatter.toString(paramObjectMatrix3D) + str4;
  }
  
  protected String toString(AbstractMatrix2D paramAbstractMatrix2D)
  {
    return toString((ObjectMatrix2D)paramAbstractMatrix2D);
  }
  
  public String toString(ObjectMatrix1D paramObjectMatrix1D)
  {
    ObjectMatrix2D localObjectMatrix2D = paramObjectMatrix1D.like2D(1, paramObjectMatrix1D.size());
    localObjectMatrix2D.viewRow(0).assign(paramObjectMatrix1D);
    return toString(localObjectMatrix2D);
  }
  
  public String toString(ObjectMatrix2D paramObjectMatrix2D)
  {
    return super.toString(paramObjectMatrix2D);
  }
  
  public String toString(ObjectMatrix3D paramObjectMatrix3D)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    boolean bool = this.printShape;
    this.printShape = false;
    for (int i = 0; i < paramObjectMatrix3D.slices(); i++)
    {
      if (i != 0) {
        localStringBuffer.append(this.sliceSeparator);
      }
      localStringBuffer.append(toString(paramObjectMatrix3D.viewSlice(i)));
    }
    this.printShape = bool;
    if (this.printShape) {
      localStringBuffer.insert(0, shape(paramObjectMatrix3D) + "\n");
    }
    return localStringBuffer.toString();
  }
  
  public String toTitleString(ObjectMatrix2D paramObjectMatrix2D, String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString1, String paramString2, String paramString3)
  {
    if (paramObjectMatrix2D.size() == 0) {
      return "Empty matrix";
    }
    String str1 = this.format;
    this.format = "left";
    int i = paramObjectMatrix2D.rows();
    int j = paramObjectMatrix2D.columns();
    int k = 0;
    int m = 0;
    k += (paramArrayOfString2 == null ? 0 : 1);
    m += (paramArrayOfString1 == null ? 0 : 1);
    m += (paramString1 == null ? 0 : 1);
    m += ((paramArrayOfString1 != null) || (paramString1 != null) ? 1 : 0);
    int n = k + Math.max(i, paramString1 == null ? 0 : paramString1.length());
    int i1 = m + j;
    ObjectMatrix2D localObjectMatrix2D = paramObjectMatrix2D.like(n, i1);
    localObjectMatrix2D.viewPart(k, m, i, j).assign(paramObjectMatrix2D);
    if (k > 0) {
      localObjectMatrix2D.viewRow(0).viewPart(m, j).assign(paramArrayOfString2);
    }
    if (paramString1 != null)
    {
      String[] arrayOfString = new String[paramString1.length()];
      int i2 = paramString1.length();
      for (;;)
      {
        i2--;
        if (i2 < 0) {
          break;
        }
        arrayOfString[i2] = paramString1.substring(i2, i2 + 1);
      }
      localObjectMatrix2D.viewColumn(0).viewPart(k, paramString1.length()).assign(arrayOfString);
    }
    if (paramArrayOfString1 != null) {
      localObjectMatrix2D.viewColumn(m - 2).viewPart(k, i).assign(paramArrayOfString1);
    }
    if (m > 0) {
      localObjectMatrix2D.viewColumn(m - 2 + 1).viewPart(0, i + k).assign("|");
    }
    boolean bool = this.printShape;
    this.printShape = false;
    String str2 = toString(localObjectMatrix2D);
    this.printShape = bool;
    StringBuffer localStringBuffer = new StringBuffer(str2);
    int i3;
    if (paramArrayOfString2 != null)
    {
      i3 = str2.indexOf(this.rowSeparator);
      localStringBuffer.insert(i3 + 1, repeat('-', i3) + this.rowSeparator);
    }
    else if (paramString2 != null)
    {
      i3 = str2.indexOf(this.rowSeparator);
      localStringBuffer.insert(0, repeat('-', i3) + this.rowSeparator);
    }
    if (paramString2 != null)
    {
      i3 = 0;
      if (m > 0) {
        i3 = str2.indexOf('|');
      }
      String str3 = blanks(i3);
      if (m > 0) {
        str3 = str3 + "| ";
      }
      str3 = str3 + paramString2 + "\n";
      localStringBuffer.insert(0, str3);
    }
    if (paramString3 != null) {
      localStringBuffer.insert(0, paramString3 + "\n");
    }
    this.format = str1;
    return localStringBuffer.toString();
  }
  
  public String toTitleString(ObjectMatrix3D paramObjectMatrix3D, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    if (paramObjectMatrix3D.size() == 0) {
      return "Empty matrix";
    }
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < paramObjectMatrix3D.slices(); i++)
    {
      if (i != 0) {
        localStringBuffer.append(this.sliceSeparator);
      }
      localStringBuffer.append(toTitleString(paramObjectMatrix3D.viewSlice(i), paramArrayOfString2, paramArrayOfString3, paramString2, paramString3, paramString4 + "\n" + paramString1 + "=" + paramArrayOfString1[i]));
    }
    return localStringBuffer.toString();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.objectalgo.Formatter
 * JD-Core Version:    0.7.0.1
 */