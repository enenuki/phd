package cern.colt.matrix;

import cern.colt.PersistentObject;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.impl.RCDoubleMatrix2D;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import cern.jet.math.Functions;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.sampling.RandomSamplingAssistant;
import java.io.PrintStream;

public class DoubleFactory2D
  extends PersistentObject
{
  public static final DoubleFactory2D dense = new DoubleFactory2D();
  public static final DoubleFactory2D sparse = new DoubleFactory2D();
  public static final DoubleFactory2D rowCompressed = new DoubleFactory2D();
  
  public DoubleMatrix2D appendColumns(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2)
  {
    if (paramDoubleMatrix2D2.rows() > paramDoubleMatrix2D1.rows()) {
      paramDoubleMatrix2D2 = paramDoubleMatrix2D2.viewPart(0, 0, paramDoubleMatrix2D1.rows(), paramDoubleMatrix2D2.columns());
    } else if (paramDoubleMatrix2D2.rows() < paramDoubleMatrix2D1.rows()) {
      paramDoubleMatrix2D1 = paramDoubleMatrix2D1.viewPart(0, 0, paramDoubleMatrix2D2.rows(), paramDoubleMatrix2D1.columns());
    }
    int i = paramDoubleMatrix2D1.columns();
    int j = paramDoubleMatrix2D2.columns();
    int k = paramDoubleMatrix2D1.rows();
    DoubleMatrix2D localDoubleMatrix2D = make(k, i + j);
    localDoubleMatrix2D.viewPart(0, 0, k, i).assign(paramDoubleMatrix2D1);
    localDoubleMatrix2D.viewPart(0, i, k, j).assign(paramDoubleMatrix2D2);
    return localDoubleMatrix2D;
  }
  
  public DoubleMatrix2D appendRows(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2)
  {
    if (paramDoubleMatrix2D2.columns() > paramDoubleMatrix2D1.columns()) {
      paramDoubleMatrix2D2 = paramDoubleMatrix2D2.viewPart(0, 0, paramDoubleMatrix2D2.rows(), paramDoubleMatrix2D1.columns());
    } else if (paramDoubleMatrix2D2.columns() < paramDoubleMatrix2D1.columns()) {
      paramDoubleMatrix2D1 = paramDoubleMatrix2D1.viewPart(0, 0, paramDoubleMatrix2D1.rows(), paramDoubleMatrix2D2.columns());
    }
    int i = paramDoubleMatrix2D1.rows();
    int j = paramDoubleMatrix2D2.rows();
    int k = paramDoubleMatrix2D1.columns();
    DoubleMatrix2D localDoubleMatrix2D = make(i + j, k);
    localDoubleMatrix2D.viewPart(0, 0, i, k).assign(paramDoubleMatrix2D1);
    localDoubleMatrix2D.viewPart(i, 0, j, k).assign(paramDoubleMatrix2D2);
    return localDoubleMatrix2D;
  }
  
  public DoubleMatrix2D ascending(int paramInt1, int paramInt2)
  {
    Functions localFunctions = Functions.functions;
    return descending(paramInt1, paramInt2).assign(Functions.chain(Functions.neg, Functions.minus(paramInt2 * paramInt1)));
  }
  
  protected static void checkRectangularShape(double[][] paramArrayOfDouble)
  {
    int i = -1;
    int j = paramArrayOfDouble.length;
    do
    {
      do
      {
        j--;
        if (j < 0) {
          break;
        }
      } while (paramArrayOfDouble[j] == null);
      if (i == -1) {
        i = paramArrayOfDouble[j].length;
      }
    } while (paramArrayOfDouble[j].length == i);
    throw new IllegalArgumentException("All rows of array must have same number of columns.");
  }
  
  protected static void checkRectangularShape(DoubleMatrix2D[][] paramArrayOfDoubleMatrix2D)
  {
    int i = -1;
    int j = paramArrayOfDoubleMatrix2D.length;
    do
    {
      do
      {
        j--;
        if (j < 0) {
          break;
        }
      } while (paramArrayOfDoubleMatrix2D[j] == null);
      if (i == -1) {
        i = paramArrayOfDoubleMatrix2D[j].length;
      }
    } while (paramArrayOfDoubleMatrix2D[j].length == i);
    throw new IllegalArgumentException("All rows of array must have same number of columns.");
  }
  
  public DoubleMatrix2D compose(DoubleMatrix2D[][] paramArrayOfDoubleMatrix2D)
  {
    checkRectangularShape(paramArrayOfDoubleMatrix2D);
    int i = paramArrayOfDoubleMatrix2D.length;
    DoubleMatrix2D localDoubleMatrix2D1 = 0;
    if (paramArrayOfDoubleMatrix2D.length > 0) {
      localDoubleMatrix2D1 = paramArrayOfDoubleMatrix2D[0].length;
    }
    DoubleMatrix2D localDoubleMatrix2D2 = make(0, 0);
    if ((i == 0) || (localDoubleMatrix2D1 == 0)) {
      return localDoubleMatrix2D2;
    }
    int[] arrayOfInt1 = new int[localDoubleMatrix2D1];
    int j = localDoubleMatrix2D1;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      k = 0;
      m = i;
      for (;;)
      {
        m--;
        if (m < 0) {
          break;
        }
        localDoubleMatrix2D3 = paramArrayOfDoubleMatrix2D[m][j];
        if (localDoubleMatrix2D3 != null)
        {
          int n = localDoubleMatrix2D3.columns();
          if ((k > 0) && (n > 0) && (n != k)) {
            throw new IllegalArgumentException("Different number of columns.");
          }
          k = Math.max(k, n);
        }
      }
      arrayOfInt1[j] = k;
    }
    int[] arrayOfInt2 = new int[i];
    int k = i;
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      m = 0;
      localDoubleMatrix2D3 = localDoubleMatrix2D1;
      for (;;)
      {
        localDoubleMatrix2D3--;
        if (localDoubleMatrix2D3 < 0) {
          break;
        }
        DoubleMatrix2D localDoubleMatrix2D4 = paramArrayOfDoubleMatrix2D[k][localDoubleMatrix2D3];
        if (localDoubleMatrix2D4 != null)
        {
          i2 = localDoubleMatrix2D4.rows();
          if ((m > 0) && (i2 > 0) && (i2 != m)) {
            throw new IllegalArgumentException("Different number of rows.");
          }
          m = Math.max(m, i2);
        }
      }
      arrayOfInt2[k] = m;
    }
    k = 0;
    int m = i;
    for (;;)
    {
      m--;
      if (m < 0) {
        break;
      }
      k += arrayOfInt2[m];
    }
    m = 0;
    DoubleMatrix2D localDoubleMatrix2D3 = localDoubleMatrix2D1;
    for (;;)
    {
      localDoubleMatrix2D3--;
      if (localDoubleMatrix2D3 < 0) {
        break;
      }
      m += arrayOfInt1[localDoubleMatrix2D3];
    }
    localDoubleMatrix2D3 = make(k, m);
    int i1 = 0;
    for (int i2 = 0; i2 < i; i2++)
    {
      int i3 = 0;
      for (int i4 = 0; i4 < localDoubleMatrix2D1; i4++)
      {
        DoubleMatrix2D localDoubleMatrix2D5 = paramArrayOfDoubleMatrix2D[i2][i4];
        if (localDoubleMatrix2D5 != null) {
          localDoubleMatrix2D3.viewPart(i1, i3, localDoubleMatrix2D5.rows(), localDoubleMatrix2D5.columns()).assign(localDoubleMatrix2D5);
        }
        i3 += arrayOfInt1[i4];
      }
      i1 += arrayOfInt2[i2];
    }
    return localDoubleMatrix2D3;
  }
  
  public DoubleMatrix2D composeDiagonal(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2)
  {
    int i = paramDoubleMatrix2D1.rows();
    int j = paramDoubleMatrix2D1.columns();
    int k = paramDoubleMatrix2D2.rows();
    int m = paramDoubleMatrix2D2.columns();
    DoubleMatrix2D localDoubleMatrix2D = make(i + k, j + m);
    localDoubleMatrix2D.viewPart(0, 0, i, j).assign(paramDoubleMatrix2D1);
    localDoubleMatrix2D.viewPart(i, j, k, m).assign(paramDoubleMatrix2D2);
    return localDoubleMatrix2D;
  }
  
  public DoubleMatrix2D composeDiagonal(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2, DoubleMatrix2D paramDoubleMatrix2D3)
  {
    DoubleMatrix2D localDoubleMatrix2D = make(paramDoubleMatrix2D1.rows() + paramDoubleMatrix2D2.rows() + paramDoubleMatrix2D3.rows(), paramDoubleMatrix2D1.columns() + paramDoubleMatrix2D2.columns() + paramDoubleMatrix2D3.columns());
    localDoubleMatrix2D.viewPart(0, 0, paramDoubleMatrix2D1.rows(), paramDoubleMatrix2D1.columns()).assign(paramDoubleMatrix2D1);
    localDoubleMatrix2D.viewPart(paramDoubleMatrix2D1.rows(), paramDoubleMatrix2D1.columns(), paramDoubleMatrix2D2.rows(), paramDoubleMatrix2D2.columns()).assign(paramDoubleMatrix2D2);
    localDoubleMatrix2D.viewPart(paramDoubleMatrix2D1.rows() + paramDoubleMatrix2D2.rows(), paramDoubleMatrix2D1.columns() + paramDoubleMatrix2D2.columns(), paramDoubleMatrix2D3.rows(), paramDoubleMatrix2D3.columns()).assign(paramDoubleMatrix2D3);
    return localDoubleMatrix2D;
  }
  
  public void decompose(DoubleMatrix2D[][] paramArrayOfDoubleMatrix2D, DoubleMatrix2D paramDoubleMatrix2D)
  {
    checkRectangularShape(paramArrayOfDoubleMatrix2D);
    int i = paramArrayOfDoubleMatrix2D.length;
    DoubleMatrix2D localDoubleMatrix2D1 = 0;
    if (paramArrayOfDoubleMatrix2D.length > 0) {
      localDoubleMatrix2D1 = paramArrayOfDoubleMatrix2D[0].length;
    }
    if ((i == 0) || (localDoubleMatrix2D1 == 0)) {
      return;
    }
    int[] arrayOfInt1 = new int[localDoubleMatrix2D1];
    int j = localDoubleMatrix2D1;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      k = 0;
      m = i;
      for (;;)
      {
        m--;
        if (m < 0) {
          break;
        }
        localDoubleMatrix2D2 = paramArrayOfDoubleMatrix2D[m][j];
        if (localDoubleMatrix2D2 != null)
        {
          int i1 = localDoubleMatrix2D2.columns();
          if ((k > 0) && (i1 > 0) && (i1 != k)) {
            throw new IllegalArgumentException("Different number of columns.");
          }
          k = Math.max(k, i1);
        }
      }
      arrayOfInt1[j] = k;
    }
    int[] arrayOfInt2 = new int[i];
    int k = i;
    int i3;
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      m = 0;
      localDoubleMatrix2D2 = localDoubleMatrix2D1;
      for (;;)
      {
        localDoubleMatrix2D2--;
        if (localDoubleMatrix2D2 < 0) {
          break;
        }
        DoubleMatrix2D localDoubleMatrix2D3 = paramArrayOfDoubleMatrix2D[k][localDoubleMatrix2D2];
        if (localDoubleMatrix2D3 != null)
        {
          i3 = localDoubleMatrix2D3.rows();
          if ((m > 0) && (i3 > 0) && (i3 != m)) {
            throw new IllegalArgumentException("Different number of rows.");
          }
          m = Math.max(m, i3);
        }
      }
      arrayOfInt2[k] = m;
    }
    k = 0;
    int m = i;
    for (;;)
    {
      m--;
      if (m < 0) {
        break;
      }
      k += arrayOfInt2[m];
    }
    m = 0;
    DoubleMatrix2D localDoubleMatrix2D2 = localDoubleMatrix2D1;
    for (;;)
    {
      localDoubleMatrix2D2--;
      if (localDoubleMatrix2D2 < 0) {
        break;
      }
      m += arrayOfInt1[localDoubleMatrix2D2];
    }
    if ((paramDoubleMatrix2D.rows() < k) || (paramDoubleMatrix2D.columns() < m)) {
      throw new IllegalArgumentException("Parts larger than matrix.");
    }
    int n = 0;
    for (int i2 = 0; i2 < i; i2++)
    {
      i3 = 0;
      for (int i4 = 0; i4 < localDoubleMatrix2D1; i4++)
      {
        DoubleMatrix2D localDoubleMatrix2D4 = paramArrayOfDoubleMatrix2D[i2][i4];
        if (localDoubleMatrix2D4 != null) {
          localDoubleMatrix2D4.assign(paramDoubleMatrix2D.viewPart(n, i3, localDoubleMatrix2D4.rows(), localDoubleMatrix2D4.columns()));
        }
        i3 += arrayOfInt1[i4];
      }
      n += arrayOfInt2[i2];
    }
  }
  
  public void demo1()
  {
    System.out.println("\n\n");
    DoubleMatrix2D[][] arrayOfDoubleMatrix2D;1 = { { null, make(2, 2, 1.0D), null }, { make(4, 4, 2.0D), null, make(4, 3, 3.0D) }, { null, make(2, 2, 4.0D), null } };
    System.out.println("\n" + compose(arrayOfDoubleMatrix2D;1));
    DoubleMatrix2D[][] arrayOfDoubleMatrix2D;2 = { { identity(3), null }, { null, identity(3).viewColumnFlip() }, { identity(3).viewRowFlip(), null } };
    System.out.println("\n" + compose(arrayOfDoubleMatrix2D;2));
    DoubleMatrix2D localDoubleMatrix2D1 = ascending(2, 2);
    DoubleMatrix2D localDoubleMatrix2D2 = descending(2, 2);
    Object localObject = null;
    DoubleMatrix2D[][] arrayOfDoubleMatrix2D;3 = { { localDoubleMatrix2D1, localObject, localDoubleMatrix2D1, localObject }, { localObject, localDoubleMatrix2D1, localObject, localDoubleMatrix2D2 } };
    System.out.println("\n" + compose(arrayOfDoubleMatrix2D;3));
  }
  
  public void demo2()
  {
    System.out.println("\n\n");
    Object localObject = null;
    DoubleMatrix2D localDoubleMatrix2D2 = make(2, 2, 1.0D);
    DoubleMatrix2D localDoubleMatrix2D3 = make(4, 4, 2.0D);
    DoubleMatrix2D localDoubleMatrix2D4 = make(4, 3, 3.0D);
    DoubleMatrix2D localDoubleMatrix2D5 = make(2, 2, 4.0D);
    DoubleMatrix2D[][] arrayOfDoubleMatrix2D; = { { localObject, localDoubleMatrix2D2, localObject }, { localDoubleMatrix2D3, localObject, localDoubleMatrix2D4 }, { localObject, localDoubleMatrix2D5, localObject } };
    DoubleMatrix2D localDoubleMatrix2D1 = compose(arrayOfDoubleMatrix2D;);
    System.out.println("\n" + localDoubleMatrix2D1);
    localDoubleMatrix2D2.assign(9.0D);
    localDoubleMatrix2D3.assign(9.0D);
    localDoubleMatrix2D4.assign(9.0D);
    localDoubleMatrix2D5.assign(9.0D);
    decompose(arrayOfDoubleMatrix2D;, localDoubleMatrix2D1);
    System.out.println(localDoubleMatrix2D2);
    System.out.println(localDoubleMatrix2D3);
    System.out.println(localDoubleMatrix2D4);
    System.out.println(localDoubleMatrix2D5);
  }
  
  public DoubleMatrix2D descending(int paramInt1, int paramInt2)
  {
    DoubleMatrix2D localDoubleMatrix2D = make(paramInt1, paramInt2);
    int i = 0;
    int j = paramInt1;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      int k = paramInt2;
      for (;;)
      {
        k--;
        if (k < 0) {
          break;
        }
        localDoubleMatrix2D.setQuick(j, k, i++);
      }
    }
    return localDoubleMatrix2D;
  }
  
  public DoubleMatrix2D diagonal(DoubleMatrix1D paramDoubleMatrix1D)
  {
    int i = paramDoubleMatrix1D.size();
    DoubleMatrix2D localDoubleMatrix2D = make(i, i);
    int j = i;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      localDoubleMatrix2D.setQuick(j, j, paramDoubleMatrix1D.getQuick(j));
    }
    return localDoubleMatrix2D;
  }
  
  public DoubleMatrix1D diagonal(DoubleMatrix2D paramDoubleMatrix2D)
  {
    int i = Math.min(paramDoubleMatrix2D.rows(), paramDoubleMatrix2D.columns());
    DoubleMatrix1D localDoubleMatrix1D = make1D(i);
    int j = i;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      localDoubleMatrix1D.setQuick(j, paramDoubleMatrix2D.getQuick(j, j));
    }
    return localDoubleMatrix1D;
  }
  
  public DoubleMatrix2D identity(int paramInt)
  {
    DoubleMatrix2D localDoubleMatrix2D = make(paramInt, paramInt);
    int i = paramInt;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      localDoubleMatrix2D.setQuick(i, i, 1.0D);
    }
    return localDoubleMatrix2D;
  }
  
  public DoubleMatrix2D make(double[][] paramArrayOfDouble)
  {
    if (this == sparse) {
      return new SparseDoubleMatrix2D(paramArrayOfDouble);
    }
    return new DenseDoubleMatrix2D(paramArrayOfDouble);
  }
  
  public DoubleMatrix2D make(double[] paramArrayOfDouble, int paramInt)
  {
    int i = paramInt != 0 ? paramArrayOfDouble.length / paramInt : 0;
    if (paramInt * i != paramArrayOfDouble.length) {
      throw new IllegalArgumentException("Array length must be a multiple of m.");
    }
    DoubleMatrix2D localDoubleMatrix2D = make(paramInt, i);
    for (int j = 0; j < paramInt; j++) {
      for (int k = 0; k < i; k++) {
        localDoubleMatrix2D.setQuick(j, k, paramArrayOfDouble[(j + k * paramInt)]);
      }
    }
    return localDoubleMatrix2D;
  }
  
  public DoubleMatrix2D make(int paramInt1, int paramInt2)
  {
    if (this == sparse) {
      return new SparseDoubleMatrix2D(paramInt1, paramInt2);
    }
    if (this == rowCompressed) {
      return new RCDoubleMatrix2D(paramInt1, paramInt2);
    }
    return new DenseDoubleMatrix2D(paramInt1, paramInt2);
  }
  
  public DoubleMatrix2D make(int paramInt1, int paramInt2, double paramDouble)
  {
    if (paramDouble == 0.0D) {
      return make(paramInt1, paramInt2);
    }
    return make(paramInt1, paramInt2).assign(paramDouble);
  }
  
  protected DoubleMatrix1D make1D(int paramInt)
  {
    return make(0, 0).like1D(paramInt);
  }
  
  public DoubleMatrix2D random(int paramInt1, int paramInt2)
  {
    return make(paramInt1, paramInt2).assign(Functions.random());
  }
  
  public DoubleMatrix2D repeat(DoubleMatrix2D paramDoubleMatrix2D, int paramInt1, int paramInt2)
  {
    int i = paramDoubleMatrix2D.rows();
    int j = paramDoubleMatrix2D.columns();
    DoubleMatrix2D localDoubleMatrix2D = make(i * paramInt1, j * paramInt2);
    int k = paramInt1;
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      int m = paramInt2;
      for (;;)
      {
        m--;
        if (m < 0) {
          break;
        }
        localDoubleMatrix2D.viewPart(i * k, j * m, i, j).assign(paramDoubleMatrix2D);
      }
    }
    return localDoubleMatrix2D;
  }
  
  public DoubleMatrix2D sample(int paramInt1, int paramInt2, double paramDouble1, double paramDouble2)
  {
    DoubleMatrix2D localDoubleMatrix2D = make(paramInt1, paramInt2);
    sample(localDoubleMatrix2D, paramDouble1, paramDouble2);
    return localDoubleMatrix2D;
  }
  
  public DoubleMatrix2D sample(DoubleMatrix2D paramDoubleMatrix2D, double paramDouble1, double paramDouble2)
  {
    int i = paramDoubleMatrix2D.rows();
    int j = paramDoubleMatrix2D.columns();
    double d = 1.E-009D;
    if ((paramDouble2 < 0.0D - d) || (paramDouble2 > 1.0D + d)) {
      throw new IllegalArgumentException();
    }
    if (paramDouble2 < 0.0D) {
      paramDouble2 = 0.0D;
    }
    if (paramDouble2 > 1.0D) {
      paramDouble2 = 1.0D;
    }
    paramDoubleMatrix2D.assign(0.0D);
    int k = i * j;
    int m = (int)Math.round(k * paramDouble2);
    if (m == 0) {
      return paramDoubleMatrix2D;
    }
    RandomSamplingAssistant localRandomSamplingAssistant = new RandomSamplingAssistant(m, k, new MersenneTwister());
    for (int n = 0; n < k; n++) {
      if (localRandomSamplingAssistant.sampleNextElement())
      {
        int i1 = n / j;
        int i2 = n % j;
        paramDoubleMatrix2D.set(i1, i2, paramDouble1);
      }
    }
    return paramDoubleMatrix2D;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.DoubleFactory2D
 * JD-Core Version:    0.7.0.1
 */