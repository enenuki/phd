package cern.colt.matrix.impl;

import cern.colt.PersistentObject;

public abstract class AbstractFormatter
  extends PersistentObject
{
  public static final String LEFT = "left";
  public static final String CENTER = "center";
  public static final String RIGHT = "right";
  public static final String DECIMAL = "decimal";
  public static final int DEFAULT_MIN_COLUMN_WIDTH = 1;
  public static final String DEFAULT_COLUMN_SEPARATOR = " ";
  public static final String DEFAULT_ROW_SEPARATOR = "\n";
  public static final String DEFAULT_SLICE_SEPARATOR = "\n\n";
  protected String alignment = "left";
  protected String format = "%G";
  protected int minColumnWidth = 1;
  protected String columnSeparator = " ";
  protected String rowSeparator = "\n";
  protected String sliceSeparator = "\n\n";
  protected boolean printShape = true;
  private static String[] blanksCache;
  protected static final FormerFactory factory = new FormerFactory();
  
  protected void align(String[][] paramArrayOfString)
  {
    int i = paramArrayOfString.length;
    int j = 0;
    if (i > 0) {
      j = paramArrayOfString[0].length;
    }
    int[] arrayOfInt1 = new int[j];
    int[] arrayOfInt2 = null;
    boolean bool = this.alignment.equals("decimal");
    if (bool) {
      arrayOfInt2 = new int[j];
    }
    for (int k = 0; k < j; k++)
    {
      int m = this.minColumnWidth;
      int n = -2147483648;
      for (int i1 = 0; i1 < i; i1++)
      {
        String str = paramArrayOfString[i1][k];
        m = Math.max(m, str.length());
        if (bool) {
          n = Math.max(n, lead(str));
        }
      }
      arrayOfInt1[k] = m;
      if (bool) {
        arrayOfInt2[k] = n;
      }
    }
    for (k = 0; k < i; k++) {
      alignRow(paramArrayOfString[k], arrayOfInt1, arrayOfInt2);
    }
  }
  
  protected int alignmentCode(String paramString)
  {
    if (paramString.equals("left")) {
      return -1;
    }
    if (paramString.equals("center")) {
      return 0;
    }
    if (paramString.equals("right")) {
      return 1;
    }
    if (paramString.equals("decimal")) {
      return 2;
    }
    throw new IllegalArgumentException("unknown alignment: " + paramString);
  }
  
  protected void alignRow(String[] paramArrayOfString, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    int i = alignmentCode(this.alignment);
    StringBuffer localStringBuffer = new StringBuffer();
    int j = paramArrayOfString.length;
    for (int k = 0; k < j; k++)
    {
      localStringBuffer.setLength(0);
      String str = paramArrayOfString[k];
      if (this.alignment.equals("right"))
      {
        localStringBuffer.append(blanks(paramArrayOfInt1[k] - localStringBuffer.length()));
        localStringBuffer.append(str);
      }
      else if (this.alignment.equals("decimal"))
      {
        localStringBuffer.append(blanks(paramArrayOfInt2[k] - lead(str)));
        localStringBuffer.append(str);
        localStringBuffer.append(blanks(paramArrayOfInt1[k] - localStringBuffer.length()));
      }
      else if (this.alignment.equals("center"))
      {
        localStringBuffer.append(blanks((paramArrayOfInt1[k] - str.length()) / 2));
        localStringBuffer.append(str);
        localStringBuffer.append(blanks(paramArrayOfInt1[k] - localStringBuffer.length()));
      }
      else if (this.alignment.equals("left"))
      {
        localStringBuffer.append(str);
        localStringBuffer.append(blanks(paramArrayOfInt1[k] - localStringBuffer.length()));
      }
      else
      {
        throw new InternalError();
      }
      paramArrayOfString[k] = localStringBuffer.toString();
    }
  }
  
  protected String blanks(int paramInt)
  {
    if (paramInt < 0) {
      paramInt = 0;
    }
    if (paramInt < blanksCache.length) {
      return blanksCache[paramInt];
    }
    StringBuffer localStringBuffer = new StringBuffer(paramInt);
    for (int i = 0; i < paramInt; i++) {
      localStringBuffer.append(' ');
    }
    return localStringBuffer.toString();
  }
  
  public static void demo1() {}
  
  public static void demo2() {}
  
  public static void demo3(int paramInt, Object paramObject) {}
  
  protected abstract String form(AbstractMatrix1D paramAbstractMatrix1D, int paramInt, Former paramFormer);
  
  protected abstract String[][] format(AbstractMatrix2D paramAbstractMatrix2D);
  
  protected String[] formatRow(AbstractMatrix1D paramAbstractMatrix1D)
  {
    Former localFormer = null;
    localFormer = factory.create(this.format);
    int i = paramAbstractMatrix1D.size();
    String[] arrayOfString = new String[i];
    for (int j = 0; j < i; j++) {
      arrayOfString[j] = form(paramAbstractMatrix1D, j, localFormer);
    }
    return arrayOfString;
  }
  
  protected int lead(String paramString)
  {
    return paramString.length();
  }
  
  protected String repeat(char paramChar, int paramInt)
  {
    if (paramChar == ' ') {
      return blanks(paramInt);
    }
    if (paramInt < 0) {
      paramInt = 0;
    }
    StringBuffer localStringBuffer = new StringBuffer(paramInt);
    for (int i = 0; i < paramInt; i++) {
      localStringBuffer.append(paramChar);
    }
    return localStringBuffer.toString();
  }
  
  public void setAlignment(String paramString)
  {
    this.alignment = paramString;
  }
  
  public void setColumnSeparator(String paramString)
  {
    this.columnSeparator = paramString;
  }
  
  public void setFormat(String paramString)
  {
    this.format = paramString;
  }
  
  public void setMinColumnWidth(int paramInt)
  {
    if (paramInt < 0) {
      throw new IllegalArgumentException();
    }
    this.minColumnWidth = paramInt;
  }
  
  public void setPrintShape(boolean paramBoolean)
  {
    this.printShape = paramBoolean;
  }
  
  public void setRowSeparator(String paramString)
  {
    this.rowSeparator = paramString;
  }
  
  public void setSliceSeparator(String paramString)
  {
    this.sliceSeparator = paramString;
  }
  
  protected static void setupBlanksCache()
  {
    int i = 40;
    blanksCache = new String[i];
    StringBuffer localStringBuffer = new StringBuffer(i);
    int j = i;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      localStringBuffer.append(' ');
    }
    String str = localStringBuffer.toString();
    int k = i;
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      blanksCache[k] = str.substring(0, k);
    }
  }
  
  public static String shape(AbstractMatrix1D paramAbstractMatrix1D)
  {
    return paramAbstractMatrix1D.size() + " matrix";
  }
  
  public static String shape(AbstractMatrix2D paramAbstractMatrix2D)
  {
    return paramAbstractMatrix2D.rows() + " x " + paramAbstractMatrix2D.columns() + " matrix";
  }
  
  public static String shape(AbstractMatrix3D paramAbstractMatrix3D)
  {
    return paramAbstractMatrix3D.slices() + " x " + paramAbstractMatrix3D.rows() + " x " + paramAbstractMatrix3D.columns() + " matrix";
  }
  
  protected String toString(String[][] paramArrayOfString)
  {
    int i = paramArrayOfString.length;
    int j = paramArrayOfString.length <= 0 ? 0 : paramArrayOfString[0].length;
    StringBuffer localStringBuffer1 = new StringBuffer();
    StringBuffer localStringBuffer2 = new StringBuffer();
    for (int k = 0; k < i; k++)
    {
      localStringBuffer2.setLength(0);
      for (int m = 0; m < j; m++)
      {
        localStringBuffer2.append(paramArrayOfString[k][m]);
        if (m < j - 1) {
          localStringBuffer2.append(this.columnSeparator);
        }
      }
      localStringBuffer1.append(localStringBuffer2);
      if (k < i - 1) {
        localStringBuffer1.append(this.rowSeparator);
      }
    }
    return localStringBuffer1.toString();
  }
  
  protected String toString(AbstractMatrix2D paramAbstractMatrix2D)
  {
    String[][] arrayOfString = format(paramAbstractMatrix2D);
    align(arrayOfString);
    StringBuffer localStringBuffer = new StringBuffer(toString(arrayOfString));
    if (this.printShape) {
      localStringBuffer.insert(0, shape(paramAbstractMatrix2D) + "\n");
    }
    return localStringBuffer.toString();
  }
  
  static
  {
    setupBlanksCache();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.AbstractFormatter
 * JD-Core Version:    0.7.0.1
 */