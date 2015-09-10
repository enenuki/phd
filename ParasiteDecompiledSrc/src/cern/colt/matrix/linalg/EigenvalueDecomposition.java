package cern.colt.matrix.linalg;

import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import java.io.Serializable;

public class EigenvalueDecomposition
  implements Serializable
{
  static final long serialVersionUID = 1020L;
  private int n;
  private boolean issymmetric;
  private double[] d;
  private double[] e;
  private double[][] V;
  private double[][] H;
  private double[] ort;
  private transient double cdivr;
  private transient double cdivi;
  
  public EigenvalueDecomposition(DoubleMatrix2D paramDoubleMatrix2D)
  {
    Property.DEFAULT.checkSquare(paramDoubleMatrix2D);
    this.n = paramDoubleMatrix2D.columns();
    this.V = new double[this.n][this.n];
    this.d = new double[this.n];
    this.e = new double[this.n];
    this.issymmetric = Property.DEFAULT.isSymmetric(paramDoubleMatrix2D);
    int i;
    int j;
    if (this.issymmetric)
    {
      for (i = 0; i < this.n; i++) {
        for (j = 0; j < this.n; j++) {
          this.V[i][j] = paramDoubleMatrix2D.getQuick(i, j);
        }
      }
      tred2();
      tql2();
    }
    else
    {
      this.H = new double[this.n][this.n];
      this.ort = new double[this.n];
      for (i = 0; i < this.n; i++) {
        for (j = 0; j < this.n; j++) {
          this.H[j][i] = paramDoubleMatrix2D.getQuick(j, i);
        }
      }
      orthes();
      hqr2();
    }
  }
  
  private void cdiv(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
  {
    double d1;
    double d2;
    if (Math.abs(paramDouble3) > Math.abs(paramDouble4))
    {
      d1 = paramDouble4 / paramDouble3;
      d2 = paramDouble3 + d1 * paramDouble4;
      this.cdivr = ((paramDouble1 + d1 * paramDouble2) / d2);
      this.cdivi = ((paramDouble2 - d1 * paramDouble1) / d2);
    }
    else
    {
      d1 = paramDouble3 / paramDouble4;
      d2 = paramDouble4 + d1 * paramDouble3;
      this.cdivr = ((d1 * paramDouble1 + paramDouble2) / d2);
      this.cdivi = ((d1 * paramDouble2 - paramDouble1) / d2);
    }
  }
  
  public DoubleMatrix2D getD()
  {
    double[][] arrayOfDouble = new double[this.n][this.n];
    for (int i = 0; i < this.n; i++)
    {
      for (int j = 0; j < this.n; j++) {
        arrayOfDouble[i][j] = 0.0D;
      }
      arrayOfDouble[i][i] = this.d[i];
      if (this.e[i] > 0.0D) {
        arrayOfDouble[i][(i + 1)] = this.e[i];
      } else if (this.e[i] < 0.0D) {
        arrayOfDouble[i][(i - 1)] = this.e[i];
      }
    }
    return DoubleFactory2D.dense.make(arrayOfDouble);
  }
  
  public DoubleMatrix1D getImagEigenvalues()
  {
    return DoubleFactory1D.dense.make(this.e);
  }
  
  public DoubleMatrix1D getRealEigenvalues()
  {
    return DoubleFactory1D.dense.make(this.d);
  }
  
  public DoubleMatrix2D getV()
  {
    return DoubleFactory2D.dense.make(this.V);
  }
  
  private void hqr2()
  {
    int i = this.n;
    int j = i - 1;
    double d1 = 0;
    int k = i - 1;
    double d2 = Math.pow(2.0D, -52.0D);
    double d3 = 0.0D;
    double d4 = 0.0D;
    double d5 = 0.0D;
    double d6 = 0.0D;
    double d7 = 0.0D;
    double d8 = 0.0D;
    double d13 = 0.0D;
    for (int m = 0; m < i; m++)
    {
      if (((m < d1 ? 1 : 0) | (m > k ? 1 : 0)) != 0)
      {
        this.d[m] = this.H[m][m];
        this.e[m] = 0.0D;
      }
      for (i1 = Math.max(m - 1, 0); i1 < i; i1++) {
        d13 += Math.abs(this.H[m][i1]);
      }
    }
    m = 0;
    double d10;
    double d11;
    int i2;
    double d12;
    int i3;
    label2267:
    while (j >= d1)
    {
      for (i1 = j; i1 > d1; i1--)
      {
        d7 = Math.abs(this.H[(i1 - 1)][(i1 - 1)]) + Math.abs(this.H[i1][i1]);
        if (d7 == 0.0D) {
          d7 = d13;
        }
        if (Math.abs(this.H[i1][(i1 - 1)]) < d2 * d7) {
          break;
        }
      }
      if (i1 == j)
      {
        this.H[j][j] += d3;
        this.d[j] = this.H[j][j];
        this.e[j] = 0.0D;
        j--;
        m = 0;
      }
      else if (i1 == j - 1)
      {
        d10 = this.H[j][(j - 1)] * this.H[(j - 1)][j];
        d4 = (this.H[(j - 1)][(j - 1)] - this.H[j][j]) / 2.0D;
        d5 = d4 * d4 + d10;
        d8 = Math.sqrt(Math.abs(d5));
        this.H[j][j] += d3;
        this.H[(j - 1)][(j - 1)] += d3;
        d11 = this.H[j][j];
        if (d5 >= 0.0D)
        {
          if (d4 >= 0.0D) {
            d8 = d4 + d8;
          } else {
            d8 = d4 - d8;
          }
          this.d[(j - 1)] = (d11 + d8);
          this.d[j] = this.d[(j - 1)];
          if (d8 != 0.0D) {
            this.d[j] = (d11 - d10 / d8);
          }
          this.e[(j - 1)] = 0.0D;
          this.e[j] = 0.0D;
          d11 = this.H[j][(j - 1)];
          d7 = Math.abs(d11) + Math.abs(d8);
          d4 = d11 / d7;
          d5 = d8 / d7;
          d6 = Math.sqrt(d4 * d4 + d5 * d5);
          d4 /= d6;
          d5 /= d6;
          for (i2 = j - 1; i2 < i; i2++)
          {
            d8 = this.H[(j - 1)][i2];
            this.H[(j - 1)][i2] = (d5 * d8 + d4 * this.H[j][i2]);
            this.H[j][i2] = (d5 * this.H[j][i2] - d4 * d8);
          }
          for (i2 = 0; i2 <= j; i2++)
          {
            d8 = this.H[i2][(j - 1)];
            this.H[i2][(j - 1)] = (d5 * d8 + d4 * this.H[i2][j]);
            this.H[i2][j] = (d5 * this.H[i2][j] - d4 * d8);
          }
          for (i2 = d1; i2 <= k; i2++)
          {
            d8 = this.V[i2][(j - 1)];
            this.V[i2][(j - 1)] = (d5 * d8 + d4 * this.V[i2][j]);
            this.V[i2][j] = (d5 * this.V[i2][j] - d4 * d8);
          }
        }
        this.d[(j - 1)] = (d11 + d4);
        this.d[j] = (d11 + d4);
        this.e[(j - 1)] = d8;
        this.e[j] = (-d8);
        j -= 2;
        m = 0;
      }
      else
      {
        d11 = this.H[j][j];
        d12 = 0.0D;
        d10 = 0.0D;
        if (i1 < j)
        {
          d12 = this.H[(j - 1)][(j - 1)];
          d10 = this.H[j][(j - 1)] * this.H[(j - 1)][j];
        }
        if (m == 10)
        {
          d3 += d11;
          for (i2 = d1; i2 <= j; i2++) {
            this.H[i2][i2] -= d11;
          }
          d7 = Math.abs(this.H[j][(j - 1)]) + Math.abs(this.H[(j - 1)][(j - 2)]);
          d11 = d12 = 0.75D * d7;
          d10 = -0.4375D * d7 * d7;
        }
        if (m == 30)
        {
          d7 = (d12 - d11) / 2.0D;
          d7 = d7 * d7 + d10;
          if (d7 > 0.0D)
          {
            d7 = Math.sqrt(d7);
            if (d12 < d11) {
              d7 = -d7;
            }
            d7 = d11 - d10 / ((d12 - d11) / 2.0D + d7);
            for (i2 = d1; i2 <= j; i2++) {
              this.H[i2][i2] -= d7;
            }
            d3 += d7;
            d11 = d12 = d10 = 0.964D;
          }
        }
        m += 1;
        for (i2 = j - 2; i2 >= i1; i2--)
        {
          d8 = this.H[i2][i2];
          d6 = d11 - d8;
          d7 = d12 - d8;
          d4 = (d6 * d7 - d10) / this.H[(i2 + 1)][i2] + this.H[i2][(i2 + 1)];
          d5 = this.H[(i2 + 1)][(i2 + 1)] - d8 - d6 - d7;
          d6 = this.H[(i2 + 2)][(i2 + 1)];
          d7 = Math.abs(d4) + Math.abs(d5) + Math.abs(d6);
          d4 /= d7;
          d5 /= d7;
          d6 /= d7;
          if ((i2 == i1) || (Math.abs(this.H[i2][(i2 - 1)]) * (Math.abs(d5) + Math.abs(d6)) < d2 * (Math.abs(d4) * (Math.abs(this.H[(i2 - 1)][(i2 - 1)]) + Math.abs(d8) + Math.abs(this.H[(i2 + 1)][(i2 + 1)]))))) {
            break;
          }
        }
        for (i3 = i2 + 2; i3 <= j; i3++)
        {
          this.H[i3][(i3 - 2)] = 0.0D;
          if (i3 > i2 + 2) {
            this.H[i3][(i3 - 3)] = 0.0D;
          }
        }
        for (i3 = i2;; i3++)
        {
          if (i3 > j - 1) {
            break label2267;
          }
          int i4 = i3 != j - 1 ? 1 : 0;
          if (i3 != i2)
          {
            d4 = this.H[i3][(i3 - 1)];
            d5 = this.H[(i3 + 1)][(i3 - 1)];
            d6 = i4 != 0 ? this.H[(i3 + 2)][(i3 - 1)] : 0.0D;
            d11 = Math.abs(d4) + Math.abs(d5) + Math.abs(d6);
            if (d11 != 0.0D)
            {
              d4 /= d11;
              d5 /= d11;
              d6 /= d11;
            }
          }
          if (d11 == 0.0D) {
            break;
          }
          d7 = Math.sqrt(d4 * d4 + d5 * d5 + d6 * d6);
          if (d4 < 0.0D) {
            d7 = -d7;
          }
          if (d7 != 0.0D)
          {
            if (i3 != i2) {
              this.H[i3][(i3 - 1)] = (-d7 * d11);
            } else if (i1 != i2) {
              this.H[i3][(i3 - 1)] = (-this.H[i3][(i3 - 1)]);
            }
            d4 += d7;
            d11 = d4 / d7;
            d12 = d5 / d7;
            d8 = d6 / d7;
            d5 /= d4;
            d6 /= d4;
            for (int i5 = i3; i5 < i; i5++)
            {
              d4 = this.H[i3][i5] + d5 * this.H[(i3 + 1)][i5];
              if (i4 != 0)
              {
                d4 += d6 * this.H[(i3 + 2)][i5];
                this.H[(i3 + 2)][i5] -= d4 * d8;
              }
              this.H[i3][i5] -= d4 * d11;
              this.H[(i3 + 1)][i5] -= d4 * d12;
            }
            for (i5 = 0; i5 <= Math.min(j, i3 + 3); i5++)
            {
              d4 = d11 * this.H[i5][i3] + d12 * this.H[i5][(i3 + 1)];
              if (i4 != 0)
              {
                d4 += d8 * this.H[i5][(i3 + 2)];
                this.H[i5][(i3 + 2)] -= d4 * d6;
              }
              this.H[i5][i3] -= d4;
              this.H[i5][(i3 + 1)] -= d4 * d5;
            }
            for (i5 = d1; i5 <= k; i5++)
            {
              d4 = d11 * this.V[i5][i3] + d12 * this.V[i5][(i3 + 1)];
              if (i4 != 0)
              {
                d4 += d8 * this.V[i5][(i3 + 2)];
                this.V[i5][(i3 + 2)] -= d4 * d6;
              }
              this.V[i5][i3] -= d4;
              this.V[i5][(i3 + 1)] -= d4 * d5;
            }
          }
        }
      }
    }
    if (d13 == 0.0D) {
      return;
    }
    double d14;
    for (j = i - 1; j >= 0; j--)
    {
      d4 = this.d[j];
      d5 = this.e[j];
      double d9;
      if (d5 == 0.0D)
      {
        i1 = j;
        this.H[j][j] = 1.0D;
        for (i2 = j - 1; i2 >= 0; i2--)
        {
          d10 = this.H[i2][i2] - d4;
          d6 = 0.0D;
          for (i3 = i1; i3 <= j; i3++) {
            d6 += this.H[i2][i3] * this.H[i3][j];
          }
          if (this.e[i2] < 0.0D)
          {
            d8 = d10;
            d7 = d6;
          }
          else
          {
            i1 = i2;
            if (this.e[i2] == 0.0D)
            {
              if (d10 != 0.0D) {
                this.H[i2][j] = (-d6 / d10);
              } else {
                this.H[i2][j] = (-d6 / (d2 * d13));
              }
            }
            else
            {
              d11 = this.H[i2][(i2 + 1)];
              d12 = this.H[(i2 + 1)][i2];
              d5 = (this.d[i2] - d4) * (this.d[i2] - d4) + this.e[i2] * this.e[i2];
              d9 = (d11 * d7 - d8 * d6) / d5;
              this.H[i2][j] = d9;
              if (Math.abs(d11) > Math.abs(d8)) {
                this.H[(i2 + 1)][j] = ((-d6 - d10 * d9) / d11);
              } else {
                this.H[(i2 + 1)][j] = ((-d7 - d12 * d9) / d8);
              }
            }
            d9 = Math.abs(this.H[i2][j]);
            if (d2 * d9 * d9 > 1.0D) {
              for (i3 = i2; i3 <= j; i3++) {
                this.H[i3][j] /= d9;
              }
            }
          }
        }
      }
      if (d5 < 0.0D)
      {
        i1 = j - 1;
        if (Math.abs(this.H[j][(j - 1)]) > Math.abs(this.H[(j - 1)][j]))
        {
          this.H[(j - 1)][(j - 1)] = (d5 / this.H[j][(j - 1)]);
          this.H[(j - 1)][j] = (-(this.H[j][j] - d4) / this.H[j][(j - 1)]);
        }
        else
        {
          cdiv(0.0D, -this.H[(j - 1)][j], this.H[(j - 1)][(j - 1)] - d4, d5);
          this.H[(j - 1)][(j - 1)] = this.cdivr;
          this.H[(j - 1)][j] = this.cdivi;
        }
        this.H[j][(j - 1)] = 0.0D;
        this.H[j][j] = 1.0D;
        for (i2 = j - 2; i2 >= 0; i2--)
        {
          d14 = 0.0D;
          double d15 = 0.0D;
          for (int i6 = i1; i6 <= j; i6++)
          {
            d14 += this.H[i2][i6] * this.H[i6][(j - 1)];
            d15 += this.H[i2][i6] * this.H[i6][j];
          }
          d10 = this.H[i2][i2] - d4;
          if (this.e[i2] < 0.0D)
          {
            d8 = d10;
            d6 = d14;
            d7 = d15;
          }
          else
          {
            i1 = i2;
            if (this.e[i2] == 0.0D)
            {
              cdiv(-d14, -d15, d10, d5);
              this.H[i2][(j - 1)] = this.cdivr;
              this.H[i2][j] = this.cdivi;
            }
            else
            {
              d11 = this.H[i2][(i2 + 1)];
              d12 = this.H[(i2 + 1)][i2];
              double d16 = (this.d[i2] - d4) * (this.d[i2] - d4) + this.e[i2] * this.e[i2] - d5 * d5;
              double d17 = (this.d[i2] - d4) * 2.0D * d5;
              if (((d16 == 0.0D ? 1 : 0) & (d17 == 0.0D ? 1 : 0)) != 0) {
                d16 = d2 * d13 * (Math.abs(d10) + Math.abs(d5) + Math.abs(d11) + Math.abs(d12) + Math.abs(d8));
              }
              cdiv(d11 * d6 - d8 * d14 + d5 * d15, d11 * d7 - d8 * d15 - d5 * d14, d16, d17);
              this.H[i2][(j - 1)] = this.cdivr;
              this.H[i2][j] = this.cdivi;
              if (Math.abs(d11) > Math.abs(d8) + Math.abs(d5))
              {
                this.H[(i2 + 1)][(j - 1)] = ((-d14 - d10 * this.H[i2][(j - 1)] + d5 * this.H[i2][j]) / d11);
                this.H[(i2 + 1)][j] = ((-d15 - d10 * this.H[i2][j] - d5 * this.H[i2][(j - 1)]) / d11);
              }
              else
              {
                cdiv(-d6 - d12 * this.H[i2][(j - 1)], -d7 - d12 * this.H[i2][j], d8, d5);
                this.H[(i2 + 1)][(j - 1)] = this.cdivr;
                this.H[(i2 + 1)][j] = this.cdivi;
              }
            }
            d9 = Math.max(Math.abs(this.H[i2][(j - 1)]), Math.abs(this.H[i2][j]));
            if (d2 * d9 * d9 > 1.0D) {
              for (i6 = i2; i6 <= j; i6++)
              {
                this.H[i6][(j - 1)] /= d9;
                this.H[i6][j] /= d9;
              }
            }
          }
        }
      }
    }
    for (int i1 = 0; i1 < i; i1++) {
      if (((i1 < d1 ? 1 : 0) | (i1 > k ? 1 : 0)) != 0) {
        for (i2 = i1; i2 < i; i2++) {
          this.V[i1][i2] = this.H[i1][i2];
        }
      }
    }
    for (i1 = i - 1; i1 >= d1; i1--) {
      for (i2 = d1; i2 <= k; i2++)
      {
        d8 = 0.0D;
        for (d14 = d1; d14 <= Math.min(i1, k); d14++) {
          d8 += this.V[i2][d14] * this.H[d14][i1];
        }
        this.V[i2][i1] = d8;
      }
    }
  }
  
  private void orthes()
  {
    int i = 0;
    int j = this.n - 1;
    for (int k = i + 1; k <= j - 1; k++)
    {
      double d1 = 0.0D;
      for (int i1 = k; i1 <= j; i1++) {
        d1 += Math.abs(this.H[i1][(k - 1)]);
      }
      if (d1 != 0.0D)
      {
        double d3 = 0.0D;
        for (int i3 = j; i3 >= k; i3--)
        {
          this.ort[i3] = (this.H[i3][(k - 1)] / d1);
          d3 += this.ort[i3] * this.ort[i3];
        }
        double d4 = Math.sqrt(d3);
        if (this.ort[k] > 0.0D) {
          d4 = -d4;
        }
        d3 -= this.ort[k] * d4;
        this.ort[k] -= d4;
        double d5;
        int i5;
        for (int i4 = k; i4 < this.n; i4++)
        {
          d5 = 0.0D;
          for (i5 = j; i5 >= k; i5--) {
            d5 += this.ort[i5] * this.H[i5][i4];
          }
          d5 /= d3;
          for (i5 = k; i5 <= j; i5++) {
            this.H[i5][i4] -= d5 * this.ort[i5];
          }
        }
        for (i4 = 0; i4 <= j; i4++)
        {
          d5 = 0.0D;
          for (i5 = j; i5 >= k; i5--) {
            d5 += this.ort[i5] * this.H[i4][i5];
          }
          d5 /= d3;
          for (i5 = k; i5 <= j; i5++) {
            this.H[i4][i5] -= d5 * this.ort[i5];
          }
        }
        this.ort[k] = (d1 * this.ort[k]);
        this.H[k][(k - 1)] = (d1 * d4);
      }
    }
    int m;
    for (k = 0; k < this.n; k++) {
      for (m = 0; m < this.n; m++) {
        this.V[k][m] = (k == m ? 1.0D : 0.0D);
      }
    }
    for (k = j - 1; k >= i + 1; k--) {
      if (this.H[k][(k - 1)] != 0.0D)
      {
        for (m = k + 1; m <= j; m++) {
          this.ort[m] = this.H[m][(k - 1)];
        }
        for (m = k; m <= j; m++)
        {
          double d2 = 0.0D;
          for (int i2 = k; i2 <= j; i2++) {
            d2 += this.ort[i2] * this.V[i2][m];
          }
          d2 = d2 / this.ort[k] / this.H[k][(k - 1)];
          for (i2 = k; i2 <= j; i2++) {
            this.V[i2][m] += d2 * this.ort[i2];
          }
        }
      }
    }
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = "Illegal operation or error: ";
    localStringBuffer.append("---------------------------------------------------------------------\n");
    localStringBuffer.append("EigenvalueDecomposition(A) --> D, V, realEigenvalues, imagEigenvalues\n");
    localStringBuffer.append("---------------------------------------------------------------------\n");
    localStringBuffer.append("realEigenvalues = ");
    try
    {
      localStringBuffer.append(String.valueOf(getRealEigenvalues()));
    }
    catch (IllegalArgumentException localIllegalArgumentException1)
    {
      localStringBuffer.append(str + localIllegalArgumentException1.getMessage());
    }
    localStringBuffer.append("\nimagEigenvalues = ");
    try
    {
      localStringBuffer.append(String.valueOf(getImagEigenvalues()));
    }
    catch (IllegalArgumentException localIllegalArgumentException2)
    {
      localStringBuffer.append(str + localIllegalArgumentException2.getMessage());
    }
    localStringBuffer.append("\n\nD = ");
    try
    {
      localStringBuffer.append(String.valueOf(getD()));
    }
    catch (IllegalArgumentException localIllegalArgumentException3)
    {
      localStringBuffer.append(str + localIllegalArgumentException3.getMessage());
    }
    localStringBuffer.append("\n\nV = ");
    try
    {
      localStringBuffer.append(String.valueOf(getV()));
    }
    catch (IllegalArgumentException localIllegalArgumentException4)
    {
      localStringBuffer.append(str + localIllegalArgumentException4.getMessage());
    }
    return localStringBuffer.toString();
  }
  
  private void tql2()
  {
    for (int i = 1; i < this.n; i++) {
      this.e[(i - 1)] = this.e[i];
    }
    this.e[(this.n - 1)] = 0.0D;
    double d1 = 0.0D;
    double d2 = 0.0D;
    double d3 = Math.pow(2.0D, -52.0D);
    int k;
    for (int j = 0; j < this.n; j++)
    {
      d2 = Math.max(d2, Math.abs(this.d[j]) + Math.abs(this.e[j]));
      for (k = j; (k < this.n) && (Math.abs(this.e[k]) > d3 * d2); k++) {}
      if (k > j)
      {
        int m = 0;
        do
        {
          m += 1;
          double d5 = this.d[j];
          double d6 = (this.d[(j + 1)] - d5) / (2.0D * this.e[j]);
          double d7 = Algebra.hypot(d6, 1.0D);
          if (d6 < 0.0D) {
            d7 = -d7;
          }
          this.d[j] = (this.e[j] / (d6 + d7));
          this.d[(j + 1)] = (this.e[j] * (d6 + d7));
          double d8 = this.d[(j + 1)];
          double d9 = d5 - this.d[j];
          for (int i2 = j + 2; i2 < this.n; i2++) {
            this.d[i2] -= d9;
          }
          d1 += d9;
          d6 = this.d[k];
          double d10 = 1.0D;
          double d11 = d10;
          double d12 = d10;
          double d13 = this.e[(j + 1)];
          double d14 = 0.0D;
          double d15 = 0.0D;
          for (int i3 = k - 1; i3 >= j; i3--)
          {
            d12 = d11;
            d11 = d10;
            d15 = d14;
            d5 = d10 * this.e[i3];
            d9 = d10 * d6;
            d7 = Algebra.hypot(d6, this.e[i3]);
            this.e[(i3 + 1)] = (d14 * d7);
            d14 = this.e[i3] / d7;
            d10 = d6 / d7;
            d6 = d10 * this.d[i3] - d14 * d5;
            this.d[(i3 + 1)] = (d9 + d14 * (d10 * d5 + d14 * this.d[i3]));
            for (int i4 = 0; i4 < this.n; i4++)
            {
              d9 = this.V[i4][(i3 + 1)];
              this.V[i4][(i3 + 1)] = (d14 * this.V[i4][i3] + d10 * d9);
              this.V[i4][i3] = (d10 * this.V[i4][i3] - d14 * d9);
            }
          }
          d6 = -d14 * d15 * d12 * d13 * this.e[j] / d8;
          this.e[j] = (d14 * d6);
          this.d[j] = (d10 * d6);
        } while (Math.abs(this.e[j]) > d3 * d2);
      }
      this.d[j] += d1;
      this.e[j] = 0.0D;
    }
    for (j = 0; j < this.n - 1; j++)
    {
      k = j;
      double d4 = this.d[j];
      for (int i1 = j + 1; i1 < this.n; i1++) {
        if (this.d[i1] < d4)
        {
          k = i1;
          d4 = this.d[i1];
        }
      }
      if (k != j)
      {
        this.d[k] = this.d[j];
        this.d[j] = d4;
        for (i1 = 0; i1 < this.n; i1++)
        {
          d4 = this.V[i1][j];
          this.V[i1][j] = this.V[i1][k];
          this.V[i1][k] = d4;
        }
      }
    }
  }
  
  private void tred2()
  {
    for (int i = 0; i < this.n; i++) {
      this.d[i] = this.V[(this.n - 1)][i];
    }
    double d1;
    for (i = this.n - 1; i > 0; i--)
    {
      d1 = 0.0D;
      double d2 = 0.0D;
      for (int k = 0; k < i; k++) {
        d1 += Math.abs(this.d[k]);
      }
      if (d1 == 0.0D)
      {
        this.e[i] = this.d[(i - 1)];
        for (k = 0; k < i; k++)
        {
          this.d[k] = this.V[(i - 1)][k];
          this.V[i][k] = 0.0D;
          this.V[k][i] = 0.0D;
        }
      }
      for (k = 0; k < i; k++)
      {
        this.d[k] /= d1;
        d2 += this.d[k] * this.d[k];
      }
      double d4 = this.d[(i - 1)];
      double d5 = Math.sqrt(d2);
      if (d4 > 0.0D) {
        d5 = -d5;
      }
      this.e[i] = (d1 * d5);
      d2 -= d4 * d5;
      this.d[(i - 1)] = (d4 - d5);
      for (int i1 = 0; i1 < i; i1++) {
        this.e[i1] = 0.0D;
      }
      for (i1 = 0; i1 < i; i1++)
      {
        d4 = this.d[i1];
        this.V[i1][i] = d4;
        d5 = this.e[i1] + this.V[i1][i1] * d4;
        for (int i2 = i1 + 1; i2 <= i - 1; i2++)
        {
          d5 += this.V[i2][i1] * this.d[i2];
          this.e[i2] += this.V[i2][i1] * d4;
        }
        this.e[i1] = d5;
      }
      d4 = 0.0D;
      for (i1 = 0; i1 < i; i1++)
      {
        this.e[i1] /= d2;
        d4 += this.e[i1] * this.d[i1];
      }
      double d6 = d4 / (d2 + d2);
      for (int i3 = 0; i3 < i; i3++) {
        this.e[i3] -= d6 * this.d[i3];
      }
      for (i3 = 0; i3 < i; i3++)
      {
        d4 = this.d[i3];
        d5 = this.e[i3];
        for (int i4 = i3; i4 <= i - 1; i4++) {
          this.V[i4][i3] -= d4 * this.e[i4] + d5 * this.d[i4];
        }
        this.d[i3] = this.V[(i - 1)][i3];
        this.V[i][i3] = 0.0D;
      }
      this.d[i] = d2;
    }
    for (i = 0; i < this.n - 1; i++)
    {
      this.V[(this.n - 1)][i] = this.V[i][i];
      this.V[i][i] = 1.0D;
      d1 = this.d[(i + 1)];
      if (d1 != 0.0D)
      {
        for (j = 0; j <= i; j++) {
          this.d[j] = (this.V[j][(i + 1)] / d1);
        }
        for (j = 0; j <= i; j++)
        {
          double d3 = 0.0D;
          for (int m = 0; m <= i; m++) {
            d3 += this.V[m][(i + 1)] * this.V[m][j];
          }
          for (m = 0; m <= i; m++) {
            this.V[m][j] -= d3 * this.d[m];
          }
        }
      }
      for (int j = 0; j <= i; j++) {
        this.V[j][(i + 1)] = 0.0D;
      }
    }
    for (i = 0; i < this.n; i++)
    {
      this.d[i] = this.V[(this.n - 1)][i];
      this.V[(this.n - 1)][i] = 0.0D;
    }
    this.V[(this.n - 1)][(this.n - 1)] = 1.0D;
    this.e[0] = 0.0D;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.linalg.EigenvalueDecomposition
 * JD-Core Version:    0.7.0.1
 */