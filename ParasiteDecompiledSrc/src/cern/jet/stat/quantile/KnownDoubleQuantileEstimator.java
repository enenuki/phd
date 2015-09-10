package cern.jet.stat.quantile;

import cern.colt.list.DoubleArrayList;
import cern.jet.math.Arithmetic;
import cern.jet.random.engine.RandomEngine;
import cern.jet.random.sampling.RandomSamplingAssistant;

class KnownDoubleQuantileEstimator
  extends DoubleQuantileEstimator
{
  protected double beta;
  protected boolean weHadMoreThanOneEmptyBuffer;
  protected RandomSamplingAssistant samplingAssistant;
  protected double samplingRate;
  protected long N;
  
  public KnownDoubleQuantileEstimator(int paramInt1, int paramInt2, long paramLong, double paramDouble, RandomEngine paramRandomEngine)
  {
    this.samplingRate = paramDouble;
    this.N = paramLong;
    if (this.samplingRate <= 1.0D) {
      this.samplingAssistant = null;
    } else {
      this.samplingAssistant = new RandomSamplingAssistant(Arithmetic.floor(paramLong / paramDouble), paramLong, paramRandomEngine);
    }
    setUp(paramInt1, paramInt2);
    clear();
  }
  
  protected void addInfinities(int paramInt, DoubleBuffer paramDoubleBuffer)
  {
    RandomSamplingAssistant localRandomSamplingAssistant = this.samplingAssistant;
    this.samplingAssistant = null;
    int i = 1;
    for (int j = 0; j < paramInt; j++)
    {
      if (i != 0) {
        paramDoubleBuffer.values.add(1.7976931348623157E+308D);
      } else {
        paramDoubleBuffer.values.add(-1.797693134862316E+308D);
      }
      i = i == 0 ? 1 : 0;
    }
    this.samplingAssistant = localRandomSamplingAssistant;
  }
  
  protected DoubleBuffer[] buffersToCollapse()
  {
    int i = this.bufferSet._getMinLevelOfFullOrPartialBuffers();
    return this.bufferSet._getFullOrPartialBuffersWithLevel(i);
  }
  
  public void clear()
  {
    super.clear();
    this.beta = 1.0D;
    this.weHadMoreThanOneEmptyBuffer = false;
    RandomSamplingAssistant localRandomSamplingAssistant = this.samplingAssistant;
    if (localRandomSamplingAssistant != null) {
      this.samplingAssistant = new RandomSamplingAssistant(Arithmetic.floor(this.N / this.samplingRate), this.N, localRandomSamplingAssistant.getRandomGenerator());
    }
  }
  
  public Object clone()
  {
    KnownDoubleQuantileEstimator localKnownDoubleQuantileEstimator = (KnownDoubleQuantileEstimator)super.clone();
    if (this.samplingAssistant != null) {
      localKnownDoubleQuantileEstimator.samplingAssistant = ((RandomSamplingAssistant)localKnownDoubleQuantileEstimator.samplingAssistant.clone());
    }
    return localKnownDoubleQuantileEstimator;
  }
  
  protected void newBuffer()
  {
    int i = this.bufferSet._getNumberOfEmptyBuffers();
    if (i == 0) {
      throw new RuntimeException("Oops, no empty buffer.");
    }
    this.currentBufferToFill = this.bufferSet._getFirstEmptyBuffer();
    if ((i == 1) && (!this.weHadMoreThanOneEmptyBuffer))
    {
      this.currentBufferToFill.level(this.bufferSet._getMinLevelOfFullOrPartialBuffers());
    }
    else
    {
      this.weHadMoreThanOneEmptyBuffer = true;
      this.currentBufferToFill.level(0);
    }
    this.currentBufferToFill.weight(1);
  }
  
  protected void postCollapse(DoubleBuffer[] paramArrayOfDoubleBuffer)
  {
    this.weHadMoreThanOneEmptyBuffer = false;
  }
  
  protected DoubleArrayList preProcessPhis(DoubleArrayList paramDoubleArrayList)
  {
    if (this.beta > 1.0D)
    {
      paramDoubleArrayList = paramDoubleArrayList.copy();
      int i = paramDoubleArrayList.size();
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        paramDoubleArrayList.set(i, (2.0D * paramDoubleArrayList.get(i) + this.beta - 1.0D) / (2.0D * this.beta));
      }
    }
    return paramDoubleArrayList;
  }
  
  public DoubleArrayList quantileElements(DoubleArrayList paramDoubleArrayList)
  {
    DoubleBuffer localDoubleBuffer = this.bufferSet._getPartialBuffer();
    int i = 0;
    if (localDoubleBuffer != null)
    {
      i = this.bufferSet.k() - localDoubleBuffer.size();
      if (i <= 0) {
        throw new RuntimeException("Oops! illegal missing values.");
      }
      addInfinities(i, localDoubleBuffer);
      this.beta = ((this.totalElementsFilled + i) / this.totalElementsFilled);
    }
    else
    {
      this.beta = 1.0D;
    }
    DoubleArrayList localDoubleArrayList = super.quantileElements(paramDoubleArrayList);
    if (localDoubleBuffer != null) {
      removeInfinitiesFrom(i, localDoubleBuffer);
    }
    return localDoubleArrayList;
  }
  
  protected void removeInfinitiesFrom(int paramInt, DoubleBuffer paramDoubleBuffer)
  {
    int i = 0;
    int j = 0;
    int k = 1;
    for (int m = 0; m < paramInt; m++)
    {
      if (k != 0) {
        i++;
      } else {
        j++;
      }
      k = k == 0 ? 1 : 0;
    }
    paramDoubleBuffer.values.removeFromTo(paramDoubleBuffer.size() - i, paramDoubleBuffer.size() - 1);
    paramDoubleBuffer.values.removeFromTo(0, j - 1);
  }
  
  protected boolean sampleNextElement()
  {
    if (this.samplingAssistant == null) {
      return true;
    }
    return this.samplingAssistant.sampleNextElement();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.stat.quantile.KnownDoubleQuantileEstimator
 * JD-Core Version:    0.7.0.1
 */