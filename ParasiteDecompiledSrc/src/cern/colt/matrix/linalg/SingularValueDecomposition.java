package cern.colt.matrix.linalg;

import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix2D;
import java.io.Serializable;

public class SingularValueDecomposition
  implements Serializable
{
  static final long serialVersionUID = 1020L;
  private double[][] U;
  private double[][] V;
  private double[] s;
  private int m;
  private int n;
  
  public SingularValueDecomposition(DoubleMatrix2D paramDoubleMatrix2D)
  {
    Property.DEFAULT.checkRectangular(paramDoubleMatrix2D);
    double[][] arrayOfDouble = paramDoubleMatrix2D.toArray();
    this.m = paramDoubleMatrix2D.rows();
    this.n = paramDoubleMatrix2D.columns();
    int i = Math.min(this.m, this.n);
    this.s = new double[Math.min(this.m + 1, this.n)];
    this.U = new double[this.m][i];
    this.V = new double[this.n][this.n];
    double[] arrayOfDouble1 = new double[this.n];
    double[] arrayOfDouble2 = new double[this.m];
    int j = 1;
    int k = 1;
    int i1 = Math.min(this.m - 1, this.n);
    int i2 = Math.max(0, Math.min(this.n - 2, this.m));
    for (int i3 = 0; i3 < Math.max(i1, i2); i3++)
    {
      if (i3 < i1)
      {
        this.s[i3] = 0.0D;
        for (i4 = i3; i4 < this.m; i4++) {
          this.s[i3] = Algebra.hypot(this.s[i3], arrayOfDouble[i4][i3]);
        }
        if (this.s[i3] != 0.0D)
        {
          if (arrayOfDouble[i3][i3] < 0.0D) {
            this.s[i3] = (-this.s[i3]);
          }
          for (i4 = i3; i4 < this.m; i4++) {
            arrayOfDouble[i4][i3] /= this.s[i3];
          }
          arrayOfDouble[i3][i3] += 1.0D;
        }
        this.s[i3] = (-this.s[i3]);
      }
      int i7;
      for (i4 = i3 + 1; i4 < this.n; i4++)
      {
        if (((i3 < i1 ? 1 : 0) & (this.s[i3] != 0.0D ? 1 : 0)) != 0)
        {
          double d1 = 0.0D;
          for (i7 = i3; i7 < this.m; i7++) {
            d1 += arrayOfDouble[i7][i3] * arrayOfDouble[i7][i4];
          }
          d1 = -d1 / arrayOfDouble[i3][i3];
          for (i7 = i3; i7 < this.m; i7++) {
            arrayOfDouble[i7][i4] += d1 * arrayOfDouble[i7][i3];
          }
        }
        arrayOfDouble1[i4] = arrayOfDouble[i3][i4];
      }
      if ((j & (i3 < i1 ? 1 : 0)) != 0) {
        for (i4 = i3; i4 < this.m; i4++) {
          this.U[i4][i3] = arrayOfDouble[i4][i3];
        }
      }
      if (i3 < i2)
      {
        arrayOfDouble1[i3] = 0.0D;
        for (i4 = i3 + 1; i4 < this.n; i4++) {
          arrayOfDouble1[i3] = Algebra.hypot(arrayOfDouble1[i3], arrayOfDouble1[i4]);
        }
        if (arrayOfDouble1[i3] != 0.0D)
        {
          if (arrayOfDouble1[(i3 + 1)] < 0.0D) {
            arrayOfDouble1[i3] = (-arrayOfDouble1[i3]);
          }
          for (i4 = i3 + 1; i4 < this.n; i4++) {
            arrayOfDouble1[i4] /= arrayOfDouble1[i3];
          }
          arrayOfDouble1[(i3 + 1)] += 1.0D;
        }
        arrayOfDouble1[i3] = (-arrayOfDouble1[i3]);
        if (((i3 + 1 < this.m ? 1 : 0) & (arrayOfDouble1[i3] != 0.0D ? 1 : 0)) != 0)
        {
          for (i4 = i3 + 1; i4 < this.m; i4++) {
            arrayOfDouble2[i4] = 0.0D;
          }
          for (i4 = i3 + 1; i4 < this.n; i4++) {
            for (int i5 = i3 + 1; i5 < this.m; i5++) {
              arrayOfDouble2[i5] += arrayOfDouble1[i4] * arrayOfDouble[i5][i4];
            }
          }
          for (i4 = i3 + 1; i4 < this.n; i4++)
          {
            double d2 = -arrayOfDouble1[i4] / arrayOfDouble1[(i3 + 1)];
            for (i7 = i3 + 1; i7 < this.m; i7++) {
              arrayOfDouble[i7][i4] += d2 * arrayOfDouble2[i7];
            }
          }
        }
        if (k != 0) {
          for (i4 = i3 + 1; i4 < this.n; i4++) {
            this.V[i4][i3] = arrayOfDouble1[i4];
          }
        }
      }
    }
    i3 = Math.min(this.n, this.m + 1);
    if (i1 < this.n) {
      this.s[i1] = arrayOfDouble[i1][i1];
    }
    if (this.m < i3) {
      this.s[(i3 - 1)] = 0.0D;
    }
    if (i2 + 1 < i3) {
      arrayOfDouble1[i2] = arrayOfDouble[i2][(i3 - 1)];
    }
    arrayOfDouble1[(i3 - 1)] = 0.0D;
    int i8;
    if (j != 0)
    {
      for (i4 = i1; i4 < i; i4++)
      {
        for (i6 = 0; i6 < this.m; i6++) {
          this.U[i6][i4] = 0.0D;
        }
        this.U[i4][i4] = 1.0D;
      }
      for (i4 = i1 - 1; i4 >= 0; i4--)
      {
        if (this.s[i4] != 0.0D)
        {
          for (i6 = i4 + 1; i6 < i; i6++)
          {
            d3 = 0.0D;
            for (i8 = i4; i8 < this.m; i8++) {
              d3 += this.U[i8][i4] * this.U[i8][i6];
            }
            d3 = -d3 / this.U[i4][i4];
            for (i8 = i4; i8 < this.m; i8++) {
              this.U[i8][i6] += d3 * this.U[i8][i4];
            }
          }
          for (i6 = i4; i6 < this.m; i6++) {
            this.U[i6][i4] = (-this.U[i6][i4]);
          }
          this.U[i4][i4] = (1.0D + this.U[i4][i4]);
          for (i6 = 0; i6 < i4 - 1; i6++) {
            this.U[i6][i4] = 0.0D;
          }
        }
        for (i6 = 0; i6 < this.m; i6++) {
          this.U[i6][i4] = 0.0D;
        }
        this.U[i4][i4] = 1.0D;
      }
    }
    if (k != 0) {
      for (i4 = this.n - 1; i4 >= 0; i4--)
      {
        if (((i4 < i2 ? 1 : 0) & (arrayOfDouble1[i4] != 0.0D ? 1 : 0)) != 0) {
          for (i6 = i4 + 1; i6 < i; i6++)
          {
            d3 = 0.0D;
            for (i8 = i4 + 1; i8 < this.n; i8++) {
              d3 += this.V[i8][i4] * this.V[i8][i6];
            }
            d3 = -d3 / this.V[(i4 + 1)][i4];
            for (i8 = i4 + 1; i8 < this.n; i8++) {
              this.V[i8][i6] += d3 * this.V[i8][i4];
            }
          }
        }
        for (i6 = 0; i6 < this.n; i6++) {
          this.V[i6][i4] = 0.0D;
        }
        this.V[i4][i4] = 1.0D;
      }
    }
    int i4 = i3 - 1;
    int i6 = 0;
    double d3 = Math.pow(2.0D, -52.0D);
    while (i3 > 0)
    {
      for (i8 = i3 - 2; (i8 >= -1) && (i8 != -1); i8--) {
        if (Math.abs(arrayOfDouble1[i8]) <= d3 * (Math.abs(this.s[i8]) + Math.abs(this.s[(i8 + 1)])))
        {
          arrayOfDouble1[i8] = 0.0D;
          break;
        }
      }
      int i9;
      if (i8 == i3 - 2)
      {
        i9 = 4;
      }
      else
      {
        for (int i10 = i3 - 1; (i10 >= i8) && (i10 != i8); i10--)
        {
          double d6 = (i10 != i3 ? Math.abs(arrayOfDouble1[i10]) : 0.0D) + (i10 != i8 + 1 ? Math.abs(arrayOfDouble1[(i10 - 1)]) : 0.0D);
          if (Math.abs(this.s[i10]) <= d3 * d6)
          {
            this.s[i10] = 0.0D;
            break;
          }
        }
        if (i10 == i8)
        {
          i9 = 3;
        }
        else if (i10 == i3 - 1)
        {
          i9 = 1;
        }
        else
        {
          i9 = 2;
          i8 = i10;
        }
      }
      i8++;
      double d4;
      int i12;
      switch (i9)
      {
      case 1: 
        d4 = arrayOfDouble1[(i3 - 2)];
        arrayOfDouble1[(i3 - 2)] = 0.0D;
        i12 = i3 - 2;
      case 2: 
      case 3: 
      case 4: 
        while (i12 >= i8)
        {
          double d8 = Algebra.hypot(this.s[i12], d4);
          double d10 = this.s[i12] / d8;
          double d12 = d4 / d8;
          this.s[i12] = d8;
          if (i12 != i8)
          {
            d4 = -d12 * arrayOfDouble1[(i12 - 1)];
            arrayOfDouble1[(i12 - 1)] = (d10 * arrayOfDouble1[(i12 - 1)]);
          }
          int i14;
          if (k != 0) {
            for (i14 = 0; i14 < this.n; i14++)
            {
              d8 = d10 * this.V[i14][i12] + d12 * this.V[i14][(i3 - 1)];
              this.V[i14][(i3 - 1)] = (-d12 * this.V[i14][i12] + d10 * this.V[i14][(i3 - 1)]);
              this.V[i14][i12] = d8;
            }
          }
          i12--;
          continue;
          d4 = arrayOfDouble1[(i8 - 1)];
          arrayOfDouble1[(i8 - 1)] = 0.0D;
          i12 = i8;
          while (i12 < i3)
          {
            d8 = Algebra.hypot(this.s[i12], d4);
            d10 = this.s[i12] / d8;
            d12 = d4 / d8;
            this.s[i12] = d8;
            d4 = -d12 * arrayOfDouble1[i12];
            arrayOfDouble1[i12] = (d10 * arrayOfDouble1[i12]);
            if (j != 0) {
              for (i14 = 0; i14 < this.m; i14++)
              {
                d8 = d10 * this.U[i14][i12] + d12 * this.U[i14][(i8 - 1)];
                this.U[i14][(i8 - 1)] = (-d12 * this.U[i14][i12] + d10 * this.U[i14][(i8 - 1)]);
                this.U[i14][i12] = d8;
              }
            }
            i12++;
            continue;
            d4 = Math.max(Math.max(Math.max(Math.max(Math.abs(this.s[(i3 - 1)]), Math.abs(this.s[(i3 - 2)])), Math.abs(arrayOfDouble1[(i3 - 2)])), Math.abs(this.s[i8])), Math.abs(arrayOfDouble1[i8]));
            double d7 = this.s[(i3 - 1)] / d4;
            double d9 = this.s[(i3 - 2)] / d4;
            double d11 = arrayOfDouble1[(i3 - 2)] / d4;
            double d13 = this.s[i8] / d4;
            double d14 = arrayOfDouble1[i8] / d4;
            double d15 = ((d9 + d7) * (d9 - d7) + d11 * d11) / 2.0D;
            double d16 = d7 * d11 * (d7 * d11);
            double d17 = 0.0D;
            if (((d15 != 0.0D ? 1 : 0) | (d16 != 0.0D ? 1 : 0)) != 0)
            {
              d17 = Math.sqrt(d15 * d15 + d16);
              if (d15 < 0.0D) {
                d17 = -d17;
              }
              d17 = d16 / (d15 + d17);
            }
            double d18 = (d13 + d7) * (d13 - d7) + d17;
            double d19 = d13 * d14;
            for (int i15 = i8; i15 < i3 - 1; i15++)
            {
              double d20 = Algebra.hypot(d18, d19);
              double d21 = d18 / d20;
              double d22 = d19 / d20;
              if (i15 != i8) {
                arrayOfDouble1[(i15 - 1)] = d20;
              }
              d18 = d21 * this.s[i15] + d22 * arrayOfDouble1[i15];
              arrayOfDouble1[i15] = (d21 * arrayOfDouble1[i15] - d22 * this.s[i15]);
              d19 = d22 * this.s[(i15 + 1)];
              this.s[(i15 + 1)] = (d21 * this.s[(i15 + 1)]);
              int i16;
              if (k != 0) {
                for (i16 = 0; i16 < this.n; i16++)
                {
                  d20 = d21 * this.V[i16][i15] + d22 * this.V[i16][(i15 + 1)];
                  this.V[i16][(i15 + 1)] = (-d22 * this.V[i16][i15] + d21 * this.V[i16][(i15 + 1)]);
                  this.V[i16][i15] = d20;
                }
              }
              d20 = Algebra.hypot(d18, d19);
              d21 = d18 / d20;
              d22 = d19 / d20;
              this.s[i15] = d20;
              d18 = d21 * arrayOfDouble1[i15] + d22 * this.s[(i15 + 1)];
              this.s[(i15 + 1)] = (-d22 * arrayOfDouble1[i15] + d21 * this.s[(i15 + 1)]);
              d19 = d22 * arrayOfDouble1[(i15 + 1)];
              arrayOfDouble1[(i15 + 1)] = (d21 * arrayOfDouble1[(i15 + 1)]);
              if ((j != 0) && (i15 < this.m - 1)) {
                for (i16 = 0; i16 < this.m; i16++)
                {
                  d20 = d21 * this.U[i16][i15] + d22 * this.U[i16][(i15 + 1)];
                  this.U[i16][(i15 + 1)] = (-d22 * this.U[i16][i15] + d21 * this.U[i16][(i15 + 1)]);
                  this.U[i16][i15] = d20;
                }
              }
            }
            arrayOfDouble1[(i3 - 2)] = d18;
            i6 += 1;
            break;
            if (this.s[i8] <= 0.0D)
            {
              this.s[i8] = (this.s[i8] < 0.0D ? -this.s[i8] : 0.0D);
              if (k != 0) {
                for (int i11 = 0; i11 <= i4; i11++) {
                  this.V[i11][i8] = (-this.V[i11][i8]);
                }
              }
            }
            while ((i8 < i4) && (this.s[i8] < this.s[(i8 + 1)]))
            {
              double d5 = this.s[i8];
              this.s[i8] = this.s[(i8 + 1)];
              this.s[(i8 + 1)] = d5;
              int i13;
              if ((k != 0) && (i8 < this.n - 1)) {
                for (i13 = 0; i13 < this.n; i13++)
                {
                  d5 = this.V[i13][(i8 + 1)];
                  this.V[i13][(i8 + 1)] = this.V[i13][i8];
                  this.V[i13][i8] = d5;
                }
              }
              if ((j != 0) && (i8 < this.m - 1)) {
                for (i13 = 0; i13 < this.m; i13++)
                {
                  d5 = this.U[i13][(i8 + 1)];
                  this.U[i13][(i8 + 1)] = this.U[i13][i8];
                  this.U[i13][i8] = d5;
                }
              }
              i8++;
            }
            i6 = 0;
            i3--;
          }
        }
      }
    }
  }
  
  public double cond()
  {
    return this.s[0] / this.s[(Math.min(this.m, this.n) - 1)];
  }
  
  public DoubleMatrix2D getS()
  {
    double[][] arrayOfDouble = new double[this.n][this.n];
    for (int i = 0; i < this.n; i++)
    {
      for (int j = 0; j < this.n; j++) {
        arrayOfDouble[i][j] = 0.0D;
      }
      arrayOfDouble[i][i] = this.s[i];
    }
    return DoubleFactory2D.dense.make(arrayOfDouble);
  }
  
  public double[] getSingularValues()
  {
    return this.s;
  }
  
  public DoubleMatrix2D getU()
  {
    return DoubleFactory2D.dense.make(this.U).viewPart(0, 0, this.m, Math.min(this.m + 1, this.n));
  }
  
  public DoubleMatrix2D getV()
  {
    return DoubleFactory2D.dense.make(this.V);
  }
  
  public double norm2()
  {
    return this.s[0];
  }
  
  public int rank()
  {
    double d1 = Math.pow(2.0D, -52.0D);
    double d2 = Math.max(this.m, this.n) * this.s[0] * d1;
    int i = 0;
    for (int j = 0; j < this.s.length; j++) {
      if (this.s[j] > d2) {
        i++;
      }
    }
    return i;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = "Illegal operation or error: ";
    localStringBuffer.append("---------------------------------------------------------------------\n");
    localStringBuffer.append("SingularValueDecomposition(A) --> cond(A), rank(A), norm2(A), U, S, V\n");
    localStringBuffer.append("---------------------------------------------------------------------\n");
    localStringBuffer.append("cond = ");
    try
    {
      localStringBuffer.append(String.valueOf(cond()));
    }
    catch (IllegalArgumentException localIllegalArgumentException1)
    {
      localStringBuffer.append(str + localIllegalArgumentException1.getMessage());
    }
    localStringBuffer.append("\nrank = ");
    try
    {
      localStringBuffer.append(String.valueOf(rank()));
    }
    catch (IllegalArgumentException localIllegalArgumentException2)
    {
      localStringBuffer.append(str + localIllegalArgumentException2.getMessage());
    }
    localStringBuffer.append("\nnorm2 = ");
    try
    {
      localStringBuffer.append(String.valueOf(norm2()));
    }
    catch (IllegalArgumentException localIllegalArgumentException3)
    {
      localStringBuffer.append(str + localIllegalArgumentException3.getMessage());
    }
    localStringBuffer.append("\n\nU = ");
    try
    {
      localStringBuffer.append(String.valueOf(getU()));
    }
    catch (IllegalArgumentException localIllegalArgumentException4)
    {
      localStringBuffer.append(str + localIllegalArgumentException4.getMessage());
    }
    localStringBuffer.append("\n\nS = ");
    try
    {
      localStringBuffer.append(String.valueOf(getS()));
    }
    catch (IllegalArgumentException localIllegalArgumentException5)
    {
      localStringBuffer.append(str + localIllegalArgumentException5.getMessage());
    }
    localStringBuffer.append("\n\nV = ");
    try
    {
      localStringBuffer.append(String.valueOf(getV()));
    }
    catch (IllegalArgumentException localIllegalArgumentException6)
    {
      localStringBuffer.append(str + localIllegalArgumentException6.getMessage());
    }
    return localStringBuffer.toString();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.linalg.SingularValueDecomposition
 * JD-Core Version:    0.7.0.1
 */