package cern.colt.matrix.linalg;

import cern.colt.GenericPermuting;
import cern.colt.GenericSorting;
import cern.colt.PersistentObject;
import cern.colt.Swapper;
import cern.colt.bitvector.QuickBitVector;
import cern.colt.function.DoubleDoubleFunction;
import cern.colt.function.IntComparator;
import cern.colt.list.ObjectArrayList;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.jet.math.Functions;

public class Algebra
  extends PersistentObject
{
  public static final Algebra DEFAULT = new Algebra();
  public static final Algebra ZERO;
  protected Property property;
  
  public Algebra()
  {
    this(Property.DEFAULT.tolerance());
  }
  
  public Algebra(double paramDouble)
  {
    setProperty(new Property(paramDouble));
  }
  
  private CholeskyDecomposition chol(DoubleMatrix2D paramDoubleMatrix2D)
  {
    return new CholeskyDecomposition(paramDoubleMatrix2D);
  }
  
  public Object clone()
  {
    return new Algebra(this.property.tolerance());
  }
  
  public double cond(DoubleMatrix2D paramDoubleMatrix2D)
  {
    return svd(paramDoubleMatrix2D).cond();
  }
  
  public double det(DoubleMatrix2D paramDoubleMatrix2D)
  {
    return lu(paramDoubleMatrix2D).det();
  }
  
  private EigenvalueDecomposition eig(DoubleMatrix2D paramDoubleMatrix2D)
  {
    return new EigenvalueDecomposition(paramDoubleMatrix2D);
  }
  
  protected static double hypot(double paramDouble1, double paramDouble2)
  {
    double d;
    if (Math.abs(paramDouble1) > Math.abs(paramDouble2))
    {
      d = paramDouble2 / paramDouble1;
      d = Math.abs(paramDouble1) * Math.sqrt(1.0D + d * d);
    }
    else if (paramDouble2 != 0.0D)
    {
      d = paramDouble1 / paramDouble2;
      d = Math.abs(paramDouble2) * Math.sqrt(1.0D + d * d);
    }
    else
    {
      d = 0.0D;
    }
    return d;
  }
  
  protected static DoubleDoubleFunction hypotFunction()
  {
    new DoubleDoubleFunction()
    {
      public final double apply(double paramAnonymousDouble1, double paramAnonymousDouble2)
      {
        return Algebra.hypot(paramAnonymousDouble1, paramAnonymousDouble2);
      }
    };
  }
  
  public DoubleMatrix2D inverse(DoubleMatrix2D paramDoubleMatrix2D)
  {
    if ((this.property.isSquare(paramDoubleMatrix2D)) && (this.property.isDiagonal(paramDoubleMatrix2D)))
    {
      DoubleMatrix2D localDoubleMatrix2D = paramDoubleMatrix2D.copy();
      boolean bool = Diagonal.inverse(localDoubleMatrix2D);
      if (!bool) {
        throw new IllegalArgumentException("A is singular.");
      }
      return localDoubleMatrix2D;
    }
    return solve(paramDoubleMatrix2D, DoubleFactory2D.dense.identity(paramDoubleMatrix2D.rows()));
  }
  
  private LUDecomposition lu(DoubleMatrix2D paramDoubleMatrix2D)
  {
    return new LUDecomposition(paramDoubleMatrix2D);
  }
  
  public double mult(DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2)
  {
    return paramDoubleMatrix1D1.zDotProduct(paramDoubleMatrix1D2);
  }
  
  public DoubleMatrix1D mult(DoubleMatrix2D paramDoubleMatrix2D, DoubleMatrix1D paramDoubleMatrix1D)
  {
    return paramDoubleMatrix2D.zMult(paramDoubleMatrix1D, null);
  }
  
  public DoubleMatrix2D mult(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2)
  {
    return paramDoubleMatrix2D1.zMult(paramDoubleMatrix2D2, null);
  }
  
  public DoubleMatrix2D multOuter(DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2, DoubleMatrix2D paramDoubleMatrix2D)
  {
    int i = paramDoubleMatrix1D1.size();
    int j = paramDoubleMatrix1D2.size();
    if (paramDoubleMatrix2D == null) {
      paramDoubleMatrix2D = paramDoubleMatrix1D1.like2D(i, j);
    }
    if ((paramDoubleMatrix2D.rows() != i) || (paramDoubleMatrix2D.columns() != j)) {
      throw new IllegalArgumentException();
    }
    int k = i;
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      paramDoubleMatrix2D.viewRow(k).assign(paramDoubleMatrix1D2);
    }
    k = j;
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      paramDoubleMatrix2D.viewColumn(k).assign(paramDoubleMatrix1D1, Functions.mult);
    }
    return paramDoubleMatrix2D;
  }
  
  public double norm1(DoubleMatrix1D paramDoubleMatrix1D)
  {
    if (paramDoubleMatrix1D.size() == 0) {
      return 0.0D;
    }
    return paramDoubleMatrix1D.aggregate(Functions.plus, Functions.abs);
  }
  
  public double norm1(DoubleMatrix2D paramDoubleMatrix2D)
  {
    double d = 0.0D;
    int i = paramDoubleMatrix2D.columns();
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      d = Math.max(d, norm1(paramDoubleMatrix2D.viewColumn(i)));
    }
    return d;
  }
  
  public double norm2(DoubleMatrix1D paramDoubleMatrix1D)
  {
    return mult(paramDoubleMatrix1D, paramDoubleMatrix1D);
  }
  
  public double norm2(DoubleMatrix2D paramDoubleMatrix2D)
  {
    return svd(paramDoubleMatrix2D).norm2();
  }
  
  public double normF(DoubleMatrix2D paramDoubleMatrix2D)
  {
    if (paramDoubleMatrix2D.size() == 0) {
      return 0.0D;
    }
    return paramDoubleMatrix2D.aggregate(hypotFunction(), Functions.identity);
  }
  
  public double normInfinity(DoubleMatrix1D paramDoubleMatrix1D)
  {
    if (paramDoubleMatrix1D.size() == 0) {
      return 0.0D;
    }
    return paramDoubleMatrix1D.aggregate(Functions.max, Functions.abs);
  }
  
  public double normInfinity(DoubleMatrix2D paramDoubleMatrix2D)
  {
    double d = 0.0D;
    int i = paramDoubleMatrix2D.rows();
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      d = Math.max(d, norm1(paramDoubleMatrix2D.viewRow(i)));
    }
    return d;
  }
  
  public DoubleMatrix1D permute(DoubleMatrix1D paramDoubleMatrix1D, int[] paramArrayOfInt, double[] paramArrayOfDouble)
  {
    int i = paramDoubleMatrix1D.size();
    if (paramArrayOfInt.length != i) {
      throw new IndexOutOfBoundsException("invalid permutation");
    }
    if ((paramArrayOfDouble == null) || (i > paramArrayOfDouble.length)) {
      paramArrayOfDouble = paramDoubleMatrix1D.toArray();
    } else {
      paramDoubleMatrix1D.toArray(paramArrayOfDouble);
    }
    int j = i;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      paramDoubleMatrix1D.setQuick(j, paramArrayOfDouble[paramArrayOfInt[j]]);
    }
    return paramDoubleMatrix1D;
  }
  
  public DoubleMatrix2D permute(DoubleMatrix2D paramDoubleMatrix2D, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return paramDoubleMatrix2D.viewSelection(paramArrayOfInt1, paramArrayOfInt2);
  }
  
  public DoubleMatrix2D permuteColumns(DoubleMatrix2D paramDoubleMatrix2D, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return permuteRows(paramDoubleMatrix2D.viewDice(), paramArrayOfInt1, paramArrayOfInt2);
  }
  
  public DoubleMatrix2D permuteRows(DoubleMatrix2D paramDoubleMatrix2D, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    int i = paramDoubleMatrix2D.rows();
    if (paramArrayOfInt1.length != i) {
      throw new IndexOutOfBoundsException("invalid permutation");
    }
    int j = paramDoubleMatrix2D.columns();
    if (j < i / 10)
    {
      localObject = new double[i];
      int k = paramDoubleMatrix2D.columns();
      for (;;)
      {
        k--;
        if (k < 0) {
          break;
        }
        permute(paramDoubleMatrix2D.viewColumn(k), paramArrayOfInt1, (double[])localObject);
      }
      return paramDoubleMatrix2D;
    }
    Object localObject = new Swapper()
    {
      private final DoubleMatrix2D val$A;
      
      public void swap(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        this.val$A.viewRow(paramAnonymousInt1).swap(this.val$A.viewRow(paramAnonymousInt2));
      }
    };
    GenericPermuting.permute(paramArrayOfInt1, (Swapper)localObject, paramArrayOfInt2, null);
    return paramDoubleMatrix2D;
  }
  
  public DoubleMatrix2D pow(DoubleMatrix2D paramDoubleMatrix2D, int paramInt)
  {
    Blas localBlas = SmpBlas.smpBlas;
    Property.DEFAULT.checkSquare(paramDoubleMatrix2D);
    if (paramInt < 0)
    {
      paramDoubleMatrix2D = inverse(paramDoubleMatrix2D);
      paramInt = -paramInt;
    }
    if (paramInt == 0) {
      return DoubleFactory2D.dense.identity(paramDoubleMatrix2D.rows());
    }
    Object localObject1 = paramDoubleMatrix2D.like();
    if (paramInt == 1) {
      return ((DoubleMatrix2D)localObject1).assign(paramDoubleMatrix2D);
    }
    if (paramInt == 2)
    {
      localBlas.dgemm(false, false, 1.0D, paramDoubleMatrix2D, paramDoubleMatrix2D, 0.0D, (DoubleMatrix2D)localObject1);
      return localObject1;
    }
    int i = QuickBitVector.mostSignificantBit(paramInt);
    for (int j = 0; (j <= i) && ((paramInt & 1 << j) == 0); j++)
    {
      localBlas.dgemm(false, false, 1.0D, paramDoubleMatrix2D, paramDoubleMatrix2D, 0.0D, (DoubleMatrix2D)localObject1);
      localObject2 = paramDoubleMatrix2D;
      paramDoubleMatrix2D = (DoubleMatrix2D)localObject1;
      localObject1 = localObject2;
    }
    Object localObject2 = paramDoubleMatrix2D.copy();
    j++;
    while (j <= i)
    {
      localBlas.dgemm(false, false, 1.0D, paramDoubleMatrix2D, paramDoubleMatrix2D, 0.0D, (DoubleMatrix2D)localObject1);
      Object localObject3 = paramDoubleMatrix2D;
      paramDoubleMatrix2D = (DoubleMatrix2D)localObject1;
      localObject1 = localObject3;
      if ((paramInt & 1 << j) != 0)
      {
        localBlas.dgemm(false, false, 1.0D, (DoubleMatrix2D)localObject2, paramDoubleMatrix2D, 0.0D, (DoubleMatrix2D)localObject1);
        localObject3 = localObject2;
        localObject2 = localObject1;
        localObject1 = localObject3;
      }
      j++;
    }
    return localObject2;
  }
  
  public Property property()
  {
    return this.property;
  }
  
  private QRDecomposition qr(DoubleMatrix2D paramDoubleMatrix2D)
  {
    return new QRDecomposition(paramDoubleMatrix2D);
  }
  
  public int rank(DoubleMatrix2D paramDoubleMatrix2D)
  {
    return svd(paramDoubleMatrix2D).rank();
  }
  
  public void setProperty(Property paramProperty)
  {
    if ((this == DEFAULT) && (paramProperty != this.property)) {
      throw new IllegalArgumentException("Attempted to modify immutable object.");
    }
    if ((this == ZERO) && (paramProperty != this.property)) {
      throw new IllegalArgumentException("Attempted to modify immutable object.");
    }
    this.property = paramProperty;
  }
  
  public DoubleMatrix2D solve(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2)
  {
    return paramDoubleMatrix2D1.rows() == paramDoubleMatrix2D1.columns() ? lu(paramDoubleMatrix2D1).solve(paramDoubleMatrix2D2) : qr(paramDoubleMatrix2D1).solve(paramDoubleMatrix2D2);
  }
  
  public DoubleMatrix2D solveTranspose(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2)
  {
    return solve(transpose(paramDoubleMatrix2D1), transpose(paramDoubleMatrix2D2));
  }
  
  private DoubleMatrix2D subMatrix(DoubleMatrix2D paramDoubleMatrix2D, int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    int i = paramInt2 - paramInt1 + 1;
    int j = paramDoubleMatrix2D.rows();
    paramDoubleMatrix2D = paramDoubleMatrix2D.viewPart(0, paramInt1, j, i);
    DoubleMatrix2D localDoubleMatrix2D = paramDoubleMatrix2D.like(paramArrayOfInt.length, i);
    int k = paramArrayOfInt.length;
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      int m = paramArrayOfInt[k];
      if ((m < 0) || (m >= j)) {
        throw new IndexOutOfBoundsException("Illegal Index");
      }
      localDoubleMatrix2D.viewRow(k).assign(paramDoubleMatrix2D.viewRow(m));
    }
    return localDoubleMatrix2D;
  }
  
  private DoubleMatrix2D subMatrix(DoubleMatrix2D paramDoubleMatrix2D, int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    if (paramInt2 - paramInt1 >= paramDoubleMatrix2D.rows()) {
      throw new IndexOutOfBoundsException("Too many rows");
    }
    int i = paramInt2 - paramInt1 + 1;
    int j = paramDoubleMatrix2D.columns();
    paramDoubleMatrix2D = paramDoubleMatrix2D.viewPart(paramInt1, 0, i, j);
    DoubleMatrix2D localDoubleMatrix2D = paramDoubleMatrix2D.like(i, paramArrayOfInt.length);
    int k = paramArrayOfInt.length;
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      int m = paramArrayOfInt[k];
      if ((m < 0) || (m >= j)) {
        throw new IndexOutOfBoundsException("Illegal Index");
      }
      localDoubleMatrix2D.viewColumn(k).assign(paramDoubleMatrix2D.viewColumn(m));
    }
    return localDoubleMatrix2D;
  }
  
  public DoubleMatrix2D subMatrix(DoubleMatrix2D paramDoubleMatrix2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return paramDoubleMatrix2D.viewPart(paramInt1, paramInt3, paramInt2 - paramInt1 + 1, paramInt4 - paramInt3 + 1);
  }
  
  private SingularValueDecomposition svd(DoubleMatrix2D paramDoubleMatrix2D)
  {
    return new SingularValueDecomposition(paramDoubleMatrix2D);
  }
  
  public String toString(DoubleMatrix2D paramDoubleMatrix2D)
  {
    ObjectArrayList localObjectArrayList1 = new ObjectArrayList();
    ObjectArrayList localObjectArrayList2 = new ObjectArrayList();
    String str1 = "Illegal operation or error: ";
    localObjectArrayList1.add("cond");
    try
    {
      localObjectArrayList2.add(String.valueOf(cond(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException1)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException1.getMessage());
    }
    localObjectArrayList1.add("det");
    try
    {
      localObjectArrayList2.add(String.valueOf(det(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException2)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException2.getMessage());
    }
    localObjectArrayList1.add("norm1");
    try
    {
      localObjectArrayList2.add(String.valueOf(norm1(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException3)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException3.getMessage());
    }
    localObjectArrayList1.add("norm2");
    try
    {
      localObjectArrayList2.add(String.valueOf(norm2(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException4)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException4.getMessage());
    }
    localObjectArrayList1.add("normF");
    try
    {
      localObjectArrayList2.add(String.valueOf(normF(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException5)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException5.getMessage());
    }
    localObjectArrayList1.add("normInfinity");
    try
    {
      localObjectArrayList2.add(String.valueOf(normInfinity(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException6)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException6.getMessage());
    }
    localObjectArrayList1.add("rank");
    try
    {
      localObjectArrayList2.add(String.valueOf(rank(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException7)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException7.getMessage());
    }
    localObjectArrayList1.add("trace");
    try
    {
      localObjectArrayList2.add(String.valueOf(trace(paramDoubleMatrix2D)));
    }
    catch (IllegalArgumentException localIllegalArgumentException8)
    {
      localObjectArrayList2.add(str1 + localIllegalArgumentException8.getMessage());
    }
    IntComparator local3 = new IntComparator()
    {
      private final ObjectArrayList val$names;
      
      public int compare(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        return Property.get(this.val$names, paramAnonymousInt1).compareTo(Property.get(this.val$names, paramAnonymousInt2));
      }
    };
    Swapper local4 = new Swapper()
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
    GenericSorting.quickSort(0, localObjectArrayList1.size(), local3, local4);
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
      localStringBuffer.append(Property.blanks(i - str2.length()));
      localStringBuffer.append(" : ");
      localStringBuffer.append(localObjectArrayList2.get(k));
      if (k < localObjectArrayList1.size() - 1) {
        localStringBuffer.append('\n');
      }
    }
    return localStringBuffer.toString();
  }
  
  public String toVerboseString(DoubleMatrix2D paramDoubleMatrix2D)
  {
    String str = "Illegal operation or error upon construction of ";
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("A = ");
    localStringBuffer.append(paramDoubleMatrix2D);
    localStringBuffer.append("\n\n" + toString(paramDoubleMatrix2D));
    localStringBuffer.append("\n\n" + Property.DEFAULT.toString(paramDoubleMatrix2D));
    LUDecomposition localLUDecomposition = null;
    try
    {
      localLUDecomposition = new LUDecomposition(paramDoubleMatrix2D);
    }
    catch (IllegalArgumentException localIllegalArgumentException1)
    {
      localStringBuffer.append("\n\n" + str + " LUDecomposition: " + localIllegalArgumentException1.getMessage());
    }
    if (localLUDecomposition != null) {
      localStringBuffer.append("\n\n" + localLUDecomposition.toString());
    }
    QRDecomposition localQRDecomposition = null;
    try
    {
      localQRDecomposition = new QRDecomposition(paramDoubleMatrix2D);
    }
    catch (IllegalArgumentException localIllegalArgumentException2)
    {
      localStringBuffer.append("\n\n" + str + " QRDecomposition: " + localIllegalArgumentException2.getMessage());
    }
    if (localQRDecomposition != null) {
      localStringBuffer.append("\n\n" + localQRDecomposition.toString());
    }
    CholeskyDecomposition localCholeskyDecomposition = null;
    try
    {
      localCholeskyDecomposition = new CholeskyDecomposition(paramDoubleMatrix2D);
    }
    catch (IllegalArgumentException localIllegalArgumentException3)
    {
      localStringBuffer.append("\n\n" + str + " CholeskyDecomposition: " + localIllegalArgumentException3.getMessage());
    }
    if (localCholeskyDecomposition != null) {
      localStringBuffer.append("\n\n" + localCholeskyDecomposition.toString());
    }
    EigenvalueDecomposition localEigenvalueDecomposition = null;
    try
    {
      localEigenvalueDecomposition = new EigenvalueDecomposition(paramDoubleMatrix2D);
    }
    catch (IllegalArgumentException localIllegalArgumentException4)
    {
      localStringBuffer.append("\n\n" + str + " EigenvalueDecomposition: " + localIllegalArgumentException4.getMessage());
    }
    if (localEigenvalueDecomposition != null) {
      localStringBuffer.append("\n\n" + localEigenvalueDecomposition.toString());
    }
    SingularValueDecomposition localSingularValueDecomposition = null;
    try
    {
      localSingularValueDecomposition = new SingularValueDecomposition(paramDoubleMatrix2D);
    }
    catch (IllegalArgumentException localIllegalArgumentException5)
    {
      localStringBuffer.append("\n\n" + str + " SingularValueDecomposition: " + localIllegalArgumentException5.getMessage());
    }
    if (localSingularValueDecomposition != null) {
      localStringBuffer.append("\n\n" + localSingularValueDecomposition.toString());
    }
    return localStringBuffer.toString();
  }
  
  public double trace(DoubleMatrix2D paramDoubleMatrix2D)
  {
    double d = 0.0D;
    int i = Math.min(paramDoubleMatrix2D.rows(), paramDoubleMatrix2D.columns());
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      d += paramDoubleMatrix2D.getQuick(i, i);
    }
    return d;
  }
  
  public DoubleMatrix2D transpose(DoubleMatrix2D paramDoubleMatrix2D)
  {
    return paramDoubleMatrix2D.viewDice();
  }
  
  protected DoubleMatrix2D trapezoidalLower(DoubleMatrix2D paramDoubleMatrix2D)
  {
    int i = paramDoubleMatrix2D.rows();
    int j = paramDoubleMatrix2D.columns();
    int k = i;
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      int m = j;
      for (;;)
      {
        m--;
        if (m < 0) {
          break;
        }
        if (k < m) {
          paramDoubleMatrix2D.setQuick(k, m, 0.0D);
        }
      }
    }
    return paramDoubleMatrix2D;
  }
  
  private DoubleMatrix2D xmultOuter(DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2)
  {
    DoubleMatrix2D localDoubleMatrix2D = paramDoubleMatrix1D1.like2D(paramDoubleMatrix1D1.size(), paramDoubleMatrix1D2.size());
    multOuter(paramDoubleMatrix1D1, paramDoubleMatrix1D2, localDoubleMatrix2D);
    return localDoubleMatrix2D;
  }
  
  private DoubleMatrix2D xpowSlow(DoubleMatrix2D paramDoubleMatrix2D, int paramInt)
  {
    DoubleMatrix2D localDoubleMatrix2D = paramDoubleMatrix2D.copy();
    for (int i = 0; i < paramInt - 1; i++) {
      localDoubleMatrix2D = mult(localDoubleMatrix2D, paramDoubleMatrix2D);
    }
    return localDoubleMatrix2D;
  }
  
  static
  {
    DEFAULT.property = Property.DEFAULT;
    ZERO = new Algebra();
    ZERO.property = Property.ZERO;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.linalg.Algebra
 * JD-Core Version:    0.7.0.1
 */