package cern.jet.random.sampling;

import cern.colt.PersistentObject;
import cern.colt.Timer;
import cern.colt.list.BooleanArrayList;
import cern.colt.list.LongArrayList;
import cern.jet.random.engine.RandomEngine;
import java.io.PrintStream;

public class RandomSamplingAssistant
  extends PersistentObject
{
  protected RandomSampler sampler;
  protected long[] buffer;
  protected int bufferPosition;
  protected long skip;
  protected long n;
  static final int MAX_BUFFER_SIZE = 200;
  
  public RandomSamplingAssistant(long paramLong1, long paramLong2, RandomEngine paramRandomEngine)
  {
    this.n = paramLong1;
    this.sampler = new RandomSampler(paramLong1, paramLong2, 0L, paramRandomEngine);
    this.buffer = new long[(int)Math.min(paramLong1, 200L)];
    if (paramLong1 > 0L) {
      this.buffer[0] = -1L;
    }
    fetchNextBlock();
  }
  
  public Object clone()
  {
    RandomSamplingAssistant localRandomSamplingAssistant = (RandomSamplingAssistant)super.clone();
    localRandomSamplingAssistant.sampler = ((RandomSampler)this.sampler.clone());
    return localRandomSamplingAssistant;
  }
  
  protected void fetchNextBlock()
  {
    if (this.n > 0L)
    {
      long l = this.buffer[this.bufferPosition];
      this.sampler.nextBlock((int)Math.min(this.n, 200L), this.buffer, 0);
      this.skip = (this.buffer[0] - l - 1L);
      this.bufferPosition = 0;
    }
  }
  
  public RandomEngine getRandomGenerator()
  {
    return this.sampler.my_RandomGenerator;
  }
  
  public static void main(String[] paramArrayOfString)
  {
    long l1 = Long.parseLong(paramArrayOfString[0]);
    long l2 = Long.parseLong(paramArrayOfString[1]);
    testArraySampling((int)l1, (int)l2);
  }
  
  public static int[] sampleArray(int paramInt, int[] paramArrayOfInt)
  {
    RandomSamplingAssistant localRandomSamplingAssistant = new RandomSamplingAssistant(paramInt, paramArrayOfInt.length, null);
    int[] arrayOfInt = new int[paramInt];
    int i = 0;
    int j = paramArrayOfInt.length;
    for (int k = 0; k < j; k++) {
      if (localRandomSamplingAssistant.sampleNextElement()) {
        arrayOfInt[(i++)] = paramArrayOfInt[k];
      }
    }
    return arrayOfInt;
  }
  
  public boolean sampleNextElement()
  {
    if (this.n == 0L) {
      return false;
    }
    if (this.skip-- > 0L) {
      return false;
    }
    this.n -= 1L;
    if (this.bufferPosition < this.buffer.length - 1)
    {
      this.skip = (this.buffer[(this.bufferPosition + 1)] - this.buffer[(this.bufferPosition++)]);
      this.skip -= 1L;
    }
    else
    {
      fetchNextBlock();
    }
    return true;
  }
  
  public static void test(long paramLong1, long paramLong2)
  {
    RandomSamplingAssistant localRandomSamplingAssistant = new RandomSamplingAssistant(paramLong1, paramLong2, null);
    LongArrayList localLongArrayList = new LongArrayList((int)paramLong1);
    Timer localTimer = new Timer().start();
    for (long l = 0L; l < paramLong2; l += 1L) {
      if (localRandomSamplingAssistant.sampleNextElement()) {
        localLongArrayList.add(l);
      }
    }
    localTimer.stop().display();
    System.out.println("sample=" + localLongArrayList);
    System.out.println("Good bye.\n");
  }
  
  public static void testArraySampling(int paramInt1, int paramInt2)
  {
    int[] arrayOfInt1 = new int[paramInt2];
    for (int i = 0; i < paramInt2; i++) {
      arrayOfInt1[i] = i;
    }
    Timer localTimer = new Timer().start();
    int[] arrayOfInt2 = sampleArray(paramInt1, arrayOfInt1);
    localTimer.stop().display();
    System.out.println("Good bye.\n");
  }
  
  private void xsampleNextElements(BooleanArrayList paramBooleanArrayList)
  {
    int i = paramBooleanArrayList.size();
    boolean[] arrayOfBoolean = paramBooleanArrayList.elements();
    for (int j = 0; j < i; j++) {
      if (this.n == 0L)
      {
        arrayOfBoolean[j] = false;
      }
      else if (this.skip-- > 0L)
      {
        arrayOfBoolean[j] = false;
      }
      else
      {
        this.n -= 1L;
        if (this.bufferPosition < this.buffer.length - 1)
        {
          this.skip = (this.buffer[(this.bufferPosition + 1)] - this.buffer[(this.bufferPosition++)]);
          this.skip -= 1L;
        }
        else
        {
          fetchNextBlock();
        }
        arrayOfBoolean[j] = true;
      }
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.random.sampling.RandomSamplingAssistant
 * JD-Core Version:    0.7.0.1
 */