package cern.colt.matrix.doublealgo;

import cern.colt.Timer;
import cern.colt.list.ObjectArrayList;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.DoubleMatrix3D;
import cern.colt.matrix.ObjectFactory2D;
import cern.colt.matrix.ObjectMatrix1D;
import cern.colt.matrix.ObjectMatrix2D;
import cern.colt.matrix.impl.AbstractFormatter;
import cern.colt.matrix.impl.AbstractMatrix1D;
import cern.colt.matrix.impl.AbstractMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.Former;
import cern.colt.matrix.impl.FormerFactory;
import hep.aida.bin.BinFunction1D;
import hep.aida.bin.BinFunctions1D;
import java.io.PrintStream;

public class Formatter
  extends AbstractFormatter
{
  public Formatter()
  {
    this("%G");
  }
  
  public Formatter(String paramString)
  {
    setFormat(paramString);
    setAlignment("decimal");
  }
  
  public static void demo1()
  {
    double[][] arrayOfDouble = { { 3.0D, 0.0D, -3.4D, 0.0D }, { 5.1D, 0.0D, 3.0123456789D, 0.0D }, { 16.370000000000001D, 0.0D, 2.5D, 0.0D }, { -16.300000000000001D, 0.0D, -0.0003012345678D, -1.0D }, { 1236.3456788999999D, 0.0D, 7.0D, -1.2D } };
    String[] arrayOfString1 = { "%G", "%1.10G", "%f", "%1.2f", "%0.2e", null };
    int i = arrayOfString1.length;
    DoubleMatrix2D localDoubleMatrix2D = DoubleFactory2D.dense.make(arrayOfDouble);
    String[] arrayOfString2 = new String[i];
    String[] arrayOfString3 = new String[i];
    String[] arrayOfString4 = new String[i];
    String[] arrayOfString5 = new String[i];
    for (int j = 0; j < i; j++)
    {
      String str = arrayOfString1[j];
      arrayOfString2[j] = new Formatter(str).toString(localDoubleMatrix2D);
      arrayOfString3[j] = new Formatter(str).toSourceCode(localDoubleMatrix2D);
    }
    System.out.println("original:\n" + new Formatter().toString(localDoubleMatrix2D));
    for (j = 0; j < i; j++) {}
    for (j = 0; j < i; j++)
    {
      System.out.println("\nstring(" + arrayOfString1[j] + "):\n" + arrayOfString2[j]);
      System.out.println("\nsourceCode(" + arrayOfString1[j] + "):\n" + arrayOfString3[j]);
    }
  }
  
  public static void demo2()
  {
    double[] arrayOfDouble = { 5.0D, 0.0D, -0.0D, (0.0D / 0.0D), (0.0D / 0.0D), (0.0D / 0.0D), 4.9E-324D, 1.7976931348623157E+308D, (-1.0D / 0.0D), (1.0D / 0.0D) };
    String[] arrayOfString1 = { "%G", "%1.19G" };
    int i = arrayOfString1.length;
    DenseDoubleMatrix1D localDenseDoubleMatrix1D = new DenseDoubleMatrix1D(arrayOfDouble);
    String[] arrayOfString2 = new String[i];
    for (int j = 0; j < i; j++)
    {
      String str = arrayOfString1[j];
      arrayOfString2[j] = new Formatter(str).toString(localDenseDoubleMatrix1D);
      for (int k = 0; k < localDenseDoubleMatrix1D.size(); k++) {
        System.out.println(String.valueOf(localDenseDoubleMatrix1D.get(k)));
      }
    }
    System.out.println("original:\n" + new Formatter().toString(localDenseDoubleMatrix1D));
    for (j = 0; j < i; j++) {
      System.out.println("\nstring(" + arrayOfString1[j] + "):\n" + arrayOfString2[j]);
    }
  }
  
  public static void demo3(int paramInt, double paramDouble)
  {
    Timer localTimer = new Timer();
    DoubleMatrix2D localDoubleMatrix2D = DoubleFactory2D.dense.make(paramInt, paramInt, paramDouble);
    localTimer.reset().start();
    StringBuffer localStringBuffer = new StringBuffer();
    int i = paramInt;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      j = paramInt;
      for (;;)
      {
        j--;
        if (j < 0) {
          break;
        }
        localStringBuffer.append(localDoubleMatrix2D.getQuick(i, j));
      }
    }
    localStringBuffer = null;
    localTimer.stop().display();
    localTimer.reset().start();
    Former localFormer = new FormerFactory().create("%G");
    localStringBuffer = new StringBuffer();
    int j = paramInt;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      int k = paramInt;
      for (;;)
      {
        k--;
        if (k < 0) {
          break;
        }
        localStringBuffer.append(localFormer.form(localDoubleMatrix2D.getQuick(j, k)));
      }
    }
    localStringBuffer = null;
    localTimer.stop().display();
    localTimer.reset().start();
    String str = new Formatter(null).toString(localDoubleMatrix2D);
    str = null;
    localTimer.stop().display();
    localTimer.reset().start();
    str = new Formatter("%G").toString(localDoubleMatrix2D);
    str = null;
    localTimer.stop().display();
  }
  
  public static void demo4()
  {
    double[][] arrayOfDouble = { { 3.0D, 0.0D, -3.4D, 0.0D }, { 5.1D, 0.0D, 3.0123456789D, 0.0D }, { 16.370000000000001D, 0.0D, 2.5D, 0.0D }, { -16.300000000000001D, 0.0D, -0.0003012345678D, -1.0D }, { 1236.3456788999999D, 0.0D, 7.0D, -1.2D } };
    String[] arrayOfString1 = { "0.1", "0.3", "0.5", "0.7" };
    String[] arrayOfString2 = { "SunJDK1.2.2 classic", "IBMJDK1.1.8", "SunJDK1.3 Hotspot", "other1", "other2" };
    DoubleMatrix2D localDoubleMatrix2D = DoubleFactory2D.dense.make(arrayOfDouble);
    System.out.println("\n\n" + new Formatter("%G").toTitleString(localDoubleMatrix2D, arrayOfString2, arrayOfString1, "rowAxis", "colAxis", "VM Performance: Provider vs. matrix density"));
  }
  
  public static void demo5()
  {
    double[][] arrayOfDouble = { { 3.0D, 0.0D, -3.4D, 0.0D }, { 5.1D, 0.0D, 3.0123456789D, 0.0D }, { 16.370000000000001D, 0.0D, 2.5D, 0.0D }, { -16.300000000000001D, 0.0D, -0.0003012345678D, -1.0D }, { 1236.3456788999999D, 0.0D, 7.0D, -1.2D } };
    String[] arrayOfString1 = { "0.1", "0.3", "0.5", "0.7" };
    String[] arrayOfString2 = { "SunJDK1.2.2 classic", "IBMJDK1.1.8", "SunJDK1.3 Hotspot", "other1", "other2" };
    System.out.println(DoubleFactory2D.dense.make(arrayOfDouble));
    System.out.println(new Formatter("%G").toTitleString(DoubleFactory2D.dense.make(arrayOfDouble), arrayOfString2, arrayOfString1, "vendor", "density", "title"));
  }
  
  public static void demo6()
  {
    double[][] arrayOfDouble = { { 3.0D, 0.0D, -3.4D, 0.0D }, { 5.1D, 0.0D, 3.0123456789D, 0.0D }, { 16.370000000000001D, 0.0D, 2.5D, 0.0D }, { -16.300000000000001D, 0.0D, -0.0003012345678D, -1.0D }, { 1236.3456788999999D, 0.0D, 7.0D, -1.2D } };
    String[] arrayOfString1 = { "W", "X", "Y", "Z" };
    String[] arrayOfString2 = { "SunJDK1.2.2 classic", "IBMJDK1.1.8", "SunJDK1.3 Hotspot", "other1", "other2" };
    System.out.println(new Formatter().toString(DoubleFactory2D.dense.make(arrayOfDouble)));
    System.out.println(new Formatter().toTitleString(DoubleFactory2D.dense.make(arrayOfDouble), arrayOfString2, arrayOfString1, "vendor", "density", "title"));
  }
  
  public static void demo7()
  {
    double[][] arrayOfDouble = { { 5.0D, 10.0D, 20.0D, 40.0D }, { 7.0D, 8.0D, 6.0D, 7.0D }, { 12.0D, 10.0D, 20.0D, 19.0D }, { 3.0D, 1.0D, 5.0D, 6.0D } };
    String[] arrayOfString1 = { "1996", "1997", "1998", "1999" };
    String[] arrayOfString2 = { "PowerBar", "Benzol", "Mercedes", "Sparcling" };
    String str1 = "CPU";
    String str2 = "Year";
    String str3 = "CPU performance over time [nops/sec]";
    BinFunctions1D localBinFunctions1D = BinFunctions1D.functions;
    BinFunction1D[] arrayOfBinFunction1D = { BinFunctions1D.mean, BinFunctions1D.rms, BinFunctions1D.quantile(0.25D), BinFunctions1D.median, BinFunctions1D.quantile(0.75D), BinFunctions1D.stdDev, BinFunctions1D.min, BinFunctions1D.max };
    String str4 = "%1.2G";
    System.out.println(new Formatter(str4).toTitleString(DoubleFactory2D.dense.make(arrayOfDouble), arrayOfString2, arrayOfString1, str1, str2, str3, arrayOfBinFunction1D));
  }
  
  protected String form(DoubleMatrix1D paramDoubleMatrix1D, int paramInt, Former paramFormer)
  {
    return paramFormer.form(paramDoubleMatrix1D.get(paramInt));
  }
  
  protected String form(AbstractMatrix1D paramAbstractMatrix1D, int paramInt, Former paramFormer)
  {
    return form((DoubleMatrix1D)paramAbstractMatrix1D, paramInt, paramFormer);
  }
  
  public String[][] format(DoubleMatrix2D paramDoubleMatrix2D)
  {
    String[][] arrayOfString = new String[paramDoubleMatrix2D.rows()][paramDoubleMatrix2D.columns()];
    int i = paramDoubleMatrix2D.rows();
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      arrayOfString[i] = formatRow(paramDoubleMatrix2D.viewRow(i));
    }
    return arrayOfString;
  }
  
  protected String[][] format(AbstractMatrix2D paramAbstractMatrix2D)
  {
    return format((DoubleMatrix2D)paramAbstractMatrix2D);
  }
  
  protected int indexOfDecimalPoint(String paramString)
  {
    int i = paramString.lastIndexOf('.');
    if (i < 0) {
      i = paramString.lastIndexOf('e');
    }
    if (i < 0) {
      i = paramString.lastIndexOf('E');
    }
    if (i < 0) {
      i = paramString.length();
    }
    return i;
  }
  
  protected int lead(String paramString)
  {
    if (this.alignment.equals("decimal")) {
      return indexOfDecimalPoint(paramString);
    }
    return super.lead(paramString);
  }
  
  public String toSourceCode(DoubleMatrix1D paramDoubleMatrix1D)
  {
    Formatter localFormatter = (Formatter)clone();
    localFormatter.setPrintShape(false);
    localFormatter.setColumnSeparator(", ");
    String str1 = "{";
    String str2 = "};";
    return str1 + localFormatter.toString(paramDoubleMatrix1D) + str2;
  }
  
  public String toSourceCode(DoubleMatrix2D paramDoubleMatrix2D)
  {
    Formatter localFormatter = (Formatter)clone();
    String str1 = blanks(3);
    localFormatter.setPrintShape(false);
    localFormatter.setColumnSeparator(", ");
    localFormatter.setRowSeparator("},\n" + str1 + "{");
    String str2 = "{\n" + str1 + "{";
    String str3 = "}\n};";
    return str2 + localFormatter.toString(paramDoubleMatrix2D) + str3;
  }
  
  public String toSourceCode(DoubleMatrix3D paramDoubleMatrix3D)
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
    return str3 + localFormatter.toString(paramDoubleMatrix3D) + str4;
  }
  
  public String toString(DoubleMatrix1D paramDoubleMatrix1D)
  {
    DoubleMatrix2D localDoubleMatrix2D = paramDoubleMatrix1D.like2D(1, paramDoubleMatrix1D.size());
    localDoubleMatrix2D.viewRow(0).assign(paramDoubleMatrix1D);
    return toString(localDoubleMatrix2D);
  }
  
  public String toString(DoubleMatrix2D paramDoubleMatrix2D)
  {
    return super.toString(paramDoubleMatrix2D);
  }
  
  public String toString(DoubleMatrix3D paramDoubleMatrix3D)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    boolean bool = this.printShape;
    this.printShape = false;
    for (int i = 0; i < paramDoubleMatrix3D.slices(); i++)
    {
      if (i != 0) {
        localStringBuffer.append(this.sliceSeparator);
      }
      localStringBuffer.append(toString(paramDoubleMatrix3D.viewSlice(i)));
    }
    this.printShape = bool;
    if (this.printShape) {
      localStringBuffer.insert(0, shape(paramDoubleMatrix3D) + "\n");
    }
    return localStringBuffer.toString();
  }
  
  protected String toString(AbstractMatrix2D paramAbstractMatrix2D)
  {
    return toString((DoubleMatrix2D)paramAbstractMatrix2D);
  }
  
  protected String toTitleString(DoubleMatrix2D paramDoubleMatrix2D, String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString1, String paramString2, String paramString3)
  {
    if (paramDoubleMatrix2D.size() == 0) {
      return "Empty matrix";
    }
    String[][] arrayOfString = format(paramDoubleMatrix2D);
    align(arrayOfString);
    return new cern.colt.matrix.objectalgo.Formatter().toTitleString(ObjectFactory2D.dense.make(arrayOfString), paramArrayOfString1, paramArrayOfString2, paramString1, paramString2, paramString3);
  }
  
  public String toTitleString(DoubleMatrix2D paramDoubleMatrix2D, String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString1, String paramString2, String paramString3, BinFunction1D[] paramArrayOfBinFunction1D)
  {
    if (paramDoubleMatrix2D.size() == 0) {
      return "Empty matrix";
    }
    if ((paramArrayOfBinFunction1D == null) || (paramArrayOfBinFunction1D.length == 0)) {
      return toTitleString(paramDoubleMatrix2D, paramArrayOfString1, paramArrayOfString2, paramString1, paramString2, paramString3);
    }
    DoubleMatrix2D localDoubleMatrix2D1 = paramDoubleMatrix2D.like(paramDoubleMatrix2D.rows(), paramArrayOfBinFunction1D.length);
    DoubleMatrix2D localDoubleMatrix2D2 = paramDoubleMatrix2D.like(paramArrayOfBinFunction1D.length, paramDoubleMatrix2D.columns());
    Statistic.aggregate(paramDoubleMatrix2D, paramArrayOfBinFunction1D, localDoubleMatrix2D2);
    Statistic.aggregate(paramDoubleMatrix2D.viewDice(), paramArrayOfBinFunction1D, localDoubleMatrix2D1.viewDice());
    DoubleMatrix2D localDoubleMatrix2D3 = paramDoubleMatrix2D.like(paramDoubleMatrix2D.rows() + paramArrayOfBinFunction1D.length, paramDoubleMatrix2D.columns());
    localDoubleMatrix2D3.viewPart(0, 0, paramDoubleMatrix2D.rows(), paramDoubleMatrix2D.columns()).assign(paramDoubleMatrix2D);
    localDoubleMatrix2D3.viewPart(paramDoubleMatrix2D.rows(), 0, paramArrayOfBinFunction1D.length, paramDoubleMatrix2D.columns()).assign(localDoubleMatrix2D2);
    localDoubleMatrix2D2 = null;
    String[][] arrayOfString1 = format(localDoubleMatrix2D3);
    align(arrayOfString1);
    localDoubleMatrix2D3 = null;
    String[][] arrayOfString2 = format(localDoubleMatrix2D1);
    align(arrayOfString2);
    localDoubleMatrix2D1 = null;
    ObjectMatrix2D localObjectMatrix2D = ObjectFactory2D.dense.make(paramDoubleMatrix2D.rows() + paramArrayOfBinFunction1D.length, paramDoubleMatrix2D.columns() + paramArrayOfBinFunction1D.length + 1);
    localObjectMatrix2D.viewPart(0, 0, paramDoubleMatrix2D.rows() + paramArrayOfBinFunction1D.length, paramDoubleMatrix2D.columns()).assign(arrayOfString1);
    localObjectMatrix2D.viewColumn(paramDoubleMatrix2D.columns()).assign("|");
    localObjectMatrix2D.viewPart(0, paramDoubleMatrix2D.columns() + 1, paramDoubleMatrix2D.rows(), paramArrayOfBinFunction1D.length).assign(arrayOfString2);
    arrayOfString1 = (String[][])null;
    arrayOfString2 = (String[][])null;
    if (paramArrayOfString2 != null)
    {
      localObject = new ObjectArrayList(paramArrayOfString2);
      ((ObjectArrayList)localObject).add("|");
      for (i = 0; i < paramArrayOfBinFunction1D.length; i++) {
        ((ObjectArrayList)localObject).add(paramArrayOfBinFunction1D[i].name());
      }
      paramArrayOfString2 = new String[((ObjectArrayList)localObject).size()];
      ((ObjectArrayList)localObject).toArray(paramArrayOfString2);
    }
    if (paramArrayOfString1 != null)
    {
      localObject = new ObjectArrayList(paramArrayOfString1);
      for (i = 0; i < paramArrayOfBinFunction1D.length; i++) {
        ((ObjectArrayList)localObject).add(paramArrayOfBinFunction1D[i].name());
      }
      paramArrayOfString1 = new String[((ObjectArrayList)localObject).size()];
      ((ObjectArrayList)localObject).toArray(paramArrayOfString1);
    }
    Object localObject = new cern.colt.matrix.objectalgo.Formatter().toTitleString(localObjectMatrix2D, paramArrayOfString1, paramArrayOfString2, paramString1, paramString2, paramString3);
    int i = ((String)localObject).length() + 1;
    int j = i;
    int k = Math.max(0, paramString1 == null ? 0 : paramString1.length() - paramDoubleMatrix2D.rows() - paramArrayOfBinFunction1D.length);
    for (int m = 0; m < paramArrayOfBinFunction1D.length + 1 + k; m++)
    {
      j = i;
      i = ((String)localObject).lastIndexOf(this.rowSeparator, i - 1);
    }
    StringBuffer localStringBuffer = new StringBuffer((String)localObject);
    localStringBuffer.insert(j, this.rowSeparator + repeat('-', j - i - 1));
    return localStringBuffer.toString();
  }
  
  public String toTitleString(DoubleMatrix3D paramDoubleMatrix3D, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String paramString1, String paramString2, String paramString3, String paramString4, BinFunction1D[] paramArrayOfBinFunction1D)
  {
    if (paramDoubleMatrix3D.size() == 0) {
      return "Empty matrix";
    }
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < paramDoubleMatrix3D.slices(); i++)
    {
      if (i != 0) {
        localStringBuffer.append(this.sliceSeparator);
      }
      localStringBuffer.append(toTitleString(paramDoubleMatrix3D.viewSlice(i), paramArrayOfString2, paramArrayOfString3, paramString2, paramString3, paramString4 + "\n" + paramString1 + "=" + paramArrayOfString1[i], paramArrayOfBinFunction1D));
    }
    return localStringBuffer.toString();
  }
  
  private String xtoTitleString(DoubleMatrix3D paramDoubleMatrix3D, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    if (paramDoubleMatrix3D.size() == 0) {
      return "Empty matrix";
    }
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < paramDoubleMatrix3D.slices(); i++)
    {
      if (i != 0) {
        localStringBuffer.append(this.sliceSeparator);
      }
      localStringBuffer.append(toTitleString(paramDoubleMatrix3D.viewSlice(i), paramArrayOfString2, paramArrayOfString3, paramString2, paramString3, paramString4 + "\n" + paramString1 + "=" + paramArrayOfString1[i]));
    }
    return localStringBuffer.toString();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.doublealgo.Formatter
 * JD-Core Version:    0.7.0.1
 */