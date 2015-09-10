package cern.colt.matrix.bench;

import cern.colt.Timer;
import cern.colt.Version;
import cern.colt.function.Double9Function;
import cern.colt.list.ObjectArrayList;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleFactory3D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.DoubleMatrix3D;
import cern.colt.matrix.doublealgo.Formatter;
import cern.colt.matrix.doublealgo.Statistic;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.impl.Former;
import cern.colt.matrix.impl.FormerFactory;
import cern.colt.matrix.linalg.Algebra;
import cern.colt.matrix.linalg.Blas;
import cern.colt.matrix.linalg.LUDecompositionQuick;
import cern.colt.matrix.linalg.Property;
import cern.colt.matrix.linalg.SeqBlas;
import cern.colt.matrix.linalg.SmpBlas;
import cern.jet.math.Functions;
import hep.aida.bin.BinFunction1D;
import hep.aida.bin.BinFunctions1D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StreamTokenizer;

public class BenchmarkMatrix
{
  protected static void bench_dgemm(String[] paramArrayOfString)
  {
    String[] arrayOfString;
    int i;
    double d;
    double[] arrayOfDouble;
    boolean bool1;
    boolean bool2;
    int[] arrayOfInt;
    try
    {
      int j = 1;
      arrayOfString = new String[] { paramArrayOfString[(j++)] };
      i = Integer.parseInt(paramArrayOfString[(j++)]);
      d = new Double(paramArrayOfString[(j++)]).doubleValue();
      arrayOfDouble = new double[] { new Double(paramArrayOfString[(j++)]).doubleValue() };
      bool1 = new Boolean(paramArrayOfString[(j++)]).booleanValue();
      bool2 = new Boolean(paramArrayOfString[(j++)]).booleanValue();
      arrayOfInt = new int[paramArrayOfString.length - j];
      for (int k = 0; j < paramArrayOfString.length; k++)
      {
        arrayOfInt[k] = Integer.parseInt(paramArrayOfString[j]);
        j++;
      }
    }
    catch (Exception localException)
    {
      System.out.println(usage(paramArrayOfString[0]));
      System.out.println("Ignoring command...\n");
      return;
    }
    SmpBlas.allocateBlas(i, SeqBlas.seqBlas);
    Double2DProcedure localDouble2DProcedure = fun_dgemm(bool1, bool2);
    String str1 = localDouble2DProcedure.toString();
    String str2 = bool1 + ", " + bool2 + ", 1, A, B, 0, C";
    str1 = str1 + " dgemm(" + str2 + ")";
    run(d, str1, localDouble2DProcedure, arrayOfString, arrayOfInt, arrayOfDouble);
  }
  
  protected static void bench_dgemv(String[] paramArrayOfString)
  {
    String[] arrayOfString;
    int i;
    double d;
    double[] arrayOfDouble;
    boolean bool;
    int[] arrayOfInt;
    try
    {
      int j = 1;
      arrayOfString = new String[] { paramArrayOfString[(j++)] };
      i = Integer.parseInt(paramArrayOfString[(j++)]);
      d = new Double(paramArrayOfString[(j++)]).doubleValue();
      arrayOfDouble = new double[] { new Double(paramArrayOfString[(j++)]).doubleValue() };
      bool = new Boolean(paramArrayOfString[(j++)]).booleanValue();
      arrayOfInt = new int[paramArrayOfString.length - j];
      for (int k = 0; j < paramArrayOfString.length; k++)
      {
        arrayOfInt[k] = Integer.parseInt(paramArrayOfString[j]);
        j++;
      }
    }
    catch (Exception localException)
    {
      System.out.println(usage(paramArrayOfString[0]));
      System.out.println("Ignoring command...\n");
      return;
    }
    SmpBlas.allocateBlas(i, SeqBlas.seqBlas);
    Double2DProcedure localDouble2DProcedure = fun_dgemv(bool);
    String str1 = localDouble2DProcedure.toString();
    String str2 = bool + ", 1, A, B, 0, C";
    str1 = str1 + " dgemv(" + str2 + ")";
    run(d, str1, localDouble2DProcedure, arrayOfString, arrayOfInt, arrayOfDouble);
  }
  
