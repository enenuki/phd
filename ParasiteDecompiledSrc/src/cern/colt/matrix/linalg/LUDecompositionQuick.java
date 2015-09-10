package cern.colt.matrix.linalg;

import cern.colt.list.IntArrayList;
import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.jet.math.Mult;
import cern.jet.math.PlusMult;
import java.io.Serializable;

public class LUDecompositionQuick
  implements Serializable
{
  static final long serialVersionUID = 1020L;
  protected DoubleMatrix2D LU;
  protected int pivsign;
  protected int[] piv;
  protected boolean isNonSingular;
  protected Algebra algebra;
  protected transient double[] workDouble;
  protected transient int[] work1;
  protected transient int[] work2;
  
  public LUDecompositionQuick()
  {
    this(Property.DEFAULT.tolerance());
  }
  
  public LUDecompositionQuick(double paramDouble)
  {
    this.algebra = new Algebra(paramDouble);
  }
  
  public void decompose(DoubleMatrix2D paramDoubleMatrix2D)
  {
    this.LU = paramDoubleMatrix2D;
    int i = paramDoubleMatrix2D.rows();
    int j = paramDoubleMatrix2D.columns();
    if ((this.piv == null) || (this.piv.length != i)) {
      this.piv = new int[i];
    }
    int k = i;
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      this.piv[k] = k;
    }
    this.pivsign = 1;
    if (i * j == 0)
    {
      setLU(this.LU);
      return;
    }
    DoubleMatrix1D[] arrayOfDoubleMatrix1D = new DoubleMatrix1D[i];
    for (int m = 0; m < i; m++) {
      arrayOfDoubleMatrix1D[m] = this.LU.viewRow(m);
    }
    IntArrayList localIntArrayList = new IntArrayList();
    DoubleMatrix1D localDoubleMatrix1D = this.LU.viewColumn(0).like();
    Mult localMult = Mult.mult(0.0D);
    for (int n = 0; n < j; n++)
    {
      localDoubleMatrix1D.assign(this.LU.viewColumn(n));
      int i1 = i / 10;
      localDoubleMatrix1D.getNonZeros(localIntArrayList, null, i1);
      int i2 = localIntArrayList.size();
      int i3 = i2 < i1 ? 1 : 0;
      double d4;
      for (int i4 = 0; i4 < i; i4++)
      {
        int i5 = Math.min(i4, n);
        double d3;
        if (i3 != 0) {
          d3 = arrayOfDoubleMatrix1D[i4].zDotProduct(localDoubleMatrix1D, 0, i5, localIntArrayList);
        } else {
          d3 = arrayOfDoubleMatrix1D[i4].zDotProduct(localDoubleMatrix1D, 0, i5);
        }
        d4 = localDoubleMatrix1D.getQuick(i4);
        double d5 = d4 - d3;
        localDoubleMatrix1D.setQuick(i4, d5);
        this.LU.setQuick(i4, n, d5);
        if (i3 != 0)
        {
          if ((d4 == 0.0D) && (d5 != 0.0D))
          {
            int i8 = localIntArrayList.binarySearch(i4);
            i8 = -i8 - 1;
            localIntArrayList.beforeInsert(i8, i4);
          }
          if ((d4 != 0.0D) && (d5 == 0.0D)) {
            localIntArrayList.remove(localIntArrayList.binarySearch(i4));
          }
        }
      }
      i4 = n;
      if (i4 < i)
      {
        double d1 = Math.abs(localDoubleMatrix1D.getQuick(i4));
        for (int i7 = n + 1; i7 < i; i7++)
        {
          d4 = Math.abs(localDoubleMatrix1D.getQuick(i7));
          if (d4 > d1)
          {
            i4 = i7;
            d1 = d4;
          }
        }
      }
      if (i4 != n)
      {
        arrayOfDoubleMatrix1D[i4].swap(arrayOfDoubleMatrix1D[n]);
        int i6 = this.piv[i4];
        this.piv[i4] = this.piv[n];
        this.piv[n] = i6;
        this.pivsign = (-this.pivsign);
      }
      double d2;
      if ((n < i) && ((d2 = this.LU.getQuick(n, n)) != 0.0D))
      {
        localMult.multiplicator = (1.0D / d2);
        this.LU.viewColumn(n).viewPart(n + 1, i - (n + 1)).assign(localMult);
      }
    }
    setLU(this.LU);
  }
  
  public void decompose(DoubleMatrix2D paramDoubleMatrix2D, int paramInt)
  {
    if ((!this.algebra.property().isSquare(paramDoubleMatrix2D)) || (paramInt < 0) || (paramInt > 2))
    {
      decompose(paramDoubleMatrix2D);
      return;
    }
    this.LU = paramDoubleMatrix2D;
    int i = paramDoubleMatrix2D.rows();
    int j = paramDoubleMatrix2D.columns();
    if ((this.piv == null) || (this.piv.length != i)) {
      this.piv = new int[i];
    }
    int k = i;
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      this.piv[k] = k;
    }
    this.pivsign = 1;
    if (i * j == 0)
    {
      setLU(paramDoubleMatrix2D);
      return;
    }
    if (paramInt == 2)
    {
      if (j > 1) {
        paramDoubleMatrix2D.setQuick(1, 0, paramDoubleMatrix2D.getQuick(1, 0) / paramDoubleMatrix2D.getQuick(0, 0));
      }
      for (k = 1; k < j; k++)
      {
        double d = paramDoubleMatrix2D.getQuick(k, k) - paramDoubleMatrix2D.getQuick(k, k - 1) * paramDoubleMatrix2D.getQuick(k - 1, k);
        paramDoubleMatrix2D.setQuick(k, k, d);
        if (k < j - 1) {
          paramDoubleMatrix2D.setQuick(k + 1, k, paramDoubleMatrix2D.getQuick(k + 1, k) / d);
        }
      }
    }
    setLU(paramDoubleMatrix2D);
  }
  
  public double det()
  {
    int i = m();
    int j = n();
    if (i != j) {
      throw new IllegalArgumentException("Matrix must be square.");
    }
    if (!isNonsingular()) {
      return 0.0D;
    }
    double d = this.pivsign;
    for (int k = 0; k < j; k++) {
      d *= this.LU.getQuick(k, k);
    }
    return d;
  }
  
  protected double[] getDoublePivot()
  {
    int i = m();
    double[] arrayOfDouble = new double[i];
    for (int j = 0; j < i; j++) {
      arrayOfDouble[j] = this.piv[j];
    }
    return arrayOfDouble;
  }
  
  public DoubleMatrix2D getL()
  {
    return lowerTriangular(this.LU.copy());
  }
  
  public DoubleMatrix2D getLU()
  {
    return this.LU.copy();
  }
  
  public int[] getPivot()
  {
    return this.piv;
  }
  
  public DoubleMatrix2D getU()
  {
    return upperTriangular(this.LU.copy());
  }
  
  public boolean isNonsingular()
  {
    return this.isNonSingular;
  }
  
  protected boolean isNonsingular(DoubleMatrix2D paramDoubleMatrix2D)
  {
    int i = paramDoubleMatrix2D.rows();
    int j = paramDoubleMatrix2D.columns();
    double d = this.algebra.property().tolerance();
    int k = Math.min(j, i);
    do
    {
      k--;
      if (k < 0) {
        break;
      }
    } while (Math.abs(paramDoubleMatrix2D.getQuick(k, k)) > d);
    return false;
    return true;
  }
  
  protected DoubleMatrix2D lowerTriangular(DoubleMatrix2D paramDoubleMatrix2D)
  {
    int i = paramDoubleMatrix2D.rows();
    int j = paramDoubleMatrix2D.columns();
    int k = Math.min(i, j);
    int m = k;
    for (;;)
    {
      m--;
      if (m < 0) {
        break;
      }
      int n = k;
      for (;;)
      {
        n--;
        if (n < 0) {
          break;
        }
        if (m < n) {
          paramDoubleMatrix2D.setQuick(m, n, 0.0D);
        } else if (m == n) {
          paramDoubleMatrix2D.setQuick(m, n, 1.0D);
        }
      }
    }
    if (j > i) {
      paramDoubleMatrix2D.viewPart(0, k, i, j - k).assign(0.0D);
    }
    return paramDoubleMatrix2D;
  }
  
  protected int m()
  {
    return this.LU.rows();
  }
  
  protected int n()
  {
    return this.LU.columns();
  }
  
  public void setLU(DoubleMatrix2D paramDoubleMatrix2D)
  {
    this.LU = paramDoubleMatrix2D;
    this.isNonSingular = isNonsingular(paramDoubleMatrix2D);
  }
  
  public void solve(DoubleMatrix1D paramDoubleMatrix1D)
  {
    this.algebra.property().checkRectangular(this.LU);
    int i = m();
    int j = n();
    if (paramDoubleMatrix1D.size() != i) {
      throw new IllegalArgumentException("Matrix dimensions must agree.");
    }
    if (!isNonsingular()) {
      throw new IllegalArgumentException("Matrix is singular.");
    }
    if ((this.workDouble == null) || (this.workDouble.length < i)) {
      this.workDouble = new double[i];
    }
    this.algebra.permute(paramDoubleMatrix1D, this.piv, this.workDouble);
    if (i * j == 0) {
      return;
    }
    double d1;
    int m;
    double d2;
    for (int k = 0; k < j; k++)
    {
      d1 = paramDoubleMatrix1D.getQuick(k);
      if (d1 != 0.0D) {
        for (m = k + 1; m < j; m++)
        {
          d2 = this.LU.getQuick(m, k);
          if (d2 != 0.0D) {
            paramDoubleMatrix1D.setQuick(m, paramDoubleMatrix1D.getQuick(m) - d1 * d2);
          }
        }
      }
    }
    for (k = j - 1; k >= 0; k--)
    {
      paramDoubleMatrix1D.setQuick(k, paramDoubleMatrix1D.getQuick(k) / this.LU.getQuick(k, k));
      d1 = paramDoubleMatrix1D.getQuick(k);
      if (d1 != 0.0D) {
        for (m = 0; m < k; m++)
        {
          d2 = this.LU.getQuick(m, k);
          if (d2 != 0.0D) {
            paramDoubleMatrix1D.setQuick(m, paramDoubleMatrix1D.getQuick(m) - d1 * d2);
          }
        }
      }
    }
  }
  
  public void solve(DoubleMatrix2D paramDoubleMatrix2D)
  {
    this.algebra.property().checkRectangular(this.LU);
    int i = m();
    int j = n();
    if (paramDoubleMatrix2D.rows() != i) {
      throw new IllegalArgumentException("Matrix row dimensions must agree.");
    }
    if (!isNonsingular()) {
      throw new IllegalArgumentException("Matrix is singular.");
    }
    if ((this.work1 == null) || (this.work1.length < i)) {
      this.work1 = new int[i];
    }
    this.algebra.permuteRows(paramDoubleMatrix2D, this.piv, this.work1);
    if (i * j == 0) {
      return;
    }
    int k = paramDoubleMatrix2D.columns();
    DoubleMatrix1D[] arrayOfDoubleMatrix1D = new DoubleMatrix1D[j];
    for (int m = 0; m < j; m++) {
      arrayOfDoubleMatrix1D[m] = paramDoubleMatrix2D.viewRow(m);
    }
    Mult localMult = Mult.div(0.0D);
    PlusMult localPlusMult = PlusMult.minusMult(0.0D);
    IntArrayList localIntArrayList = new IntArrayList();
    DoubleMatrix1D localDoubleMatrix1D = DoubleFactory1D.dense.make(k);
    int i1;
    int i2;
    int i3;
    int i4;
    for (int n = 0; n < j; n++)
    {
      localDoubleMatrix1D.assign(arrayOfDoubleMatrix1D[n]);
      i1 = k / 10;
      localDoubleMatrix1D.getNonZeros(localIntArrayList, null, i1);
      i2 = localIntArrayList.size();
      i3 = i2 < i1 ? 1 : 0;
      for (i4 = n + 1; i4 < j; i4++)
      {
        localPlusMult.multiplicator = (-this.LU.getQuick(i4, n));
        if (localPlusMult.multiplicator != 0.0D) {
          if (i3 != 0) {
            arrayOfDoubleMatrix1D[i4].assign(localDoubleMatrix1D, localPlusMult, localIntArrayList);
          } else {
            arrayOfDoubleMatrix1D[i4].assign(localDoubleMatrix1D, localPlusMult);
          }
        }
      }
    }
    for (n = j - 1; n >= 0; n--)
    {
      localMult.multiplicator = (1.0D / this.LU.getQuick(n, n));
      arrayOfDoubleMatrix1D[n].assign(localMult);
      if (localDoubleMatrix1D == null) {
        localDoubleMatrix1D = DoubleFactory1D.dense.make(paramDoubleMatrix2D.columns());
      }
      localDoubleMatrix1D.assign(arrayOfDoubleMatrix1D[n]);
      i1 = k / 10;
      localDoubleMatrix1D.getNonZeros(localIntArrayList, null, i1);
      i2 = localIntArrayList.size();
      i3 = i2 < i1 ? 1 : 0;
      for (i4 = 0; i4 < n; i4++)
      {
        localPlusMult.multiplicator = (-this.LU.getQuick(i4, n));
        if (localPlusMult.multiplicator != 0.0D) {
          if (i3 != 0) {
            arrayOfDoubleMatrix1D[i4].assign(localDoubleMatrix1D, localPlusMult, localIntArrayList);
          } else {
            arrayOfDoubleMatrix1D[i4].assign(localDoubleMatrix1D, localPlusMult);
          }
        }
      }
    }
  }
  
  private void solveOld(DoubleMatrix2D paramDoubleMatrix2D)
  {
    this.algebra.property().checkRectangular(this.LU);
    int i = m();
    int j = n();
    if (paramDoubleMatrix2D.rows() != i) {
      throw new IllegalArgumentException("Matrix row dimensions must agree.");
    }
    if (!isNonsingular()) {
      throw new IllegalArgumentException("Matrix is singular.");
    }
    int k = paramDoubleMatrix2D.columns();
    if ((this.work1 == null) || (this.work1.length < i)) {
      this.work1 = new int[i];
    }
    this.algebra.permuteRows(paramDoubleMatrix2D, this.piv, this.work1);
    int i2;
    for (int m = 0; m < j; m++) {
      for (int n = m + 1; n < j; n++)
      {
        double d2 = this.LU.getQuick(n, m);
        if (d2 != 0.0D) {
          for (i2 = 0; i2 < k; i2++) {
            paramDoubleMatrix2D.setQuick(n, i2, paramDoubleMatrix2D.getQuick(n, i2) - paramDoubleMatrix2D.getQuick(m, i2) * d2);
          }
        }
      }
    }
    for (m = j - 1; m >= 0; m--)
    {
      double d1 = 1.0D / this.LU.getQuick(m, m);
      if (d1 != 1.0D) {
        for (i1 = 0; i1 < k; i1++) {
          paramDoubleMatrix2D.setQuick(m, i1, paramDoubleMatrix2D.getQuick(m, i1) * d1);
        }
      }
      for (int i1 = 0; i1 < m; i1++)
      {
        d1 = this.LU.getQuick(i1, m);
        if (d1 != 0.0D) {
          for (i2 = 0; i2 < k; i2++) {
            paramDoubleMatrix2D.setQuick(i1, i2, paramDoubleMatrix2D.getQuick(i1, i2) - paramDoubleMatrix2D.getQuick(m, i2) * d1);
          }
        }
      }
    }
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = "Illegal operation or error: ";
    localStringBuffer.append("-----------------------------------------------------------------------------\n");
    localStringBuffer.append("LUDecompositionQuick(A) --> isNonSingular(A), det(A), pivot, L, U, inverse(A)\n");
    localStringBuffer.append("-----------------------------------------------------------------------------\n");
    localStringBuffer.append("isNonSingular = ");
    try
    {
      localStringBuffer.append(String.valueOf(isNonsingular()));
    }
    catch (IllegalArgumentException localIllegalArgumentException1)
    {
      localStringBuffer.append(str + localIllegalArgumentException1.getMessage());
    }
    localStringBuffer.append("\ndet = ");
    try
    {
      localStringBuffer.append(String.valueOf(det()));
    }
    catch (IllegalArgumentException localIllegalArgumentException2)
    {
      localStringBuffer.append(str + localIllegalArgumentException2.getMessage());
    }
    localStringBuffer.append("\npivot = ");
    try
    {
      localStringBuffer.append(String.valueOf(new IntArrayList(getPivot())));
    }
    catch (IllegalArgumentException localIllegalArgumentException3)
    {
      localStringBuffer.append(str + localIllegalArgumentException3.getMessage());
    }
    localStringBuffer.append("\n\nL = ");
    try
    {
      localStringBuffer.append(String.valueOf(getL()));
    }
    catch (IllegalArgumentException localIllegalArgumentException4)
    {
      localStringBuffer.append(str + localIllegalArgumentException4.getMessage());
    }
    localStringBuffer.append("\n\nU = ");
    try
    {
      localStringBuffer.append(String.valueOf(getU()));
    }
    catch (IllegalArgumentException localIllegalArgumentException5)
    {
      localStringBuffer.append(str + localIllegalArgumentException5.getMessage());
    }
    localStringBuffer.append("\n\ninverse(A) = ");
    DoubleMatrix2D localDoubleMatrix2D = DoubleFactory2D.dense.identity(this.LU.rows());
    try
    {
      solve(localDoubleMatrix2D);
      localStringBuffer.append(String.valueOf(localDoubleMatrix2D));
    }
    catch (IllegalArgumentException localIllegalArgumentException6)
    {
      localStringBuffer.append(str + localIllegalArgumentException6.getMessage());
    }
    return localStringBuffer.toString();
  }
  
  protected DoubleMatrix2D upperTriangular(DoubleMatrix2D paramDoubleMatrix2D)
  {
    int i = paramDoubleMatrix2D.rows();
    int j = paramDoubleMatrix2D.columns();
    int k = Math.min(i, j);
    int m = k;
    for (;;)
    {
      m--;
      if (m < 0) {
        break;
      }
      int n = k;
      for (;;)
      {
        n--;
        if (n < 0) {
          break;
        }
        if (m > n) {
          paramDoubleMatrix2D.setQuick(m, n, 0.0D);
        }
      }
    }
    if (j < i) {
      paramDoubleMatrix2D.viewPart(k, 0, i - k, j).assign(0.0D);
    }
    return paramDoubleMatrix2D;
  }
  
  private double[] xgetDoublePivot()
  {
    int i = m();
    double[] arrayOfDouble = new double[i];
    for (int j = 0; j < i; j++) {
      arrayOfDouble[j] = this.piv[j];
    }
    return arrayOfDouble;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.linalg.LUDecompositionQuick
 * JD-Core Version:    0.7.0.1
 */