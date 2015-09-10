package cern.colt.matrix.linalg;

import cern.colt.function.DoubleDoubleFunction;
import cern.colt.function.DoubleFunction;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.jet.math.Functions;
import cern.jet.math.PlusMult;

public class SeqBlas
  implements Blas
{
  public static final Blas seqBlas = new SeqBlas();
  private static final Functions F = Functions.functions;
  
  public void assign(DoubleMatrix2D paramDoubleMatrix2D, DoubleFunction paramDoubleFunction)
  {
    paramDoubleMatrix2D.assign(paramDoubleFunction);
  }
  
  public void assign(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2, DoubleDoubleFunction paramDoubleDoubleFunction)
  {
    paramDoubleMatrix2D1.assign(paramDoubleMatrix2D2, paramDoubleDoubleFunction);
  }
  
  public double dasum(DoubleMatrix1D paramDoubleMatrix1D)
  {
    return paramDoubleMatrix1D.aggregate(Functions.plus, Functions.abs);
  }
  
  public void daxpy(double paramDouble, DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2)
  {
    paramDoubleMatrix1D2.assign(paramDoubleMatrix1D1, Functions.plusMult(paramDouble));
  }
  
  public void daxpy(double paramDouble, DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2)
  {
    paramDoubleMatrix2D2.assign(paramDoubleMatrix2D1, Functions.plusMult(paramDouble));
  }
  
  public void dcopy(DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2)
  {
    paramDoubleMatrix1D2.assign(paramDoubleMatrix1D1);
  }
  
  public void dcopy(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2)
  {
    paramDoubleMatrix2D2.assign(paramDoubleMatrix2D1);
  }
  
  public double ddot(DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2)
  {
    return paramDoubleMatrix1D1.zDotProduct(paramDoubleMatrix1D2);
  }
  
  public void dgemm(boolean paramBoolean1, boolean paramBoolean2, double paramDouble1, DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2, double paramDouble2, DoubleMatrix2D paramDoubleMatrix2D3)
  {
    paramDoubleMatrix2D1.zMult(paramDoubleMatrix2D2, paramDoubleMatrix2D3, paramDouble1, paramDouble2, paramBoolean1, paramBoolean2);
  }
  
  public void dgemv(boolean paramBoolean, double paramDouble1, DoubleMatrix2D paramDoubleMatrix2D, DoubleMatrix1D paramDoubleMatrix1D1, double paramDouble2, DoubleMatrix1D paramDoubleMatrix1D2)
  {
    paramDoubleMatrix2D.zMult(paramDoubleMatrix1D1, paramDoubleMatrix1D2, paramDouble1, paramDouble2, paramBoolean);
  }
  
  public void dger(double paramDouble, DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2, DoubleMatrix2D paramDoubleMatrix2D)
  {
    PlusMult localPlusMult = PlusMult.plusMult(0.0D);
    int i = paramDoubleMatrix2D.rows();
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      localPlusMult.multiplicator = (paramDouble * paramDoubleMatrix1D1.getQuick(i));
      paramDoubleMatrix2D.viewRow(i).assign(paramDoubleMatrix1D2, localPlusMult);
    }
  }
  
  public double dnrm2(DoubleMatrix1D paramDoubleMatrix1D)
  {
    return Math.sqrt(Algebra.DEFAULT.norm2(paramDoubleMatrix1D));
  }
  
  public void drot(DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2, double paramDouble1, double paramDouble2)
  {
    paramDoubleMatrix1D1.checkSize(paramDoubleMatrix1D2);
    DoubleMatrix1D localDoubleMatrix1D = paramDoubleMatrix1D1.copy();
    paramDoubleMatrix1D1.assign(Functions.mult(paramDouble1));
    paramDoubleMatrix1D1.assign(paramDoubleMatrix1D2, Functions.plusMult(paramDouble2));
    paramDoubleMatrix1D2.assign(Functions.mult(paramDouble1));
    paramDoubleMatrix1D2.assign(localDoubleMatrix1D, Functions.minusMult(paramDouble2));
  }
  
  public void drotg(double paramDouble1, double paramDouble2, double[] paramArrayOfDouble)
  {
    double d3 = paramDouble2;
    if (Math.abs(paramDouble1) > Math.abs(paramDouble2)) {
      d3 = paramDouble1;
    }
    double d4 = Math.abs(paramDouble1) + Math.abs(paramDouble2);
    double d5;
    double d1;
    double d2;
    double d6;
    if (d4 != 0.0D)
    {
      double d7 = paramDouble1 / d4;
      double d8 = paramDouble2 / d4;
      d5 = d4 * Math.sqrt(d7 * d7 + d8 * d8);
      d5 = sign(1.0D, d3) * d5;
      d1 = paramDouble1 / d5;
      d2 = paramDouble2 / d5;
      d6 = 1.0D;
      if (Math.abs(paramDouble1) > Math.abs(paramDouble2)) {
        d6 = d2;
      }
      if ((Math.abs(paramDouble2) >= Math.abs(paramDouble1)) && (d1 != 0.0D)) {
        d6 = 1.0D / d1;
      }
    }
    else
    {
      d1 = 1.0D;
      d2 = 0.0D;
      d5 = 0.0D;
      d6 = 0.0D;
    }
    paramDouble1 = d5;
    paramDouble2 = d6;
    paramArrayOfDouble[0] = paramDouble1;
    paramArrayOfDouble[1] = paramDouble2;
    paramArrayOfDouble[2] = d1;
    paramArrayOfDouble[3] = d2;
  }
  
  public void dscal(double paramDouble, DoubleMatrix1D paramDoubleMatrix1D)
  {
    paramDoubleMatrix1D.assign(Functions.mult(paramDouble));
  }
  
  public void dscal(double paramDouble, DoubleMatrix2D paramDoubleMatrix2D)
  {
    paramDoubleMatrix2D.assign(Functions.mult(paramDouble));
  }
  
  public void dswap(DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2)
  {
    paramDoubleMatrix1D2.swap(paramDoubleMatrix1D1);
  }
  
  public void dswap(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2)
  {
    paramDoubleMatrix2D1.checkShape(paramDoubleMatrix2D2);
    int i = paramDoubleMatrix2D1.rows();
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      paramDoubleMatrix2D1.viewRow(i).swap(paramDoubleMatrix2D2.viewRow(i));
    }
  }
  
  public void dsymv(boolean paramBoolean, double paramDouble1, DoubleMatrix2D paramDoubleMatrix2D, DoubleMatrix1D paramDoubleMatrix1D1, double paramDouble2, DoubleMatrix1D paramDoubleMatrix1D2)
  {
    if (paramBoolean) {
      paramDoubleMatrix2D = paramDoubleMatrix2D.viewDice();
    }
    Property.DEFAULT.checkSquare(paramDoubleMatrix2D);
    int i = paramDoubleMatrix2D.rows();
    if ((i != paramDoubleMatrix1D1.size()) || (i != paramDoubleMatrix1D2.size())) {
      throw new IllegalArgumentException(paramDoubleMatrix2D.toStringShort() + ", " + paramDoubleMatrix1D1.toStringShort() + ", " + paramDoubleMatrix1D2.toStringShort());
    }
    DoubleMatrix1D localDoubleMatrix1D = paramDoubleMatrix1D1.like();
    for (int j = 0; j < i; j++)
    {
      double d = 0.0D;
      for (int k = 0; k <= j; k++) {
        d += paramDoubleMatrix2D.getQuick(j, k) * paramDoubleMatrix1D1.getQuick(k);
      }
      for (k = j + 1; k < i; k++) {
        d += paramDoubleMatrix2D.getQuick(k, j) * paramDoubleMatrix1D1.getQuick(k);
      }
      localDoubleMatrix1D.setQuick(j, paramDouble1 * d + paramDouble2 * paramDoubleMatrix1D2.getQuick(j));
    }
    paramDoubleMatrix1D2.assign(localDoubleMatrix1D);
  }
  
  public void dtrmv(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, DoubleMatrix2D paramDoubleMatrix2D, DoubleMatrix1D paramDoubleMatrix1D)
  {
    if (paramBoolean2)
    {
      paramDoubleMatrix2D = paramDoubleMatrix2D.viewDice();
      paramBoolean1 = !paramBoolean1;
    }
    Property.DEFAULT.checkSquare(paramDoubleMatrix2D);
    int i = paramDoubleMatrix2D.rows();
    if (i != paramDoubleMatrix1D.size()) {
      throw new IllegalArgumentException(paramDoubleMatrix2D.toStringShort() + ", " + paramDoubleMatrix1D.toStringShort());
    }
    DoubleMatrix1D localDoubleMatrix1D1 = paramDoubleMatrix1D.like();
    DoubleMatrix1D localDoubleMatrix1D2 = paramDoubleMatrix1D.like();
    if (paramBoolean3) {
      localDoubleMatrix1D2.assign(1.0D);
    } else {
      for (j = 0; j < i; j++) {
        localDoubleMatrix1D2.setQuick(j, paramDoubleMatrix2D.getQuick(j, j));
      }
    }
    for (int j = 0; j < i; j++)
    {
      double d = 0.0D;
      int k;
      if (!paramBoolean1)
      {
        for (k = 0; k < j; k++) {
          d += paramDoubleMatrix2D.getQuick(j, k) * paramDoubleMatrix1D.getQuick(k);
        }
        d += localDoubleMatrix1D2.getQuick(j) * paramDoubleMatrix1D.getQuick(j);
      }
      else
      {
        d += localDoubleMatrix1D2.getQuick(j) * paramDoubleMatrix1D.getQuick(j);
        for (k = j + 1; k < i; k++) {
          d += paramDoubleMatrix2D.getQuick(j, k) * paramDoubleMatrix1D.getQuick(k);
        }
      }
      localDoubleMatrix1D1.setQuick(j, d);
    }
    paramDoubleMatrix1D.assign(localDoubleMatrix1D1);
  }
  
  public int idamax(DoubleMatrix1D paramDoubleMatrix1D)
  {
    int i = -1;
    double d1 = 4.9E-324D;
    int j = paramDoubleMatrix1D.size();
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      double d2 = Math.abs(paramDoubleMatrix1D.getQuick(j));
      if (d2 > d1)
      {
        d1 = d2;
        i = j;
      }
    }
    return i;
  }
  
  private double sign(double paramDouble1, double paramDouble2)
  {
    if (paramDouble2 < 0.0D) {
      return -Math.abs(paramDouble1);
    }
    return Math.abs(paramDouble1);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.linalg.SeqBlas
 * JD-Core Version:    0.7.0.1
 */