  protected static void bench_pow(String[] paramArrayOfString)
  {
    String[] arrayOfString;
    int i;
    double d;
    double[] arrayOfDouble;
    int j;
    int[] arrayOfInt;
    try
    {
      int k = 1;
      arrayOfString = new String[] { paramArrayOfString[(k++)] };
      i = Integer.parseInt(paramArrayOfString[(k++)]);
      d = new Double(paramArrayOfString[(k++)]).doubleValue();
      arrayOfDouble = new double[] { new Double(paramArrayOfString[(k++)]).doubleValue() };
      j = Integer.parseInt(paramArrayOfString[(k++)]);
      arrayOfInt = new int[paramArrayOfString.length - k];
      for (int m = 0; k < paramArrayOfString.length; m++)
      {
        arrayOfInt[m] = Integer.parseInt(paramArrayOfString[k]);
        k++;
      }
    }
    catch (Exception localException)
    {
      System.out.println(usage(paramArrayOfString[0]));
      System.out.println("Ignoring command...\n");
      return;
    }
    SmpBlas.allocateBlas(i, SeqBlas.seqBlas);
    Double2DProcedure localDouble2DProcedure = fun_pow(j);
    String str1 = localDouble2DProcedure.toString();
    String str2 = "A," + j;
    str1 = str1 + " pow(" + str2 + ")";
    run(d, str1, localDouble2DProcedure, arrayOfString, arrayOfInt, arrayOfDouble);
  }
  
  protected static void benchGeneric(Double2DProcedure paramDouble2DProcedure, String[] paramArrayOfString)
  {
    String[] arrayOfString;
    int i;
    double d;
    double[] arrayOfDouble;
    int[] arrayOfInt;
    try
    {
      int j = 1;
      arrayOfString = new String[] { paramArrayOfString[(j++)] };
      i = Integer.parseInt(paramArrayOfString[(j++)]);
      d = new Double(paramArrayOfString[(j++)]).doubleValue();
      arrayOfDouble = new double[] { new Double(paramArrayOfString[(j++)]).doubleValue() };
      arrayOfInt = new int[paramArrayOfString.length - j];
      for (int k = 0; j < paramArrayOfString.length; k++)
      {
        arrayOfInt[k] = Integer.parseInt(paramArrayOfString[j]);
        j++;
      }
    }
    catch (Exception localException)
    {
      System.out.println(usage(paramArrayOfString[0]));
      System.out.println("Ignoring command...\n");
      return;
    }
    SmpBlas.allocateBlas(i, SeqBlas.seqBlas);
    String str = paramDouble2DProcedure.toString();
    run(d, str, paramDouble2DProcedure, arrayOfString, arrayOfInt, arrayOfDouble);
  }
  
  protected static String commands()
  {
    return "dgemm, dgemv, pow, assign, assignGetSet, assignGetSetQuick, assignLog, assignPlusMult, elementwiseMult, elementwiseMultB, SOR5, SOR8, LUDecompose, LUSolve";
  }
  
  protected static Double2DProcedure fun_dgemm(boolean paramBoolean1, boolean paramBoolean2)
  {
    new Double2DProcedure()
    {
      private final boolean val$transposeA;
      private final boolean val$transposeB;
      
      public String toString()
      {
        return "Blas matrix-matrix mult";
      }
      
      public void setParameters(DoubleMatrix2D paramAnonymousDoubleMatrix2D1, DoubleMatrix2D paramAnonymousDoubleMatrix2D2)
      {
        super.setParameters(paramAnonymousDoubleMatrix2D1, paramAnonymousDoubleMatrix2D2);
        this.D = new DenseDoubleMatrix2D(this.A.rows(), this.A.columns()).assign(0.5D);
        this.C = this.D.copy();
        this.B = this.D.copy();
      }
      
      public void init()
      {
        this.C.assign(this.D);
      }
      
      public void apply(Timer paramAnonymousTimer)
      {
        SmpBlas.smpBlas.dgemm(this.val$transposeA, this.val$transposeB, 1.0D, this.A, this.B, 0.0D, this.C);
      }
      
      public double operations()
      {
        double d1 = this.A.rows();
        double d2 = this.A.columns();
        double d3 = this.B.columns();
        return 2.0D * d1 * d2 * d3 / 1000000.0D;
      }
    };
  }
  
  protected static Double2DProcedure fun_dgemv(boolean paramBoolean)
  {
    new Double2DProcedure()
    {
      private final boolean val$transposeA;
      
      public String toString()
      {
        return "Blas matrix-vector mult";
      }
      
      public void setParameters(DoubleMatrix2D paramAnonymousDoubleMatrix2D1, DoubleMatrix2D paramAnonymousDoubleMatrix2D2)
      {
        super.setParameters(paramAnonymousDoubleMatrix2D1, paramAnonymousDoubleMatrix2D2);
        this.D = new DenseDoubleMatrix2D(this.A.rows(), this.A.columns()).assign(0.5D);
        this.C = this.D.copy();
        this.B = this.D.copy();
      }
      
      public void init()
      {
        this.C.viewRow(0).assign(this.D.viewRow(0));
      }
      
      public void apply(Timer paramAnonymousTimer)
      {
        SmpBlas.smpBlas.dgemv(this.val$transposeA, 1.0D, this.A, this.B.viewRow(0), 0.0D, this.C.viewRow(0));
      }
      
      public double operations()
      {
        double d1 = this.A.rows();
        double d2 = this.A.columns();
        return 2.0D * d1 * d2 / 1000000.0D;
      }
    };
  }
  
