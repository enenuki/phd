package hep.aida.ref;

import cern.colt.list.ObjectArrayList;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.DoubleMatrix3D;
import cern.colt.matrix.doublealgo.Formatter;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix3D;
import cern.colt.matrix.impl.Former;
import cern.colt.matrix.impl.FormerFactory;
import hep.aida.IAxis;
import hep.aida.IHistogram1D;
import hep.aida.IHistogram2D;
import hep.aida.IHistogram3D;
import hep.aida.bin.BinFunction1D;
import hep.aida.bin.BinFunctions1D;

public class Converter
{
  public double[] edges(IAxis paramIAxis)
  {
    int i = paramIAxis.bins();
    double[] arrayOfDouble = new double[i + 1];
    for (int j = 0; j < i; j++) {
      arrayOfDouble[j] = paramIAxis.binLowerEdge(j);
    }
    arrayOfDouble[i] = paramIAxis.upperEdge();
    return arrayOfDouble;
  }
  
  String form(Former paramFormer, double paramDouble)
  {
    return paramFormer.form(paramDouble);
  }
  
  protected double[] toArrayErrors(IHistogram1D paramIHistogram1D)
  {
    int i = paramIHistogram1D.xAxis().bins();
    double[] arrayOfDouble = new double[i];
    int j = i;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      arrayOfDouble[j] = paramIHistogram1D.binError(j);
    }
    return arrayOfDouble;
  }
  
  protected double[][] toArrayErrors(IHistogram2D paramIHistogram2D)
  {
    int i = paramIHistogram2D.xAxis().bins();
    int j = paramIHistogram2D.yAxis().bins();
    double[][] arrayOfDouble = new double[i][j];
    int k = j;
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      int m = i;
      for (;;)
      {
        m--;
        if (m < 0) {
          break;
        }
        arrayOfDouble[m][k] = paramIHistogram2D.binError(m, k);
      }
    }
    return arrayOfDouble;
  }
  
  protected double[] toArrayHeights(IHistogram1D paramIHistogram1D)
  {
    int i = paramIHistogram1D.xAxis().bins();
    double[] arrayOfDouble = new double[i];
    int j = i;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      arrayOfDouble[j] = paramIHistogram1D.binHeight(j);
    }
    return arrayOfDouble;
  }
  
  protected double[][] toArrayHeights(IHistogram2D paramIHistogram2D)
  {
    int i = paramIHistogram2D.xAxis().bins();
    int j = paramIHistogram2D.yAxis().bins();
    double[][] arrayOfDouble = new double[i][j];
    int k = j;
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      int m = i;
      for (;;)
      {
        m--;
        if (m < 0) {
          break;
        }
        arrayOfDouble[m][k] = paramIHistogram2D.binHeight(m, k);
      }
    }
    return arrayOfDouble;
  }
  
  protected double[][][] toArrayHeights(IHistogram3D paramIHistogram3D)
  {
    int i = paramIHistogram3D.xAxis().bins();
    int j = paramIHistogram3D.yAxis().bins();
    int k = paramIHistogram3D.zAxis().bins();
    double[][][] arrayOfDouble = new double[i][j][k];
    int m = i;
    m--;
    if (m >= 0)
    {
      int n = j;
      for (;;)
      {
        n--;
        if (n < 0) {
          break;
        }
        int i1 = k;
        for (;;)
        {
          i1--;
          if (i1 < 0) {
            break;
          }
          arrayOfDouble[m][n][i1] = paramIHistogram3D.binHeight(m, n, i1);
        }
      }
    }
    return arrayOfDouble;
  }
  
  protected static String toString(double[] paramArrayOfDouble)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("[");
    int i = paramArrayOfDouble.length - 1;
    for (int j = 0; j <= i; j++)
    {
      localStringBuffer.append(paramArrayOfDouble[j]);
      if (j < i) {
        localStringBuffer.append(", ");
      }
    }
    localStringBuffer.append("]");
    return localStringBuffer.toString();
  }
  
  public String toString(IAxis paramIAxis)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("Range: [" + paramIAxis.lowerEdge() + "," + paramIAxis.upperEdge() + ")");
    localStringBuffer.append(", Bins: " + paramIAxis.bins());
    localStringBuffer.append(", Bin edges: " + toString(edges(paramIAxis)) + "\n");
    return localStringBuffer.toString();
  }
  
  public String toString(IHistogram1D paramIHistogram1D)
  {
    String str1 = null;
    String str2 = null;
    BinFunction1D[] arrayOfBinFunction1D = null;
    String str3 = "%G";
    Former localFormer = new FormerFactory().create(str3);
    String str4 = System.getProperty("line.separator");
    int[] arrayOfInt = paramIHistogram1D.minMaxBins();
    String str5 = paramIHistogram1D.title() + ":" + str4 + "   Entries=" + form(localFormer, paramIHistogram1D.entries()) + ", ExtraEntries=" + form(localFormer, paramIHistogram1D.extraEntries()) + str4 + "   Mean=" + form(localFormer, paramIHistogram1D.mean()) + ", Rms=" + form(localFormer, paramIHistogram1D.rms()) + str4 + "   MinBinHeight=" + form(localFormer, paramIHistogram1D.binHeight(arrayOfInt[0])) + ", MaxBinHeight=" + form(localFormer, paramIHistogram1D.binHeight(arrayOfInt[1])) + str4 + "   Axis: " + "Bins=" + form(localFormer, paramIHistogram1D.xAxis().bins()) + ", Min=" + form(localFormer, paramIHistogram1D.xAxis().lowerEdge()) + ", Max=" + form(localFormer, paramIHistogram1D.xAxis().upperEdge());
    String[] arrayOfString1 = new String[paramIHistogram1D.xAxis().bins()];
    for (int i = 0; i < paramIHistogram1D.xAxis().bins(); i++) {
      arrayOfString1[i] = form(localFormer, paramIHistogram1D.xAxis().binLowerEdge(i));
    }
    String[] arrayOfString2 = null;
    DenseDoubleMatrix2D localDenseDoubleMatrix2D = new DenseDoubleMatrix2D(1, paramIHistogram1D.xAxis().bins());
    localDenseDoubleMatrix2D.viewRow(0).assign(toArrayHeights(paramIHistogram1D));
    return str5 + str4 + "Heights:" + str4 + new Formatter().toTitleString(localDenseDoubleMatrix2D, arrayOfString2, arrayOfString1, str2, str1, null, arrayOfBinFunction1D);
  }
  
  public String toString(IHistogram2D paramIHistogram2D)
  {
    String str1 = "X";
    String str2 = "Y";
    BinFunction1D[] arrayOfBinFunction1D = { BinFunctions1D.sum };
    String str3 = "%G";
    Former localFormer = new FormerFactory().create(str3);
    String str4 = System.getProperty("line.separator");
    int[] arrayOfInt = paramIHistogram2D.minMaxBins();
    String str5 = paramIHistogram2D.title() + ":" + str4 + "   Entries=" + form(localFormer, paramIHistogram2D.entries()) + ", ExtraEntries=" + form(localFormer, paramIHistogram2D.extraEntries()) + str4 + "   MeanX=" + form(localFormer, paramIHistogram2D.meanX()) + ", RmsX=" + form(localFormer, paramIHistogram2D.rmsX()) + str4 + "   MeanY=" + form(localFormer, paramIHistogram2D.meanY()) + ", RmsY=" + form(localFormer, paramIHistogram2D.rmsX()) + str4 + "   MinBinHeight=" + form(localFormer, paramIHistogram2D.binHeight(arrayOfInt[0], arrayOfInt[1])) + ", MaxBinHeight=" + form(localFormer, paramIHistogram2D.binHeight(arrayOfInt[2], arrayOfInt[3])) + str4 + "   xAxis: " + "Bins=" + form(localFormer, paramIHistogram2D.xAxis().bins()) + ", Min=" + form(localFormer, paramIHistogram2D.xAxis().lowerEdge()) + ", Max=" + form(localFormer, paramIHistogram2D.xAxis().upperEdge()) + str4 + "   yAxis: " + "Bins=" + form(localFormer, paramIHistogram2D.yAxis().bins()) + ", Min=" + form(localFormer, paramIHistogram2D.yAxis().lowerEdge()) + ", Max=" + form(localFormer, paramIHistogram2D.yAxis().upperEdge());
    String[] arrayOfString1 = new String[paramIHistogram2D.xAxis().bins()];
    for (int i = 0; i < paramIHistogram2D.xAxis().bins(); i++) {
      arrayOfString1[i] = form(localFormer, paramIHistogram2D.xAxis().binLowerEdge(i));
    }
    String[] arrayOfString2 = new String[paramIHistogram2D.yAxis().bins()];
    for (int j = 0; j < paramIHistogram2D.yAxis().bins(); j++) {
      arrayOfString2[j] = form(localFormer, paramIHistogram2D.yAxis().binLowerEdge(j));
    }
    new ObjectArrayList(arrayOfString2).reverse();
    Object localObject = new DenseDoubleMatrix2D(toArrayHeights(paramIHistogram2D));
    localObject = ((DoubleMatrix2D)localObject).viewDice().viewRowFlip();
    return str5 + str4 + "Heights:" + str4 + new Formatter().toTitleString((DoubleMatrix2D)localObject, arrayOfString2, arrayOfString1, str2, str1, null, arrayOfBinFunction1D);
  }
  
  public String toString(IHistogram3D paramIHistogram3D)
  {
    String str1 = "X";
    String str2 = "Y";
    String str3 = "Z";
    BinFunction1D[] arrayOfBinFunction1D = { BinFunctions1D.sum };
    String str4 = "%G";
    Former localFormer = new FormerFactory().create(str4);
    String str5 = System.getProperty("line.separator");
    int[] arrayOfInt = paramIHistogram3D.minMaxBins();
    String str6 = paramIHistogram3D.title() + ":" + str5 + "   Entries=" + form(localFormer, paramIHistogram3D.entries()) + ", ExtraEntries=" + form(localFormer, paramIHistogram3D.extraEntries()) + str5 + "   MeanX=" + form(localFormer, paramIHistogram3D.meanX()) + ", RmsX=" + form(localFormer, paramIHistogram3D.rmsX()) + str5 + "   MeanY=" + form(localFormer, paramIHistogram3D.meanY()) + ", RmsY=" + form(localFormer, paramIHistogram3D.rmsX()) + str5 + "   MeanZ=" + form(localFormer, paramIHistogram3D.meanZ()) + ", RmsZ=" + form(localFormer, paramIHistogram3D.rmsZ()) + str5 + "   MinBinHeight=" + form(localFormer, paramIHistogram3D.binHeight(arrayOfInt[0], arrayOfInt[1], arrayOfInt[2])) + ", MaxBinHeight=" + form(localFormer, paramIHistogram3D.binHeight(arrayOfInt[3], arrayOfInt[4], arrayOfInt[5])) + str5 + "   xAxis: " + "Bins=" + form(localFormer, paramIHistogram3D.xAxis().bins()) + ", Min=" + form(localFormer, paramIHistogram3D.xAxis().lowerEdge()) + ", Max=" + form(localFormer, paramIHistogram3D.xAxis().upperEdge()) + str5 + "   yAxis: " + "Bins=" + form(localFormer, paramIHistogram3D.yAxis().bins()) + ", Min=" + form(localFormer, paramIHistogram3D.yAxis().lowerEdge()) + ", Max=" + form(localFormer, paramIHistogram3D.yAxis().upperEdge()) + str5 + "   zAxis: " + "Bins=" + form(localFormer, paramIHistogram3D.zAxis().bins()) + ", Min=" + form(localFormer, paramIHistogram3D.zAxis().lowerEdge()) + ", Max=" + form(localFormer, paramIHistogram3D.zAxis().upperEdge());
    String[] arrayOfString1 = new String[paramIHistogram3D.xAxis().bins()];
    for (int i = 0; i < paramIHistogram3D.xAxis().bins(); i++) {
      arrayOfString1[i] = form(localFormer, paramIHistogram3D.xAxis().binLowerEdge(i));
    }
    String[] arrayOfString2 = new String[paramIHistogram3D.yAxis().bins()];
    for (int j = 0; j < paramIHistogram3D.yAxis().bins(); j++) {
      arrayOfString2[j] = form(localFormer, paramIHistogram3D.yAxis().binLowerEdge(j));
    }
    new ObjectArrayList(arrayOfString2).reverse();
    String[] arrayOfString3 = new String[paramIHistogram3D.zAxis().bins()];
    for (int k = 0; k < paramIHistogram3D.zAxis().bins(); k++) {
      arrayOfString3[k] = form(localFormer, paramIHistogram3D.zAxis().binLowerEdge(k));
    }
    new ObjectArrayList(arrayOfString3).reverse();
    Object localObject = new DenseDoubleMatrix3D(toArrayHeights(paramIHistogram3D));
    localObject = ((DoubleMatrix3D)localObject).viewDice(2, 1, 0).viewSliceFlip().viewRowFlip();
    return str6 + str5 + "Heights:" + str5 + new Formatter().toTitleString((DoubleMatrix3D)localObject, arrayOfString3, arrayOfString2, arrayOfString1, str3, str2, str1, "", arrayOfBinFunction1D);
  }
  
  public String toXML(IHistogram1D paramIHistogram1D)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = System.getProperty("line.separator");
    localStringBuffer.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>");
    localStringBuffer.append(str);
    localStringBuffer.append("<!DOCTYPE plotML SYSTEM \"plotML.dtd\">");
    localStringBuffer.append(str);
    localStringBuffer.append("<plotML>");
    localStringBuffer.append(str);
    localStringBuffer.append("<plot>");
    localStringBuffer.append(str);
    localStringBuffer.append("<dataArea>");
    localStringBuffer.append(str);
    localStringBuffer.append("<data1d>");
    localStringBuffer.append(str);
    localStringBuffer.append("<bins1d title=\"" + paramIHistogram1D.title() + "\">");
    localStringBuffer.append(str);
    for (int i = 0; i < paramIHistogram1D.xAxis().bins(); i++)
    {
      localStringBuffer.append(paramIHistogram1D.binEntries(i) + "," + paramIHistogram1D.binError(i));
      localStringBuffer.append(str);
    }
    localStringBuffer.append("</bins1d>");
    localStringBuffer.append(str);
    localStringBuffer.append("<binnedDataAxisAttributes type=\"double\" axis=\"x0\"");
    localStringBuffer.append(" min=\"" + paramIHistogram1D.xAxis().lowerEdge() + "\"");
    localStringBuffer.append(" max=\"" + paramIHistogram1D.xAxis().upperEdge() + "\"");
    localStringBuffer.append(" numberOfBins=\"" + paramIHistogram1D.xAxis().bins() + "\"");
    localStringBuffer.append("/>");
    localStringBuffer.append(str);
    localStringBuffer.append("<statistics>");
    localStringBuffer.append(str);
    localStringBuffer.append("<statistic name=\"Entries\" value=\"" + paramIHistogram1D.entries() + "\"/>");
    localStringBuffer.append(str);
    localStringBuffer.append("<statistic name=\"Underflow\" value=\"" + paramIHistogram1D.binEntries(-2) + "\"/>");
    localStringBuffer.append(str);
    localStringBuffer.append("<statistic name=\"Overflow\" value=\"" + paramIHistogram1D.binEntries(-1) + "\"/>");
    localStringBuffer.append(str);
    if (!Double.isNaN(paramIHistogram1D.mean()))
    {
      localStringBuffer.append("<statistic name=\"Mean\" value=\"" + paramIHistogram1D.mean() + "\"/>");
      localStringBuffer.append(str);
    }
    if (!Double.isNaN(paramIHistogram1D.rms()))
    {
      localStringBuffer.append("<statistic name=\"RMS\" value=\"" + paramIHistogram1D.rms() + "\"/>");
      localStringBuffer.append(str);
    }
    localStringBuffer.append("</statistics>");
    localStringBuffer.append(str);
    localStringBuffer.append("</data1d>");
    localStringBuffer.append(str);
    localStringBuffer.append("</dataArea>");
    localStringBuffer.append(str);
    localStringBuffer.append("</plot>");
    localStringBuffer.append(str);
    localStringBuffer.append("</plotML>");
    localStringBuffer.append(str);
    return localStringBuffer.toString();
  }
  
  public String toXML(IHistogram2D paramIHistogram2D)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = System.getProperty("line.separator");
    localStringBuffer.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>");
    localStringBuffer.append(str);
    localStringBuffer.append("<!DOCTYPE plotML SYSTEM \"plotML.dtd\">");
    localStringBuffer.append(str);
    localStringBuffer.append("<plotML>");
    localStringBuffer.append(str);
    localStringBuffer.append("<plot>");
    localStringBuffer.append(str);
    localStringBuffer.append("<dataArea>");
    localStringBuffer.append(str);
    localStringBuffer.append("<data2d type=\"xxx\">");
    localStringBuffer.append(str);
    localStringBuffer.append("<bins2d title=\"" + paramIHistogram2D.title() + "\" xSize=\"" + paramIHistogram2D.xAxis().bins() + "\" ySize=\"" + paramIHistogram2D.yAxis().bins() + "\">");
    localStringBuffer.append(str);
    for (int i = 0; i < paramIHistogram2D.xAxis().bins(); i++) {
      for (int j = 0; j < paramIHistogram2D.yAxis().bins(); j++)
      {
        localStringBuffer.append(paramIHistogram2D.binEntries(i, j) + "," + paramIHistogram2D.binError(i, j));
        localStringBuffer.append(str);
      }
    }
    localStringBuffer.append("</bins2d>");
    localStringBuffer.append(str);
    localStringBuffer.append("<binnedDataAxisAttributes type=\"double\" axis=\"x0\"");
    localStringBuffer.append(" min=\"" + paramIHistogram2D.xAxis().lowerEdge() + "\"");
    localStringBuffer.append(" max=\"" + paramIHistogram2D.xAxis().upperEdge() + "\"");
    localStringBuffer.append(" numberOfBins=\"" + paramIHistogram2D.xAxis().bins() + "\"");
    localStringBuffer.append("/>");
    localStringBuffer.append(str);
    localStringBuffer.append("<binnedDataAxisAttributes type=\"double\" axis=\"y0\"");
    localStringBuffer.append(" min=\"" + paramIHistogram2D.yAxis().lowerEdge() + "\"");
    localStringBuffer.append(" max=\"" + paramIHistogram2D.yAxis().upperEdge() + "\"");
    localStringBuffer.append(" numberOfBins=\"" + paramIHistogram2D.yAxis().bins() + "\"");
    localStringBuffer.append("/>");
    localStringBuffer.append(str);
    localStringBuffer.append("</data2d>");
    localStringBuffer.append(str);
    localStringBuffer.append("</dataArea>");
    localStringBuffer.append(str);
    localStringBuffer.append("</plot>");
    localStringBuffer.append(str);
    localStringBuffer.append("</plotML>");
    localStringBuffer.append(str);
    return localStringBuffer.toString();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hep.aida.ref.Converter
 * JD-Core Version:    0.7.0.1
 */