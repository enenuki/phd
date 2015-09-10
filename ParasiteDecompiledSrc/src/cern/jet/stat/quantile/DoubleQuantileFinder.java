package cern.jet.stat.quantile;

import cern.colt.function.DoubleProcedure;
import cern.colt.list.DoubleArrayList;
import java.io.Serializable;

public abstract interface DoubleQuantileFinder
  extends Serializable
{
  public abstract void add(double paramDouble);
  
  public abstract void addAllOf(DoubleArrayList paramDoubleArrayList);
  
  public abstract void addAllOfFromTo(DoubleArrayList paramDoubleArrayList, int paramInt1, int paramInt2);
  
  public abstract void clear();
  
  public abstract Object clone();
  
  public abstract boolean forEach(DoubleProcedure paramDoubleProcedure);
  
  public abstract long memory();
  
  public abstract double phi(double paramDouble);
  
  public abstract DoubleArrayList quantileElements(DoubleArrayList paramDoubleArrayList);
  
  public abstract long size();
  
  public abstract long totalMemory();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.stat.quantile.DoubleQuantileFinder
 * JD-Core Version:    0.7.0.1
 */