  protected static Double2DProcedure fun_pow(int paramInt)
  {
    new Double2DProcedure()
    {
      public double dummy;
      private final int val$k;
      
      public String toString()
      {
        return "matrix to the power of an exponent";
      }
      
      public void setParameters(DoubleMatrix2D paramAnonymousDoubleMatrix2D1, DoubleMatrix2D paramAnonymousDoubleMatrix2D2)
      {
        if (this.val$k < 0)
        {
          if ((!Property.ZERO.isDiagonallyDominantByRow(paramAnonymousDoubleMatrix2D1)) || (!Property.ZERO.isDiagonallyDominantByColumn(paramAnonymousDoubleMatrix2D1))) {
            Property.ZERO.generateNonSingular(paramAnonymousDoubleMatrix2D1);
          }
          super.setParameters(paramAnonymousDoubleMatrix2D1, paramAnonymousDoubleMatrix2D2);
        }
      }
      
      public void init() {}
      
      public void apply(Timer paramAnonymousTimer)
      {
        Algebra.DEFAULT.pow(this.A, this.val$k);
      }
      
      public double operations()
      {
        double d1 = this.A.rows();
        if (this.val$k == 0) {
          return d1;
        }
        double d2 = 0.0D;
        if (this.val$k < 0)
        {
          double d3 = Math.min(this.A.rows(), this.A.columns());
          d2 += 2.0D * d3 * d3 * d3 / 3.0D / 1000000.0D;
          double d4 = this.A.columns();
          double d5 = this.B.columns();
          d2 += 2.0D * d5 * (d4 * d4 + d4) / 1000000.0D;
        }
        d2 += 2.0D * (Math.abs(this.val$k) - 1) * d1 * d1 * d1 / 1000000.0D;
        return d2;
      }
    };
  }
  
  protected static Double2DProcedure funAssign()
  {
    new Double2DProcedure()
    {
      public String toString()
      {
        return "A.assign(B) [Mops/sec]";
      }
      
      public void init()
      {
        this.A.assign(0.0D);
      }
      
      public void apply(Timer paramAnonymousTimer)
      {
        this.A.assign(this.B);
      }
    };
  }
  
  protected static Double2DProcedure funAssignGetSet()
  {
    new Double2DProcedure()
    {
      public String toString()
      {
        return "A.assign(B) via get and set [Mops/sec]";
      }
      
      public void init()
      {
        this.A.assign(0.0D);
      }
      
      public void apply(Timer paramAnonymousTimer)
      {
        int i = this.B.rows();
        int j = this.B.columns();
        for (int k = 0; k < i; k++) {
          for (int m = 0; m < j; m++) {
            this.A.set(k, m, this.B.get(k, m));
          }
        }
      }
    };
  }
  
  protected static Double2DProcedure funAssignGetSetQuick()
  {
    new Double2DProcedure()
    {
      public String toString()
      {
        return "A.assign(B) via getQuick and setQuick [Mops/sec]";
      }
      
      public void init()
      {
        this.A.assign(0.0D);
      }
      
      public void apply(Timer paramAnonymousTimer)
      {
        int i = this.B.rows();
        int j = this.B.columns();
        for (int k = 0; k < i; k++) {
          for (int m = 0; m < j; m++) {
            this.A.setQuick(k, m, this.B.getQuick(k, m));
          }
        }
      }
    };
  }
  
  protected static Double2DProcedure funAssignLog()
  {
    new Double2DProcedure()
    {
      public String toString()
      {
        return "A[i,j] = log(A[i,j]) via Blas.assign(fun) [Mflops/sec]";
      }
      
      public void init()
      {
        this.A.assign(this.C);
      }
      
      public void apply(Timer paramAnonymousTimer)
      {
        SmpBlas.smpBlas.assign(this.A, Functions.log);
      }
    };
  }
  
  protected static Double2DProcedure funAssignPlusMult()
  {
    new Double2DProcedure()
    {
      public String toString()
      {
        return "A[i,j] = A[i,j] + s*B[i,j] via Blas.assign(fun) [Mflops/sec]";
      }
      
      public void init()
      {
        this.A.assign(this.C);
      }
      
      public void apply(Timer paramAnonymousTimer)
      {
        SmpBlas.smpBlas.assign(this.A, this.B, Functions.plusMult(0.5D));
      }
      
      public double operations()
      {
        double d1 = this.A.rows();
        double d2 = this.A.columns();
        return 2.0D * d1 * d2 / 1000000.0D;
      }
    };
  }
  
