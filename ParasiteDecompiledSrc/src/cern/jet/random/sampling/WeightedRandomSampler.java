package cern.jet.random.sampling;

import cern.colt.PersistentObject;
import cern.colt.list.BooleanArrayList;
import cern.colt.list.IntArrayList;
import cern.jet.random.AbstractDistribution;
import cern.jet.random.Uniform;
import cern.jet.random.engine.RandomEngine;
import java.io.PrintStream;

public class WeightedRandomSampler
  extends PersistentObject
{
  protected int skip;
  protected int nextTriggerPos;
  protected int nextSkip;
  protected int weight;
  protected Uniform generator;
  static final int UNDEFINED = -1;
  
  public WeightedRandomSampler()
  {
    this(1, null);
  }
  
  public WeightedRandomSampler(int paramInt, RandomEngine paramRandomEngine)
  {
    if (paramRandomEngine == null) {
      paramRandomEngine = AbstractDistribution.makeDefaultGenerator();
    }
    this.generator = new Uniform(paramRandomEngine);
    setWeight(paramInt);
  }
  
  public Object clone()
  {
    WeightedRandomSampler localWeightedRandomSampler = (WeightedRandomSampler)super.clone();
    localWeightedRandomSampler.generator = ((Uniform)this.generator.clone());
    return localWeightedRandomSampler;
  }
  
  public int getWeight()
  {
    return this.weight;
  }
  
  public boolean sampleNextElement()
  {
    if (this.skip > 0)
    {
      this.skip -= 1;
      return false;
    }
    if (this.nextTriggerPos == -1)
    {
      if (this.weight == 1) {
        this.nextTriggerPos = 0;
      } else {
        this.nextTriggerPos = this.generator.nextIntFromTo(0, this.weight - 1);
      }
      this.nextSkip = (this.weight - 1 - this.nextTriggerPos);
    }
    if (this.nextTriggerPos > 0)
    {
      this.nextTriggerPos -= 1;
      return false;
    }
    this.nextTriggerPos = -1;
    this.skip = this.nextSkip;
    return true;
  }
  
  public void setWeight(int paramInt)
  {
    if (paramInt < 1) {
      throw new IllegalArgumentException("bad weight");
    }
    this.weight = paramInt;
    this.skip = 0;
    this.nextTriggerPos = -1;
    this.nextSkip = 0;
  }
  
  public static void test(int paramInt1, int paramInt2)
  {
    WeightedRandomSampler localWeightedRandomSampler = new WeightedRandomSampler();
    localWeightedRandomSampler.setWeight(paramInt1);
    IntArrayList localIntArrayList = new IntArrayList();
    for (int i = 0; i < paramInt2; i++) {
      if (localWeightedRandomSampler.sampleNextElement()) {
        localIntArrayList.add(i);
      }
    }
    System.out.println("Sample = " + localIntArrayList);
  }
  
  private void xsampleNextElements(BooleanArrayList paramBooleanArrayList)
  {
    int i = paramBooleanArrayList.size();
    boolean[] arrayOfBoolean = paramBooleanArrayList.elements();
    for (int j = 0; j < i; j++) {
      if (this.skip > 0)
      {
        this.skip -= 1;
        arrayOfBoolean[j] = false;
      }
      else
      {
        if (this.nextTriggerPos == -1)
        {
          if (this.weight == 1) {
            this.nextTriggerPos = 0;
          } else {
            this.nextTriggerPos = this.generator.nextIntFromTo(0, this.weight - 1);
          }
          this.nextSkip = (this.weight - 1 - this.nextTriggerPos);
        }
        if (this.nextTriggerPos > 0)
        {
          this.nextTriggerPos -= 1;
          arrayOfBoolean[j] = false;
        }
        else
        {
          this.nextTriggerPos = -1;
          this.skip = this.nextSkip;
          arrayOfBoolean[j] = true;
        }
      }
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.random.sampling.WeightedRandomSampler
 * JD-Core Version:    0.7.0.1
 */