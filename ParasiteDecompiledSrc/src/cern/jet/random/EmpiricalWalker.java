package cern.jet.random;

import cern.jet.random.engine.RandomEngine;

public class EmpiricalWalker
  extends AbstractDiscreteDistribution
{
  protected int K;
  protected int[] A;
  protected double[] F;
  protected double[] cdf;
  
  public EmpiricalWalker(double[] paramArrayOfDouble, int paramInt, RandomEngine paramRandomEngine)
  {
    setRandomGenerator(paramRandomEngine);
    setState(paramArrayOfDouble, paramInt);
    setState2(paramArrayOfDouble);
  }
  
  public double cdf(int paramInt)
  {
    if (paramInt < 0) {
      return 0.0D;
    }
    if (paramInt >= this.cdf.length - 1) {
      return 1.0D;
    }
    return this.cdf[paramInt];
  }
  
  public Object clone()
  {
    EmpiricalWalker localEmpiricalWalker = (EmpiricalWalker)super.clone();
    if (this.cdf != null) {
      localEmpiricalWalker.cdf = ((double[])this.cdf.clone());
    }
    if (this.A != null) {
      localEmpiricalWalker.A = ((int[])this.A.clone());
    }
    if (this.F != null) {
      localEmpiricalWalker.F = ((double[])this.F.clone());
    }
    return localEmpiricalWalker;
  }
  
  public int nextInt()
  {
    int i = 0;
    double d1 = this.randomGenerator.raw();
    d1 *= this.K;
    i = (int)d1;
    d1 -= i;
    double d2 = this.F[i];
    if (d2 == 1.0D) {
      return i;
    }
    if (d1 < d2) {
      return i;
    }
    return this.A[i];
  }
  
  public double pdf(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.cdf.length - 1)) {
      return 0.0D;
    }
    return this.cdf[(paramInt - 1)] - this.cdf[paramInt];
  }
  
  public void setState(double[] paramArrayOfDouble, int paramInt)
  {
    if ((paramArrayOfDouble == null) || (paramArrayOfDouble.length == 0)) {
      throw new IllegalArgumentException("Non-existing pdf");
    }
    int i = paramArrayOfDouble.length;
    this.cdf = new double[i + 1];
    this.cdf[0] = 0.0D;
    for (int j = 0; j < i; j++)
    {
      if (paramArrayOfDouble[j] < 0.0D) {
        throw new IllegalArgumentException("Negative probability");
      }
      this.cdf[(j + 1)] = (this.cdf[j] + paramArrayOfDouble[j]);
    }
    if (this.cdf[i] <= 0.0D) {
      throw new IllegalArgumentException("At leat one probability must be > 0.0");
    }
    for (j = 0; j < i + 1; j++) {
      this.cdf[j] /= this.cdf[i];
    }
  }
  
  public void setState2(double[] paramArrayOfDouble)
  {
    int i = paramArrayOfDouble.length;
    double d1 = 0.0D;
    for (int j = 0; j < i; j++) {
      d1 += paramArrayOfDouble[j];
    }
    this.K = i;
    this.F = new double[i];
    this.A = new int[i];
    double[] arrayOfDouble = new double[i];
    for (j = 0; j < i; j++) {
      paramArrayOfDouble[j] /= d1;
    }
    double d2 = 1.0D / i;
    int i1 = 0;
    int n = 0;
    for (j = 0; j < i; j++) {
      if (arrayOfDouble[j] < d2) {
        i1++;
      } else {
        n++;
      }
    }
    Stack localStack1 = new Stack(n);
    Stack localStack2 = new Stack(i1);
    for (j = 0; j < i; j++) {
      if (arrayOfDouble[j] < d2) {
        localStack2.push(j);
      } else {
        localStack1.push(j);
      }
    }
    int m;
    while (localStack2.size() > 0)
    {
      int k = localStack2.pop();
      if (localStack1.size() == 0)
      {
        this.A[k] = k;
        this.F[k] = 1.0D;
        break;
      }
      m = localStack1.pop();
      this.A[k] = m;
      this.F[k] = (i * arrayOfDouble[k]);
      double d3 = d2 - arrayOfDouble[k];
      arrayOfDouble[k] += d3;
      arrayOfDouble[m] -= d3;
      if (arrayOfDouble[m] < d2)
      {
        localStack2.push(m);
      }
      else if (arrayOfDouble[m] > d2)
      {
        localStack1.push(m);
      }
      else
      {
        this.A[m] = m;
        this.F[m] = 1.0D;
      }
    }
    while (localStack1.size() > 0)
    {
      m = localStack1.pop();
      this.A[m] = m;
      this.F[m] = 1.0D;
    }
  }
  
  public String toString()
  {
    Object localObject = null;
    return getClass().getName() + "(" + (this.cdf != null ? this.cdf.length : 0) + ")";
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.random.EmpiricalWalker
 * JD-Core Version:    0.7.0.1
 */