  protected static Double2DProcedure funCorrelation()
  {
    new Double2DProcedure()
    {
      public String toString()
      {
        return "xxxxxxx";
      }
      
      public void init() {}
      
      public void setParameters(DoubleMatrix2D paramAnonymousDoubleMatrix2D1, DoubleMatrix2D paramAnonymousDoubleMatrix2D2)
      {
        super.setParameters(paramAnonymousDoubleMatrix2D1.viewDice(), paramAnonymousDoubleMatrix2D2);
      }
      
      public void apply(Timer paramAnonymousTimer)
      {
        Statistic.correlation(Statistic.covariance(this.A));
      }
      
      public double operations()
      {
        double d1 = this.A.rows();
        double d2 = this.A.columns();
        return d1 * (d2 * d2 + d2) / 1000000.0D;
      }
    };
  }
  
  protected static Double2DProcedure funElementwiseMult()
  {
    new Double2DProcedure()
    {
      public String toString()
      {
        return "A.assign(F.mult(0.5)) via Blas [Mflops/sec]";
      }
      
      public void init()
      {
        this.A.assign(this.C);
      }
      
      public void apply(Timer paramAnonymousTimer)
      {
        SmpBlas.smpBlas.assign(this.A, Functions.mult(0.5D));
      }
    };
  }
  
  protected static Double2DProcedure funElementwiseMultB()
  {
    new Double2DProcedure()
    {
      public String toString()
      {
        return "A.assign(B,F.mult) via Blas [Mflops/sec]";
      }
      
      public void init()
      {
        this.A.assign(this.C);
      }
      
      public void apply(Timer paramAnonymousTimer)
      {
        SmpBlas.smpBlas.assign(this.A, this.B, Functions.mult);
      }
    };
  }
  
  protected static Double2DProcedure funGetQuick()
  {
    new Double2DProcedure()
    {
      public double dummy;
      
      public String toString()
      {
        return "xxxxxxx";
      }
      
      public void init() {}
      
      public void apply(Timer paramAnonymousTimer)
      {
        int i = this.B.rows();
        int j = this.B.columns();
        double d = 0.0D;
        for (int k = 0; k < i; k++) {
          for (int m = 0; m < j; m++) {
            d += this.A.getQuick(k, m);
          }
        }
        this.dummy = d;
      }
    };
  }
  
  protected static Double2DProcedure funLUDecompose()
  {
    new Double2DProcedure()
    {
      LUDecompositionQuick lu = new LUDecompositionQuick(0.0D);
      
      public String toString()
      {
        return "LU.decompose(A) [Mflops/sec]";
      }
      
      public void init()
      {
        this.A.assign(this.C);
      }
      
      public void apply(Timer paramAnonymousTimer)
      {
        this.lu.decompose(this.A);
      }
      
      public double operations()
      {
        double d = Math.min(this.A.rows(), this.A.columns());
        return 2.0D * d * d * d / 3.0D / 1000000.0D;
      }
    };
  }
  
  protected static Double2DProcedure funLUSolve()
  {
    new Double2DProcedure()
    {
      LUDecompositionQuick lu;
      
      public String toString()
      {
        return "LU.solve(A) [Mflops/sec]";
      }
      
      public void setParameters(DoubleMatrix2D paramAnonymousDoubleMatrix2D1, DoubleMatrix2D paramAnonymousDoubleMatrix2D2)
      {
        this.lu = null;
        if ((!Property.ZERO.isDiagonallyDominantByRow(paramAnonymousDoubleMatrix2D1)) || (!Property.ZERO.isDiagonallyDominantByColumn(paramAnonymousDoubleMatrix2D1))) {
          Property.ZERO.generateNonSingular(paramAnonymousDoubleMatrix2D1);
        }
        super.setParameters(paramAnonymousDoubleMatrix2D1, paramAnonymousDoubleMatrix2D2);
        this.lu = new LUDecompositionQuick(0.0D);
        this.lu.decompose(paramAnonymousDoubleMatrix2D1);
      }
      
      public void init()
      {
        this.B.assign(this.D);
      }
      
      public void apply(Timer paramAnonymousTimer)
      {
        this.lu.solve(this.B);
      }
      
      public double operations()
      {
        double d1 = this.A.columns();
        double d2 = this.B.columns();
        return 2.0D * d2 * (d1 * d1 + d1) / 1000000.0D;
      }
    };
  }
  
  protected static Double2DProcedure funMatMultLarge()
  {
    new Double2DProcedure()
    {
      public String toString()
      {
        return "xxxxxxx";
      }
      
      public void setParameters(DoubleMatrix2D paramAnonymousDoubleMatrix2D1, DoubleMatrix2D paramAnonymousDoubleMatrix2D2)
      {
        this.A = paramAnonymousDoubleMatrix2D1;
        this.B = paramAnonymousDoubleMatrix2D2;
        this.C = paramAnonymousDoubleMatrix2D1.copy();
      }
      
      public void init()
      {
        this.C.assign(0.0D);
      }
      
      public void apply(Timer paramAnonymousTimer)
      {
        this.A.zMult(this.B, this.C);
      }
      
      public double operations()
      {
        double d1 = this.A.rows();
        double d2 = this.A.columns();
        double d3 = this.B.columns();
        return 2.0D * d1 * d2 * d3 / 1000000.0D;
      }
    };
  }
  
