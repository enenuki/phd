package hep.aida.bin;

import cern.colt.list.DoubleArrayList;
import cern.jet.stat.Descriptive;

public class MightyStaticBin1D
  extends StaticBin1D
{
  protected boolean hasSumOfLogarithms = false;
  protected double sumOfLogarithms = 0.0D;
  protected boolean hasSumOfInversions = false;
  protected double sumOfInversions = 0.0D;
  protected double[] sumOfPowers = null;
  
  public MightyStaticBin1D()
  {
    this(false, false, 4);
  }
  
  public MightyStaticBin1D(boolean paramBoolean1, boolean paramBoolean2, int paramInt)
  {
    setMaxOrderForSumOfPowers(paramInt);
    this.hasSumOfLogarithms = paramBoolean1;
    this.hasSumOfInversions = paramBoolean2;
    clear();
  }
  
  public synchronized void addAllOfFromTo(DoubleArrayList paramDoubleArrayList, int paramInt1, int paramInt2)
  {
    super.addAllOfFromTo(paramDoubleArrayList, paramInt1, paramInt2);
    if (this.sumOfPowers != null) {
      Descriptive.incrementalUpdateSumsOfPowers(paramDoubleArrayList, paramInt1, paramInt2, 3, getMaxOrderForSumOfPowers(), this.sumOfPowers);
    }
    if (this.hasSumOfInversions) {
      this.sumOfInversions += Descriptive.sumOfInversions(paramDoubleArrayList, paramInt1, paramInt2);
    }
    if (this.hasSumOfLogarithms) {
      this.sumOfLogarithms += Descriptive.sumOfLogarithms(paramDoubleArrayList, paramInt1, paramInt2);
    }
  }
  
  protected void clearAllMeasures()
  {
    super.clearAllMeasures();
    this.sumOfLogarithms = 0.0D;
    this.sumOfInversions = 0.0D;
    if (this.sumOfPowers != null)
    {
      int i = this.sumOfPowers.length;
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        this.sumOfPowers[i] = 0.0D;
      }
    }
  }
  
  public synchronized Object clone()
  {
    MightyStaticBin1D localMightyStaticBin1D = (MightyStaticBin1D)super.clone();
    if (this.sumOfPowers != null) {
      localMightyStaticBin1D.sumOfPowers = ((double[])localMightyStaticBin1D.sumOfPowers.clone());
    }
    return localMightyStaticBin1D;
  }
  
  public String compareWith(AbstractBin1D paramAbstractBin1D)
  {
    StringBuffer localStringBuffer = new StringBuffer(super.compareWith(paramAbstractBin1D));
    if ((paramAbstractBin1D instanceof MightyStaticBin1D))
    {
      MightyStaticBin1D localMightyStaticBin1D = (MightyStaticBin1D)paramAbstractBin1D;
      if ((hasSumOfLogarithms()) && (localMightyStaticBin1D.hasSumOfLogarithms())) {
        localStringBuffer.append("geometric mean: " + relError(geometricMean(), localMightyStaticBin1D.geometricMean()) + " %\n");
      }
      if ((hasSumOfInversions()) && (localMightyStaticBin1D.hasSumOfInversions())) {
        localStringBuffer.append("harmonic mean: " + relError(harmonicMean(), localMightyStaticBin1D.harmonicMean()) + " %\n");
      }
      if ((hasSumOfPowers(3)) && (localMightyStaticBin1D.hasSumOfPowers(3))) {
        localStringBuffer.append("skew: " + relError(skew(), localMightyStaticBin1D.skew()) + " %\n");
      }
      if ((hasSumOfPowers(4)) && (localMightyStaticBin1D.hasSumOfPowers(4))) {
        localStringBuffer.append("kurtosis: " + relError(kurtosis(), localMightyStaticBin1D.kurtosis()) + " %\n");
      }
      localStringBuffer.append("\n");
    }
    return localStringBuffer.toString();
  }
  
  public synchronized double geometricMean()
  {
    return Descriptive.geometricMean(size(), sumOfLogarithms());
  }
  
  public synchronized int getMaxOrderForSumOfPowers()
  {
    if (this.sumOfPowers == null) {
      return 2;
    }
    return 2 + this.sumOfPowers.length;
  }
  
  public synchronized int getMinOrderForSumOfPowers()
  {
    int i = 0;
    if (hasSumOfInversions()) {
      i = -1;
    }
    return i;
  }
  
  public synchronized double harmonicMean()
  {
    return Descriptive.harmonicMean(size(), sumOfInversions());
  }
  
  public boolean hasSumOfInversions()
  {
    return this.hasSumOfInversions;
  }
  
  public boolean hasSumOfLogarithms()
  {
    return this.hasSumOfLogarithms;
  }
  
  public boolean hasSumOfPowers(int paramInt)
  {
    return (getMinOrderForSumOfPowers() <= paramInt) && (paramInt <= getMaxOrderForSumOfPowers());
  }
  
  public synchronized double kurtosis()
  {
    return Descriptive.kurtosis(moment(4, mean()), standardDeviation());
  }
  
  public synchronized double moment(int paramInt, double paramDouble)
  {
    if (paramInt < 0) {
      throw new IllegalArgumentException("k must be >= 0");
    }
    if (!hasSumOfPowers(paramInt)) {
      return (0.0D / 0.0D);
    }
    int i = Math.min(paramInt, getMaxOrderForSumOfPowers());
    DoubleArrayList localDoubleArrayList = new DoubleArrayList(i + 1);
    localDoubleArrayList.add(size());
    localDoubleArrayList.add(sum());
    localDoubleArrayList.add(sumOfSquares());
    for (int j = 3; j <= i; j++) {
      localDoubleArrayList.add(sumOfPowers(j));
    }
    return Descriptive.moment(paramInt, paramDouble, size(), localDoubleArrayList.elements());
  }
  
  public double product()
  {
    return Descriptive.product(size(), sumOfLogarithms());
  }
  
  protected void setMaxOrderForSumOfPowers(int paramInt)
  {
    if (paramInt <= 2) {
      this.sumOfPowers = null;
    } else {
      this.sumOfPowers = new double[paramInt - 2];
    }
  }
  
  public synchronized double skew()
  {
    return Descriptive.skew(moment(3, mean()), standardDeviation());
  }
  
  public double sumOfInversions()
  {
    if (!this.hasSumOfInversions) {
      return (0.0D / 0.0D);
    }
    return this.sumOfInversions;
  }
  
  public synchronized double sumOfLogarithms()
  {
    if (!this.hasSumOfLogarithms) {
      return (0.0D / 0.0D);
    }
    return this.sumOfLogarithms;
  }
  
  public synchronized double sumOfPowers(int paramInt)
  {
    if (!hasSumOfPowers(paramInt)) {
      return (0.0D / 0.0D);
    }
    if (paramInt == -1) {
      return sumOfInversions();
    }
    if (paramInt == 0) {
      return size();
    }
    if (paramInt == 1) {
      return sum();
    }
    if (paramInt == 2) {
      return sumOfSquares();
    }
    return this.sumOfPowers[(paramInt - 3)];
  }
  
  public synchronized String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer(super.toString());
    if (hasSumOfLogarithms())
    {
      localStringBuffer.append("Geometric mean: " + geometricMean());
      localStringBuffer.append("\nProduct: " + product() + "\n");
    }
    if (hasSumOfInversions())
    {
      localStringBuffer.append("Harmonic mean: " + harmonicMean());
      localStringBuffer.append("\nSum of inversions: " + sumOfInversions() + "\n");
    }
    int i = getMaxOrderForSumOfPowers();
    int j = Math.min(6, i);
    if (i > 2)
    {
      if (i >= 3) {
        localStringBuffer.append("Skew: " + skew() + "\n");
      }
      if (i >= 4) {
        localStringBuffer.append("Kurtosis: " + kurtosis() + "\n");
      }
      for (int k = 3; k <= j; k++) {
        localStringBuffer.append("Sum of powers(" + k + "): " + sumOfPowers(k) + "\n");
      }
      for (k = 0; k <= j; k++) {
        localStringBuffer.append("Moment(" + k + ",0): " + moment(k, 0.0D) + "\n");
      }
      for (k = 0; k <= j; k++) {
        localStringBuffer.append("Moment(" + k + ",mean()): " + moment(k, mean()) + "\n");
      }
    }
    return localStringBuffer.toString();
  }
  
  protected void xcheckOrder(int paramInt) {}
  
  protected boolean xequals(Object paramObject)
  {
    if (!(paramObject instanceof MightyStaticBin1D)) {
      return false;
    }
    MightyStaticBin1D localMightyStaticBin1D = (MightyStaticBin1D)paramObject;
    return (super.equals(localMightyStaticBin1D)) && (sumOfInversions() == localMightyStaticBin1D.sumOfInversions()) && (sumOfLogarithms() == localMightyStaticBin1D.sumOfLogarithms());
  }
  
  protected boolean xhasSumOfPowers(int paramInt1, int paramInt2)
  {
    if (paramInt1 > paramInt2) {
      throw new IllegalArgumentException("fromK must be less or equal to toK");
    }
    return (getMinOrderForSumOfPowers() <= paramInt1) && (paramInt2 <= getMaxOrderForSumOfPowers());
  }
  
  protected synchronized boolean xisLegalOrder(int paramInt)
  {
    return (getMinOrderForSumOfPowers() <= paramInt) && (paramInt <= getMaxOrderForSumOfPowers());
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hep.aida.bin.MightyStaticBin1D
 * JD-Core Version:    0.7.0.1
 */