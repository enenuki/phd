package hep.aida.bin;

import cern.colt.buffer.DoubleBuffer;
import cern.colt.buffer.DoubleBufferConsumer;
import cern.colt.list.DoubleArrayList;
import cern.jet.stat.Descriptive;

public abstract class AbstractBin1D
  extends AbstractBin
  implements DoubleBufferConsumer
{
  public abstract void add(double paramDouble);
  
  public final synchronized void addAllOf(DoubleArrayList paramDoubleArrayList)
  {
    addAllOfFromTo(paramDoubleArrayList, 0, paramDoubleArrayList.size() - 1);
  }
  
  public synchronized void addAllOfFromTo(DoubleArrayList paramDoubleArrayList, int paramInt1, int paramInt2)
  {
    for (int i = paramInt1; i <= paramInt2; i++) {
      add(paramDoubleArrayList.getQuick(i));
    }
  }
  
  public synchronized DoubleBuffer buffered(int paramInt)
  {
    return new DoubleBuffer(this, paramInt);
  }
  
  public String compareWith(AbstractBin1D paramAbstractBin1D)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("\nDifferences [percent]");
    localStringBuffer.append("\nSize: " + relError(size(), paramAbstractBin1D.size()) + " %");
    localStringBuffer.append("\nSum: " + relError(sum(), paramAbstractBin1D.sum()) + " %");
    localStringBuffer.append("\nSumOfSquares: " + relError(sumOfSquares(), paramAbstractBin1D.sumOfSquares()) + " %");
    localStringBuffer.append("\nMin: " + relError(min(), paramAbstractBin1D.min()) + " %");
    localStringBuffer.append("\nMax: " + relError(max(), paramAbstractBin1D.max()) + " %");
    localStringBuffer.append("\nMean: " + relError(mean(), paramAbstractBin1D.mean()) + " %");
    localStringBuffer.append("\nRMS: " + relError(rms(), paramAbstractBin1D.rms()) + " %");
    localStringBuffer.append("\nVariance: " + relError(variance(), paramAbstractBin1D.variance()) + " %");
    localStringBuffer.append("\nStandard deviation: " + relError(standardDeviation(), paramAbstractBin1D.standardDeviation()) + " %");
    localStringBuffer.append("\nStandard error: " + relError(standardError(), paramAbstractBin1D.standardError()) + " %");
    localStringBuffer.append("\n");
    return localStringBuffer.toString();
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof AbstractBin1D)) {
      return false;
    }
    AbstractBin1D localAbstractBin1D = (AbstractBin1D)paramObject;
    return (size() == localAbstractBin1D.size()) && (min() == localAbstractBin1D.min()) && (max() == localAbstractBin1D.max()) && (sum() == localAbstractBin1D.sum()) && (sumOfSquares() == localAbstractBin1D.sumOfSquares());
  }
  
  public abstract double max();
  
  public synchronized double mean()
  {
    return sum() / size();
  }
  
  public abstract double min();
  
  protected double relError(double paramDouble1, double paramDouble2)
  {
    return 100.0D * (1.0D - paramDouble1 / paramDouble2);
  }
  
  public synchronized double rms()
  {
    return Descriptive.rms(size(), sumOfSquares());
  }
  
  public synchronized double standardDeviation()
  {
    return Math.sqrt(variance());
  }
  
  public synchronized double standardError()
  {
    return Descriptive.standardError(size(), variance());
  }
  
  public abstract double sum();
  
  public abstract double sumOfSquares();
  
  public synchronized String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(getClass().getName());
    localStringBuffer.append("\n-------------");
    localStringBuffer.append("\nSize: " + size());
    localStringBuffer.append("\nSum: " + sum());
    localStringBuffer.append("\nSumOfSquares: " + sumOfSquares());
    localStringBuffer.append("\nMin: " + min());
    localStringBuffer.append("\nMax: " + max());
    localStringBuffer.append("\nMean: " + mean());
    localStringBuffer.append("\nRMS: " + rms());
    localStringBuffer.append("\nVariance: " + variance());
    localStringBuffer.append("\nStandard deviation: " + standardDeviation());
    localStringBuffer.append("\nStandard error: " + standardError());
    localStringBuffer.append("\n");
    return localStringBuffer.toString();
  }
  
  public synchronized void trimToSize() {}
  
  public synchronized double variance()
  {
    return Descriptive.sampleVariance(size(), sum(), sumOfSquares());
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hep.aida.bin.AbstractBin1D
 * JD-Core Version:    0.7.0.1
 */