  protected static Double2DProcedure funMatVectorMult()
  {
    new Double2DProcedure()
    {
      public String toString()
      {
        return "xxxxxxx";
      }
      
      public void setParameters(DoubleMatrix2D paramAnonymousDoubleMatrix2D1, DoubleMatrix2D paramAnonymousDoubleMatrix2D2)
      {
        super.setParameters(paramAnonymousDoubleMatrix2D1, paramAnonymousDoubleMatrix2D2);
        this.D = new DenseDoubleMatrix2D(this.A.rows(), this.A.columns()).assign(0.5D);
        this.C = this.D.copy();
        this.B = this.D.copy();
      }
      
      public void init()
      {
        this.C.viewRow(0).assign(this.D.viewRow(0));
      }
      
      public void apply(Timer paramAnonymousTimer)
      {
        this.A.zMult(this.B.viewRow(0), this.C.viewRow(0));
      }
      
      public double operations()
      {
        double d1 = this.A.rows();
        double d2 = this.A.columns();
        return 2.0D * d1 * d2 / 1000000.0D;
      }
    };
  }
  
  protected static Double2DProcedure funSetQuick()
  {
    new Double2DProcedure()
    {
      private int current;
      private double density;
      
      public String toString()
      {
        return "xxxxxxx";
      }
      
      public void init()
      {
        this.A.assign(0.0D);
        int i = 123456;
        this.current = (4 * i + 1);
        this.density = (this.A.cardinality() / this.A.size());
      }
      
      public void apply(Timer paramAnonymousTimer)
      {
        int i = this.B.rows();
        int j = this.B.columns();
        for (int k = 0; k < i; k++) {
          for (int m = 0; m < j; m++)
          {
            this.current *= 663608941;
            double d = (this.current & 0xFFFFFFFF) * 2.328306436538696E-010D;
            if (d < this.density) {
              this.A.setQuick(k, m, d);
            } else {
              this.A.setQuick(k, m, 0.0D);
            }
          }
        }
      }
    };
  }
  
  protected static Double2DProcedure funSOR5()
  {
    new Double2DProcedure()
    {
      double value = 2.0D;
      double omega = 1.25D;
      final double alpha = this.omega * 0.25D;
      final double beta = 1.0D - this.omega;
      Double9Function function = new BenchmarkMatrix.19(this);
      
      public String toString()
      {
        return "A.zAssign8Neighbors(5 point function) [Mflops/sec]";
      }
      
      public void init()
      {
        this.B.assign(this.D);
      }
      
      public void apply(Timer paramAnonymousTimer)
      {
        this.A.zAssign8Neighbors(this.B, this.function);
      }
      
      public double operations()
      {
        double d1 = this.A.columns();
        double d2 = this.A.rows();
        return 6.0D * d2 * d1 / 1000000.0D;
      }
    };
  }
  
  protected static Double2DProcedure funSOR8()
  {
    new Double2DProcedure()
    {
      double value = 2.0D;
      double omega = 1.25D;
      final double alpha = this.omega * 0.25D;
      final double beta = 1.0D - this.omega;
      Double9Function function = new BenchmarkMatrix.21(this);
      
      public String toString()
      {
        return "A.zAssign8Neighbors(9 point function) [Mflops/sec]";
      }
      
      public void init()
      {
        this.B.assign(this.D);
      }
      
      public void apply(Timer paramAnonymousTimer)
      {
        this.A.zAssign8Neighbors(this.B, this.function);
      }
      
      public double operations()
      {
        double d1 = this.A.columns();
        double d2 = this.A.rows();
        return 10.0D * d2 * d1 / 1000000.0D;
      }
    };
  }
  
  protected static Double2DProcedure funSort()
  {
    new Double2DProcedure()
    {
      public String toString()
      {
        return "xxxxxxx";
      }
      
      public void init()
      {
        this.A.assign(this.C);
      }
      
      public void apply(Timer paramAnonymousTimer)
      {
        this.A.viewSorted(0);
      }
    };
  }
  
  protected static DoubleFactory2D getFactory(String paramString)
  {
    if (paramString.equals("dense")) {
      return DoubleFactory2D.dense;
    }
    if (paramString.equals("sparse")) {
      return DoubleFactory2D.sparse;
    }
    if (paramString.equals("rowCompressed")) {
      return DoubleFactory2D.rowCompressed;
    }
    String str = "type=" + paramString + " is unknown. Use one of {dense,sparse,rowCompressed}";
    throw new IllegalArgumentException(str);
  }
  
