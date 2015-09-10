package cern.colt.matrix.linalg;

import cern.colt.GenericSorting;
import cern.colt.PersistentObject;
import cern.colt.Swapper;
import cern.colt.function.IntComparator;
import cern.colt.list.ObjectArrayList;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.DoubleMatrix3D;
import cern.colt.matrix.doublealgo.Formatter;
import cern.jet.math.Functions;

public class Property
  extends PersistentObject
{
  public static final Property DEFAULT = new Property(1.E-009D);
  public static final Property ZERO = new Property(0.0D);
  public static final Property TWELVE = new Property(1.0E-012D);
  protected double tolerance;
  
  private Property()
  {
    this(1.E-009D);
  }
  
  public Property(double paramDouble)
  {
    this.tolerance = Math.abs(paramDouble);
  }
  
  protected static String blanks(int paramInt)
  {
    if (paramInt < 0) {
      paramInt = 0;
    }
    StringBuffer localStringBuffer = new StringBuffer(paramInt);
    for (int i = 0; i < paramInt; i++) {
      localStringBuffer.append(' ');
    }
    return localStringBuffer.toString();
  }
  
  public void checkRectangular(DoubleMatrix2D paramDoubleMatrix2D)
  {
    if (paramDoubleMatrix2D.rows() < paramDoubleMatrix2D.columns()) {
      throw new IllegalArgumentException("Matrix must be rectangular: " + Formatter.shape(paramDoubleMatrix2D));
    }
  }
  
  public void checkSquare(DoubleMatrix2D paramDoubleMatrix2D)
  {
    if (paramDoubleMatrix2D.rows() != paramDoubleMatrix2D.columns()) {
      throw new IllegalArgumentException("Matrix must be square: " + Formatter.shape(paramDoubleMatrix2D));
    }
  }
  
  public double density(DoubleMatrix2D paramDoubleMatrix2D)
  {
    return paramDoubleMatrix2D.cardinality() / paramDoubleMatrix2D.size();
  }
  
  public boolean equals(DoubleMatrix1D paramDoubleMatrix1D, double paramDouble)
  {
    if (paramDoubleMatrix1D == null) {
      return false;
    }
    double d1 = tolerance();
    int i = paramDoubleMatrix1D.size();
    double d3;
    do
    {
      i--;
      if (i < 0) {
        break;
      }
      double d2 = paramDoubleMatrix1D.getQuick(i);
      d3 = Math.abs(paramDouble - d2);
      if ((d3 != d3) && (((paramDouble != paramDouble) && (d2 != d2)) || (paramDouble == d2))) {
        d3 = 0.0D;
      }
    } while (d3 <= d1);
    return false;
    return true;
  }
  
  public boolean equals(DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2)
  {
    if (paramDoubleMatrix1D1 == paramDoubleMatrix1D2) {
      return true;
    }
    if ((paramDoubleMatrix1D1 == null) || (paramDoubleMatrix1D2 == null)) {
      return false;
    }
    int i = paramDoubleMatrix1D1.size();
    if (i != paramDoubleMatrix1D2.size()) {
      return false;
    }
    double d1 = tolerance();
    int j = i;
    double d4;
    do
    {
      j--;
      if (j < 0) {
        break;
      }
      double d2 = paramDoubleMatrix1D1.getQuick(j);
      double d3 = paramDoubleMatrix1D2.getQuick(j);
      d4 = Math.abs(d3 - d2);
      if ((d4 != d4) && (((d3 != d3) && (d2 != d2)) || (d3 == d2))) {
        d4 = 0.0D;
      }
    } while (d4 <= d1);
    return false;
    return true;
  }
  
  public boolean equals(DoubleMatrix2D paramDoubleMatrix2D, double paramDouble)
  {
    if (paramDoubleMatrix2D == null) {
      return false;
    }
    int i = paramDoubleMatrix2D.rows();
    int j = paramDoubleMatrix2D.columns();
    double d1 = tolerance();
    int k = i;
    double d3;
    do
    {
      k--;
      int m;
      do
      {
        if (k < 0) {
          break;
        }
        m = j;
        m--;
      } while (m < 0);
      double d2 = paramDoubleMatrix2D.getQuick(k, m);
      d3 = Math.abs(paramDouble - d2);
      if ((d3 != d3) && (((paramDouble != paramDouble) && (d2 != d2)) || (paramDouble == d2))) {
        d3 = 0.0D;
      }
    } while (d3 <= d1);
    return false;
    return true;
  }
  
  public boolean equals(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2)
  {
    if (paramDoubleMatrix2D1 == paramDoubleMatrix2D2) {
      return true;
    }
    if ((paramDoubleMatrix2D1 == null) || (paramDoubleMatrix2D2 == null)) {
      return false;
    }
    int i = paramDoubleMatrix2D1.rows();
    int j = paramDoubleMatrix2D1.columns();
    if ((j != paramDoubleMatrix2D2.columns()) || (i != paramDoubleMatrix2D2.rows())) {
      return false;
    }
    double d1 = tolerance();
    int k = i;
    double d4;
    do
    {
      k--;
      int m;
      do
      {
        if (k < 0) {
          break;
        }
        m = j;
        m--;
      } while (m < 0);
      double d2 = paramDoubleMatrix2D1.getQuick(k, m);
      double d3 = paramDoubleMatrix2D2.getQuick(k, m);
      d4 = Math.abs(d3 - d2);
      if ((d4 != d4) && (((d3 != d3) && (d2 != d2)) || (d3 == d2))) {
        d4 = 0.0D;
      }
    } while (d4 <= d1);
    return false;
    return true;
  }
  
  public boolean equals(DoubleMatrix3D paramDoubleMatrix3D, double paramDouble)
  {
    if (paramDoubleMatrix3D == null) {
      return false;
    }
    int i = paramDoubleMatrix3D.rows();
    int j = paramDoubleMatrix3D.columns();
    double d1 = tolerance();
    int k = paramDoubleMatrix3D.slices();
    label30:
    k--;
    if (k >= 0)
    {
      int m = i;
      label54:
      double d3;
      do
      {
        m--;
        break label54;
        if (m < 0) {
          break label30;
        }
        int n = j;
        n--;
        if (n < 0) {
          break;
        }
        double d2 = paramDoubleMatrix3D.getQuick(k, m, n);
        d3 = Math.abs(paramDouble - d2);
        if ((d3 != d3) && (((paramDouble != paramDouble) && (d2 != d2)) || (paramDouble == d2))) {
          d3 = 0.0D;
        }
      } while (d3 <= d1);
      return false;
    }
    return true;
  }
  
  public boolean equals(DoubleMatrix3D paramDoubleMatrix3D1, DoubleMatrix3D paramDoubleMatrix3D2)
  {
    if (paramDoubleMatrix3D1 == paramDoubleMatrix3D2) {
      return true;
    }
    if ((paramDoubleMatrix3D1 == null) || (paramDoubleMatrix3D2 == null)) {
      return false;
    }
    int i = paramDoubleMatrix3D1.slices();
    int j = paramDoubleMatrix3D1.rows();
    int k = paramDoubleMatrix3D1.columns();
    if ((k != paramDoubleMatrix3D2.columns()) || (j != paramDoubleMatrix3D2.rows()) || (i != paramDoubleMatrix3D2.slices())) {
      return false;
    }
    double d1 = tolerance();
    int m = i;
    label71:
    m--;
    if (m >= 0)
    {
      int n = j;
      label95:
      double d4;
      do
      {
        n--;
        break label95;
        if (n < 0) {
          break label71;
        }
        int i1 = k;
        i1--;
        if (i1 < 0) {
          break;
        }
        double d2 = paramDoubleMatrix3D1.getQuick(m, n, i1);
        double d3 = paramDoubleMatrix3D2.getQuick(m, n, i1);
        d4 = Math.abs(d3 - d2);
        if ((d4 != d4) && (((d3 != d3) && (d2 != d2)) || (d3 == d2))) {
          d4 = 0.0D;
        }
      } while (d4 <= d1);
      return false;
    }
    return true;
  }
  
  public void generateNonSingular(DoubleMatrix2D paramDoubleMatrix2D)
  {
    checkSquare(paramDoubleMatrix2D);
    Functions localFunctions = Functions.functions;
    int i = Math.min(paramDoubleMatrix2D.rows(), paramDoubleMatrix2D.columns());
    int j = i;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      paramDoubleMatrix2D.setQuick(j, j, 0.0D);
    }
    j = i;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      double d1 = paramDoubleMatrix2D.viewRow(j).aggregate(Functions.plus, Functions.abs);
      double d2 = paramDoubleMatrix2D.viewColumn(j).aggregate(Functions.plus, Functions.abs);
      paramDoubleMatrix2D.setQuick(j, j, Math.max(d1, d2) + j + 1.0D);
    }
  }
  
  protected static String get(ObjectArrayList paramObjectArrayList, int paramInt)
  {
    return (String)paramObjectArrayList.get(paramInt);
  }
  
  public boolean isDiagonal(DoubleMatrix2D paramDoubleMatrix2D)
  {
    double d = tolerance();
    int i = paramDoubleMatrix2D.rows();
    int j = paramDoubleMatrix2D.columns();
    int k = i;
    int m;
    do
    {
      k--;
      do
      {
        if (k < 0) {
          break;
        }
        m = j;
        m--;
      } while (m < 0);
    } while ((k == m) || (Math.abs(paramDoubleMatrix2D.getQuick(k, m)) <= d));
    return false;
    return true;
  }
  
  public boolean isDiagonallyDominantByColumn(DoubleMatrix2D paramDoubleMatrix2D)
  {
    Functions localFunctions = Functions.functions;
    double d1 = tolerance();
    int i = Math.min(paramDoubleMatrix2D.rows(), paramDoubleMatrix2D.columns());
    int j = i;
    double d2;
    do
    {
      j--;
      if (j < 0) {
        break;
      }
      d2 = Math.abs(paramDoubleMatrix2D.getQuick(j, j));
      d2 += d2;
    } while (d2 > paramDoubleMatrix2D.viewColumn(j).aggregate(Functions.plus, Functions.abs));
    return false;
    return true;
  }
  
  public boolean isDiagonallyDominantByRow(DoubleMatrix2D paramDoubleMatrix2D)
  {
    Functions localFunctions = Functions.functions;
    double d1 = tolerance();
    int i = Math.min(paramDoubleMatrix2D.rows(), paramDoubleMatrix2D.columns());
    int j = i;
    double d2;
    do
    {
      j--;
      if (j < 0) {
        break;
      }
      d2 = Math.abs(paramDoubleMatrix2D.getQuick(j, j));
      d2 += d2;
    } while (d2 > paramDoubleMatrix2D.viewRow(j).aggregate(Functions.plus, Functions.abs));
    return false;
    return true;
  }
  
  public boolean isIdentity(DoubleMatrix2D paramDoubleMatrix2D)
  {
    double d1 = tolerance();
    int i = paramDoubleMatrix2D.rows();
    int j = paramDoubleMatrix2D.columns();
    int k = i;
    label33:
    double d2;
    do
    {
      do
      {
        k--;
        break label33;
        int m;
        do
        {
          if (k < 0) {
            break;
          }
          m = j;
          m--;
        } while (m < 0);
        d2 = paramDoubleMatrix2D.getQuick(k, m);
        if (k != m) {
          break;
        }
      } while (Math.abs(1.0D - d2) < d1);
      return false;
    } while (Math.abs(d2) <= d1);
    return false;
    return true;
  }
  
  public boolean isLowerBidiagonal(DoubleMatrix2D paramDoubleMatrix2D)
  {
    double d = tolerance();
    int i = paramDoubleMatrix2D.rows();
    int j = paramDoubleMatrix2D.columns();
    int k = i;
    int m;
    do
    {
      k--;
      do
      {
        if (k < 0) {
          break;
        }
        m = j;
        m--;
      } while (m < 0);
    } while ((k == m) || (k == m + 1) || (Math.abs(paramDoubleMatrix2D.getQuick(k, m)) <= d));
    return false;
    return true;
  }
  
  public boolean isLowerTriangular(DoubleMatrix2D paramDoubleMatrix2D)
  {
    double d = tolerance();
    int i = paramDoubleMatrix2D.rows();
    int j = paramDoubleMatrix2D.columns();
    int k = j;
    int m;
    do
    {
      k--;
      do
      {
        if (k < 0) {
          break;
        }
        m = Math.min(k, i);
        m--;
      } while (m < 0);
    } while (Math.abs(paramDoubleMatrix2D.getQuick(m, k)) <= d);
    return false;
    return true;
  }
  
  public boolean isNonNegative(DoubleMatrix2D paramDoubleMatrix2D)
  {
    int i = paramDoubleMatrix2D.rows();
    int j = paramDoubleMatrix2D.columns();
    int k = i;
    int m;
    do
    {
      k--;
      do
      {
        if (k < 0) {
          break;
        }
        m = j;
        m--;
      } while (m < 0);
    } while (paramDoubleMatrix2D.getQuick(k, m) >= 0.0D);
    return false;
    return true;
  }
  
  public boolean isOrthogonal(DoubleMatrix2D paramDoubleMatrix2D)
  {
    checkSquare(paramDoubleMatrix2D);
    return equals(paramDoubleMatrix2D.zMult(paramDoubleMatrix2D, null, 1.0D, 0.0D, false, true), DoubleFactory2D.dense.identity(paramDoubleMatrix2D.rows()));
  }
  
  public boolean isPositive(DoubleMatrix2D paramDoubleMatrix2D)
  {
    int i = paramDoubleMatrix2D.rows();
    int j = paramDoubleMatrix2D.columns();
    int k = i;
    int m;
    do
    {
      k--;
      do
      {
        if (k < 0) {
          break;
        }
        m = j;
        m--;
      } while (m < 0);
    } while (paramDoubleMatrix2D.getQuick(k, m) > 0.0D);
    return false;
    return true;
  }
  
  public boolean isSingular(DoubleMatrix2D paramDoubleMatrix2D)
  {
    return Math.abs(Algebra.DEFAULT.det(paramDoubleMatrix2D)) < tolerance();
  }
  
  public boolean isSkewSymmetric(DoubleMatrix2D paramDoubleMatrix2D)
  {
    checkSquare(paramDoubleMatrix2D);
    double d = tolerance();
    int i = paramDoubleMatrix2D.rows();
    int j = paramDoubleMatrix2D.columns();
    int k = i;
    int m;
    do
    {
      k--;
      do
      {
        if (k < 0) {
          break;
        }
        m = i;
        m--;
      } while (m < 0);
    } while (Math.abs(paramDoubleMatrix2D.getQuick(k, m) + paramDoubleMatrix2D.getQuick(m, k)) <= d);
    return false;
    return true;
  }
  
  public boolean isSquare(DoubleMatrix2D paramDoubleMatrix2D)
  {
    return paramDoubleMatrix2D.rows() == paramDoubleMatrix2D.columns();
  }
  
  public boolean isStrictlyLowerTriangular(DoubleMatrix2D paramDoubleMatrix2D)
  {
    double d = tolerance();
    int i = paramDoubleMatrix2D.rows();
    int j = paramDoubleMatrix2D.columns();
    int k = j;
    int m;
    do
    {
      k--;
      do
      {
        if (k < 0) {
          break;
        }
        m = Math.min(i, k + 1);
        m--;
      } while (m < 0);
    } while (Math.abs(paramDoubleMatrix2D.getQuick(m, k)) <= d);
    return false;
    return true;
  }
  
  public boolean isStrictlyTriangular(DoubleMatrix2D paramDoubleMatrix2D)
  {
    if (!isTriangular(paramDoubleMatrix2D)) {
      return false;
    }
    double d = tolerance();
    int i = Math.min(paramDoubleMatrix2D.rows(), paramDoubleMatrix2D.columns());
    do
    {
      i--;
      if (i < 0) {
        break;
      }
    } while (Math.abs(paramDoubleMatrix2D.getQuick(i, i)) <= d);
    return false;
    return true;
  }
  
  public boolean isStrictlyUpperTriangular(DoubleMatrix2D paramDoubleMatrix2D)
  {
    double d = tolerance();
    int i = paramDoubleMatrix2D.rows();
    int j = paramDoubleMatrix2D.columns();
    int k = j;
    int m;
    do
    {
      k--;
      do
      {
        if (k < 0) {
          break;
        }
        m = i;
        m--;
      } while (m < k);
    } while (Math.abs(paramDoubleMatrix2D.getQuick(m, k)) <= d);
    return false;
    return true;
  }
  
  public boolean isSymmetric(DoubleMatrix2D paramDoubleMatrix2D)
  {
    checkSquare(paramDoubleMatrix2D);
    return equals(paramDoubleMatrix2D, paramDoubleMatrix2D.viewDice());
  }
  
  public boolean isTriangular(DoubleMatrix2D paramDoubleMatrix2D)
  {
    return (isLowerTriangular(paramDoubleMatrix2D)) || (isUpperTriangular(paramDoubleMatrix2D));
  }
  
  public boolean isTridiagonal(DoubleMatrix2D paramDoubleMatrix2D)
  {
    double d = tolerance();
    int i = paramDoubleMatrix2D.rows();
    int j = paramDoubleMatrix2D.columns();
    int k = i;
    int m;
    do
    {
      k--;
      do
      {
        if (k < 0) {
          break;
        }
        m = j;
        m--;
      } while (m < 0);
    } while ((Math.abs(k - m) <= 1) || (Math.abs(paramDoubleMatrix2D.getQuick(k, m)) <= d));
    return false;
    return true;
  }
  
  public boolean isUnitTriangular(DoubleMatrix2D paramDoubleMatrix2D)
  {
    if (!isTriangular(paramDoubleMatrix2D)) {
      return false;
    }
    double d = tolerance();
    int i = Math.min(paramDoubleMatrix2D.rows(), paramDoubleMatrix2D.columns());
    do
    {
      i--;
      if (i < 0) {
        break;
      }
    } while (Math.abs(1.0D - paramDoubleMatrix2D.getQuick(i, i)) <= d);
    return false;
    return true;
  }
  
  public boolean isUpperBidiagonal(DoubleMatrix2D paramDoubleMatrix2D)
  {
    double d = tolerance();
    int i = paramDoubleMatrix2D.rows();
    int j = paramDoubleMatrix2D.columns();
    int k = i;
    int m;
    do
    {
      k--;
      do
      {
        if (k < 0) {
          break;
        }
        m = j;
        m--;
      } while (m < 0);
    } while ((k == m) || (k == m - 1) || (Math.abs(paramDoubleMatrix2D.getQuick(k, m)) <= d));
    return false;
    return true;
  }
  
  public boolean isUpperTriangular(DoubleMatrix2D paramDoubleMatrix2D)
  {
    double d = tolerance();
    int i = paramDoubleMatrix2D.rows();
    int j = paramDoubleMatrix2D.columns();
    int k = j;
    int m;
    do
    {
      k--;
      do
      {
        if (k < 0) {
          break;
        }
        m = i;
        m--;
      } while (m <= k);
    } while (Math.abs(paramDoubleMatrix2D.getQuick(m, k)) <= d);
    return false;
    return true;
  }
  
  public boolean isZero(DoubleMatrix2D paramDoubleMatrix2D)
  {
    return equals(paramDoubleMatrix2D, 0.0D);
  }
  
  public int lowerBandwidth(DoubleMatrix2D paramDoubleMatrix2D)
  {
    checkSquare(paramDoubleMatrix2D);
    double d = tolerance();
    int i = paramDoubleMatrix2D.rows();
    int j = i;
    int k;
    int m;
    do
    {
      j--;
      do
      {
        if (j < 0) {
          break;
        }
        k = i - j;
        k--;
      } while (k < 0);
      m = k + j;
    } while (Math.abs(paramDoubleMatrix2D.getQuick(m, k)) <= d);
    return j;
    return 0;
  }
  
  public int semiBandwidth(DoubleMatrix2D paramDoubleMatrix2D)
  {
    checkSquare(paramDoubleMatrix2D);
    double d = tolerance();
    int i = paramDoubleMatrix2D.rows();
    int j = i;
    int k;
    int m;
    do
    {
      j--;
      do
      {
        if (j < 0) {
          break;
        }
        k = i - j;
        k--;
      } while (k < 0);
      m = k + j;
      if (Math.abs(paramDoubleMatrix2D.getQuick(m, k)) > d) {
        return j + 1;
      }
    } while (Math.abs(paramDoubleMatrix2D.getQuick(k, m)) <= d);
    return j + 1;
    return 1;
  }
  
  public void setTolerance(double paramDouble)
  {
    if ((this == DEFAULT) || (this == ZERO) || (this == TWELVE)) {
      throw new IllegalArgumentException("Attempted to modify immutable object.");
    }
    this.tolerance = Math.abs(paramDouble);
  }
  
  public double tolerance()
  {
    return this.tolerance;
  }
  
  public String toString(DoubleMatrix2D paramDoubleMatrix2D)
  {
    ObjectArrayList localObjectArrayList1 = new ObjectArrayList();
    ObjectArrayList localObjectArrayList2 = new ObjectArrayList();
    String str1 = "Illegal operation or error: ";
    localObjectArrayList1.add("density");
    try
    {
      localObjectArrayList2.add(String.valueOf(density(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException1)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException1.getMessage());
    }
    localObjectArrayList1.add("isDiagonal");
    try
    {
      localObjectArrayList2.add(String.valueOf(isDiagonal(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException2)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException2.getMessage());
    }
    localObjectArrayList1.add("isDiagonallyDominantByRow");
    try
    {
      localObjectArrayList2.add(String.valueOf(isDiagonallyDominantByRow(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException3)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException3.getMessage());
    }
    localObjectArrayList1.add("isDiagonallyDominantByColumn");
    try
    {
      localObjectArrayList2.add(String.valueOf(isDiagonallyDominantByColumn(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException4)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException4.getMessage());
    }
    localObjectArrayList1.add("isIdentity");
    try
    {
      localObjectArrayList2.add(String.valueOf(isIdentity(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException5)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException5.getMessage());
    }
    localObjectArrayList1.add("isLowerBidiagonal");
    try
    {
      localObjectArrayList2.add(String.valueOf(isLowerBidiagonal(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException6)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException6.getMessage());
    }
    localObjectArrayList1.add("isLowerTriangular");
    try
    {
      localObjectArrayList2.add(String.valueOf(isLowerTriangular(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException7)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException7.getMessage());
    }
    localObjectArrayList1.add("isNonNegative");
    try
    {
      localObjectArrayList2.add(String.valueOf(isNonNegative(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException8)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException8.getMessage());
    }
    localObjectArrayList1.add("isOrthogonal");
    try
    {
      localObjectArrayList2.add(String.valueOf(isOrthogonal(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException9)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException9.getMessage());
    }
    localObjectArrayList1.add("isPositive");
    try
    {
      localObjectArrayList2.add(String.valueOf(isPositive(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException10)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException10.getMessage());
    }
    localObjectArrayList1.add("isSingular");
    try
    {
      localObjectArrayList2.add(String.valueOf(isSingular(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException11)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException11.getMessage());
    }
    localObjectArrayList1.add("isSkewSymmetric");
    try
    {
      localObjectArrayList2.add(String.valueOf(isSkewSymmetric(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException12)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException12.getMessage());
    }
    localObjectArrayList1.add("isSquare");
    try
    {
      localObjectArrayList2.add(String.valueOf(isSquare(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException13)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException13.getMessage());
    }
    localObjectArrayList1.add("isStrictlyLowerTriangular");
    try
    {
      localObjectArrayList2.add(String.valueOf(isStrictlyLowerTriangular(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException14)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException14.getMessage());
    }
    localObjectArrayList1.add("isStrictlyTriangular");
    try
    {
      localObjectArrayList2.add(String.valueOf(isStrictlyTriangular(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException15)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException15.getMessage());
    }
    localObjectArrayList1.add("isStrictlyUpperTriangular");
    try
    {
      localObjectArrayList2.add(String.valueOf(isStrictlyUpperTriangular(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException16)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException16.getMessage());
    }
    localObjectArrayList1.add("isSymmetric");
    try
    {
      localObjectArrayList2.add(String.valueOf(isSymmetric(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException17)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException17.getMessage());
    }
    localObjectArrayList1.add("isTriangular");
    try
    {
      localObjectArrayList2.add(String.valueOf(isTriangular(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException18)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException18.getMessage());
    }
    localObjectArrayList1.add("isTridiagonal");
    try
    {
      localObjectArrayList2.add(String.valueOf(isTridiagonal(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException19)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException19.getMessage());
    }
    localObjectArrayList1.add("isUnitTriangular");
    try
    {
      localObjectArrayList2.add(String.valueOf(isUnitTriangular(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException20)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException20.getMessage());
    }
    localObjectArrayList1.add("isUpperBidiagonal");
    try
    {
      localObjectArrayList2.add(String.valueOf(isUpperBidiagonal(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException21)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException21.getMessage());
    }
    localObjectArrayList1.add("isUpperTriangular");
    try
    {
      localObjectArrayList2.add(String.valueOf(isUpperTriangular(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException22)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException22.getMessage());
    }
    localObjectArrayList1.add("isZero");
    try
    {
      localObjectArrayList2.add(String.valueOf(isZero(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException23)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException23.getMessage());
    }
    localObjectArrayList1.add("lowerBandwidth");
    try
    {
      localObjectArrayList2.add(String.valueOf(lowerBandwidth(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException24)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException24.getMessage());
    }
    localObjectArrayList1.add("semiBandwidth");
    try
    {
      localObjectArrayList2.add(String.valueOf(semiBandwidth(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException25)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException25.getMessage());
    }
    localObjectArrayList1.add("upperBandwidth");
    try
    {
      localObjectArrayList2.add(String.valueOf(upperBandwidth(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException26)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException26.getMessage());
    }
    IntComparator local1 = new IntComparator()
    {
      private final ObjectArrayList val$names;
      
      public int compare(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        return Property.get(this.val$names, paramAnonymousInt1).compareTo(Property.get(this.val$names, paramAnonymousInt2));
      }
    };
    Swapper local2 = new Swapper()
    {
      private final ObjectArrayList val$names;
      private final ObjectArrayList val$values;
      
      public void swap(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        Object localObject = this.val$names.get(paramAnonymousInt1);
        this.val$names.set(paramAnonymousInt1, this.val$names.get(paramAnonymousInt2));
        this.val$names.set(paramAnonymousInt2, localObject);
        localObject = this.val$values.get(paramAnonymousInt1);
        this.val$values.set(paramAnonymousInt1, this.val$values.get(paramAnonymousInt2));
        this.val$values.set(paramAnonymousInt2, localObject);
      }
    };
    GenericSorting.quickSort(0, localObjectArrayList1.size(), local1, local2);
    int i = 0;
    for (int j = 0; j < localObjectArrayList1.size(); j++)
    {
      k = ((String)localObjectArrayList1.get(j)).length();
      i = Math.max(k, i);
    }
    StringBuffer localStringBuffer = new StringBuffer();
    for (int k = 0; k < localObjectArrayList1.size(); k++)
    {
      String str2 = (String)localObjectArrayList1.get(k);
      localStringBuffer.append(str2);
      localStringBuffer.append(blanks(i - str2.length()));
      localStringBuffer.append(" : ");
      localStringBuffer.append(localObjectArrayList2.get(k));
      if (k < localObjectArrayList1.size() - 1) {
        localStringBuffer.append('\n');
      }
    }
    return localStringBuffer.toString();
  }
  
  public int upperBandwidth(DoubleMatrix2D paramDoubleMatrix2D)
  {
    checkSquare(paramDoubleMatrix2D);
    double d = tolerance();
    int i = paramDoubleMatrix2D.rows();
    int j = i;
    int k;
    int m;
    do
    {
      j--;
      do
      {
        if (j < 0) {
          break;
        }
        k = i - j;
        k--;
      } while (k < 0);
      m = k + j;
    } while (Math.abs(paramDoubleMatrix2D.getQuick(k, m)) <= d);
    return j;
    return 0;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.linalg.Property
 * JD-Core Version:    0.7.0.1
 */