  protected static Double2DProcedure getGenericFunction(String paramString)
  {
    if (paramString.equals("dgemm")) {
      return fun_dgemm(false, false);
    }
    if (paramString.equals("dgemv")) {
      return fun_dgemv(false);
    }
    if (paramString.equals("pow")) {
      return fun_pow(2);
    }
    if (paramString.equals("assign")) {
      return funAssign();
    }
    if (paramString.equals("assignGetSet")) {
      return funAssignGetSet();
    }
    if (paramString.equals("assignGetSetQuick")) {
      return funAssignGetSetQuick();
    }
    if (paramString.equals("elementwiseMult")) {
      return funElementwiseMult();
    }
    if (paramString.equals("elementwiseMultB")) {
      return funElementwiseMultB();
    }
    if (paramString.equals("SOR5")) {
      return funSOR5();
    }
    if (paramString.equals("SOR8")) {
      return funSOR8();
    }
    if (paramString.equals("LUDecompose")) {
      return funLUDecompose();
    }
    if (paramString.equals("LUSolve")) {
      return funLUSolve();
    }
    if (paramString.equals("assignLog")) {
      return funAssignLog();
    }
    if (paramString.equals("assignPlusMult")) {
      return funAssignPlusMult();
    }
    return null;
  }
  
  protected static boolean handle(String[] paramArrayOfString)
  {
    boolean bool = true;
    String str1 = paramArrayOfString[0];
    if (str1.equals("dgemm"))
    {
      bench_dgemm(paramArrayOfString);
    }
    else if (str1.equals("dgemv"))
    {
      bench_dgemv(paramArrayOfString);
    }
    else if (str1.equals("pow"))
    {
      bench_pow(paramArrayOfString);
    }
    else
    {
      Double2DProcedure localDouble2DProcedure = getGenericFunction(str1);
      if (localDouble2DProcedure != null)
      {
        benchGeneric(localDouble2DProcedure, paramArrayOfString);
      }
      else
      {
        bool = false;
        String str2 = "Command=" + paramArrayOfString[0] + " is illegal or unknown. Should be one of " + commands() + "followed by appropriate parameters.\n" + usage() + "\nIgnoring this line.\n";
        System.out.println(str2);
      }
    }
    return bool;
  }
  
  public static void main(String[] paramArrayOfString)
  {
    int i = paramArrayOfString.length;
    if ((i == 0) || ((i <= 1) && (paramArrayOfString[0].equals("-help"))))
    {
      System.out.println(usage());
      return;
    }
    if (paramArrayOfString[0].equals("-help"))
    {
      if (commands().indexOf(paramArrayOfString[1]) < 0) {
        System.out.println(paramArrayOfString[1] + ": no such command available.\n" + usage());
      } else {
        System.out.println(usage(paramArrayOfString[1]));
      }
      return;
    }
    System.out.println("Colt Matrix benchmark running on\n");
    System.out.println(BenchmarkKernel.systemInfo() + "\n");
    System.out.println("Colt Version is " + Version.asString() + "\n");
    Timer localTimer = new Timer().start();
    if (!paramArrayOfString[0].equals("-file"))
    {
      System.out.println("\n\nExecuting command = " + new ObjectArrayList(paramArrayOfString) + " ...");
      handle(paramArrayOfString);
    }
    else
    {
      BufferedReader localBufferedReader = null;
      try
      {
        localBufferedReader = new BufferedReader(new FileReader(paramArrayOfString[1]));
      }
      catch (IOException localIOException1)
      {
        throw new RuntimeException(localIOException1.getMessage());
      }
      StreamTokenizer localStreamTokenizer = new StreamTokenizer(localBufferedReader);
      localStreamTokenizer.eolIsSignificant(true);
      localStreamTokenizer.slashSlashComments(true);
      localStreamTokenizer.slashStarComments(true);
      try
      {
        ObjectArrayList localObjectArrayList = new ObjectArrayList();
        int j;
        while ((j = localStreamTokenizer.nextToken()) != -1)
        {
          Object localObject;
          if (j == 10)
          {
            if (localObjectArrayList.size() > 0)
            {
              localObject = new String[localObjectArrayList.size()];
              for (int k = 0; k < localObjectArrayList.size(); k++) {
                localObject[k] = ((String)localObjectArrayList.get(k));
              }
              System.out.println("\n\nExecuting command = " + localObjectArrayList + " ...");
              handle((String[])localObject);
            }
            localObjectArrayList.clear();
          }
          else
          {
            Former localFormer = new FormerFactory().create("%G");
            if (j == -2) {
              localObject = localFormer.form(localStreamTokenizer.nval);
            } else {
              localObject = localStreamTokenizer.sval;
            }
            if (localObject != null) {
              localObjectArrayList.add(localObject);
            }
          }
        }
        localBufferedReader.close();
        System.out.println("\nCommand file name used: " + paramArrayOfString[1] + "\nTo reproduce and compare results, here it's contents:");
        try
        {
          localBufferedReader = new BufferedReader(new FileReader(paramArrayOfString[1]));
        }
        catch (IOException localIOException3)
        {
          throw new RuntimeException(localIOException3.getMessage());
        }
        String str;
        while ((str = localBufferedReader.readLine()) != null) {
          System.out.println(str);
        }
        localBufferedReader.close();
      }
      catch (IOException localIOException2)
      {
        throw new RuntimeException(localIOException2.getMessage());
      }
    }
    System.out.println("\nProgram execution took a total of " + localTimer.minutes() + " minutes.");
    System.out.println("Good bye.");
  }
  
  protected static void run(double paramDouble, String paramString, Double2DProcedure paramDouble2DProcedure, String[] paramArrayOfString, int[] paramArrayOfInt, double[] paramArrayOfDouble)
  {
    DoubleMatrix3D localDoubleMatrix3D = DoubleFactory3D.dense.make(paramArrayOfString.length, paramArrayOfInt.length, paramArrayOfDouble.length);
    Timer localTimer = new Timer().start();
    for (int i = 0; i < paramArrayOfString.length; i++)
    {
      localObject1 = getFactory(paramArrayOfString[i]);
      System.out.print("\n@");
      for (int j = 0; j < paramArrayOfInt.length; j++)
      {
        int k = paramArrayOfInt[j];
        System.out.print("x");
        for (int m = 0; m < paramArrayOfDouble.length; m++)
        {
          double d1 = paramArrayOfDouble[m];
          System.out.print(".");
          float f;
          if ((i <= 0) || (d1 < 0.1D) || (k < 500))
          {
            double d2 = 0.5D;
            paramDouble2DProcedure.A = null;
            paramDouble2DProcedure.B = null;
            paramDouble2DProcedure.C = null;
            paramDouble2DProcedure.D = null;
            DoubleMatrix2D localDoubleMatrix2D1 = ((DoubleFactory2D)localObject1).sample(k, k, d2, d1);
            DoubleMatrix2D localDoubleMatrix2D2 = ((DoubleFactory2D)localObject1).sample(k, k, d2, d1);
            paramDouble2DProcedure.setParameters(localDoubleMatrix2D1, localDoubleMatrix2D2);
            localDoubleMatrix2D1 = null;
            localDoubleMatrix2D2 = null;
            double d3 = paramDouble2DProcedure.operations();
            double d4 = BenchmarkKernel.run(paramDouble, paramDouble2DProcedure);
            f = (float)(d3 / d4);
          }
          else
          {
            f = (0.0F / 0.0F);
          }
          localDoubleMatrix3D.set(i, j, m, f);
        }
      }
    }
    localTimer.stop();
    String str = "type";
    Object localObject1 = "size";
    Object localObject2 = "d";
    String[] arrayOfString = paramArrayOfString;
    BinFunctions1D localBinFunctions1D = BinFunctions1D.functions;
    BinFunction1D[] arrayOfBinFunction1D = null;
    Object localObject3 = new String[paramArrayOfInt.length];
    Object localObject4 = new String[paramArrayOfDouble.length];
    int n = paramArrayOfInt.length;
    for (;;)
    {
      n--;
      if (n < 0) {
        break;
      }
      localObject3[n] = Integer.toString(paramArrayOfInt[n]);
    }
    n = paramArrayOfDouble.length;
    for (;;)
    {
      n--;
      if (n < 0) {
        break;
      }
      localObject4[n] = Double.toString(paramArrayOfDouble[n]);
    }
    System.out.println("*");
    Object localObject5 = localObject1;
    localObject1 = localObject2;
    localObject2 = localObject5;
    Object localObject6 = localObject3;
    localObject3 = localObject4;
    localObject4 = localObject6;
    localDoubleMatrix3D = localDoubleMatrix3D.viewDice(0, 2, 1);
    System.out.println(new Formatter("%1.3G").toTitleString(localDoubleMatrix3D, arrayOfString, (String[])localObject3, (String[])localObject4, str, (String)localObject1, (String)localObject2, "Performance of " + paramString, arrayOfBinFunction1D));
    System.out.println("Run took a total of " + localTimer + ". End of run.");
  }
  
  protected static void runSpecial(double paramDouble, String paramString, Double2DProcedure paramDouble2DProcedure)
  {
    int[] arrayOfInt = { 10000 };
    double[] arrayOfDouble = { 1.E-005D };
    boolean[] arrayOfBoolean = { true };
    DoubleMatrix2D localDoubleMatrix2D1 = DoubleFactory2D.dense.make(arrayOfInt.length, 4);
    Timer localTimer = new Timer().start();
    for (int i = 0; i < arrayOfInt.length; i++)
    {
      int j = arrayOfInt[i];
      double d1 = arrayOfDouble[i];
      int k = arrayOfBoolean[i];
      localDoubleFactory2D = k != 0 ? DoubleFactory2D.sparse : DoubleFactory2D.dense;
      System.out.print("\n@");
      System.out.print("x");
      double d2 = 0.5D;
      paramDouble2DProcedure.A = null;
      paramDouble2DProcedure.B = null;
      paramDouble2DProcedure.C = null;
      paramDouble2DProcedure.D = null;
      DoubleMatrix2D localDoubleMatrix2D2 = localDoubleFactory2D.sample(j, j, d2, d1);
      DoubleMatrix2D localDoubleMatrix2D3 = localDoubleFactory2D.sample(j, j, d2, d1);
      paramDouble2DProcedure.setParameters(localDoubleMatrix2D2, localDoubleMatrix2D3);
      localDoubleMatrix2D2 = null;
      localDoubleMatrix2D3 = null;
      float f1 = BenchmarkKernel.run(paramDouble, paramDouble2DProcedure);
      double d3 = paramDouble2DProcedure.operations();
      float f2 = (float)(d3 / f1);
      localDoubleMatrix2D1.viewRow(i).set(0, k != 0 ? 0.0D : 1.0D);
      localDoubleMatrix2D1.viewRow(i).set(1, j);
      localDoubleMatrix2D1.viewRow(i).set(2, d1);
      localDoubleMatrix2D1.viewRow(i).set(3, f2);
    }
    localTimer.stop();
    BinFunctions1D localBinFunctions1D = BinFunctions1D.functions;
    BinFunction1D[] arrayOfBinFunction1D = null;
    String[] arrayOfString1 = null;
    String[] arrayOfString2 = { "dense (y=1,n=0)", "size", "density", "flops/sec" };
    String str = null;
    DoubleFactory2D localDoubleFactory2D = null;
    System.out.println("*");
    System.out.println(new Formatter("%1.3G").toTitleString(localDoubleMatrix2D1, arrayOfString1, arrayOfString2, str, localDoubleFactory2D, paramString, arrayOfBinFunction1D));
    System.out.println("Run took a total of " + localTimer + ". End of run.");
  }
  
  protected static String usage()
  {
    String str = "\nUsage (help): To get this help, type java cern.colt.matrix.bench.BenchmarkMatrix -help\nTo get help on a command's args, omit args and type java cern.colt.matrix.bench.BenchmarkMatrix -help <command>\nAvailable commands: " + commands() + "\n\n" + "Usage (direct): java cern.colt.matrix.bench.BenchmarkMatrix command {args}\n" + "Example: dgemm dense 2 2.0 0.999 false true 5 10 25 50 100 250 500\n\n" + "Usage (batch mode): java cern.colt.matrix.bench.BenchmarkMatrix -file <file>\nwhere <file> is a text file with each line holding a command followed by appropriate args (comments and empty lines ignored).\n\n" + "Example file's content:\n" + "dgemm dense 1 2.0 0.999 false true 5 10 25 50 100 250 500\n" + "dgemm dense 2 2.0 0.999 false true 5 10 25 50 100 250 500\n\n" + "/*\n" + "Java like comments in file are ignored\n" + "dgemv dense 1 2.0 0.001 false 5 10 25 50 100 250 500 1000\n" + "dgemv sparse 1 2.0 0.001 false 5 10 25 50 100 250 500 1000\n" + "dgemv rowCompressed 1 2.0 0.001 false 5 10 25 50 100 250 500 1000\n" + "*/\n" + "// more comments ignored\n";
    return str;
  }
  
  protected static String usage(String paramString)
  {
    String str = paramString + " description: " + getGenericFunction(paramString).toString() + "\nArguments to be supplied:\n" + "\t<operation> <type> <cpus> <minSecs> <density>";
    if (paramString.equals("dgemv")) {
      str = str + " <transposeA>";
    }
    if (paramString.equals("dgemm")) {
      str = str + " <transposeA> <transposeB>";
    }
    if (paramString.equals("pow")) {
      str = str + " <exponent>";
    }
    str = str + " {sizes}\n" + "where\n" + "\toperation = the operation to benchmark; in this case: " + paramString + "\n" + "\ttype = matrix type to be used; e.g. dense, sparse or rowCompressed\n" + "\tcpus = #cpus available; e.g. 1 or 2 or ...\n" + "\tminSecs = #seconds each operation shall at least run; e.g. 2.0 is a good number giving realistic timings\n" + "\tdensity = the density of the matrices to be benchmarked; e.g. 0.999 is very dense, 0.001 is very sparse\n";
    if (paramString.equals("dgemv")) {
      str = str + "\ttransposeA = false or true\n";
    }
    if (paramString.equals("dgemm")) {
      str = str + "\ttransposeA = false or true\n\ttransposeB = false or true\n";
    }
    if (paramString.equals("pow")) {
      str = str + "\texponent = the number of times to multiply; e.g. 1000\n";
    }
    str = str + "\tsizes = a list of problem sizes; e.g. 100 200 benchmarks squared 100x100 and 200x200 matrices";
    return str;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.bench.BenchmarkMatrix
 * JD-Core Version:    0.7.0.1
 */