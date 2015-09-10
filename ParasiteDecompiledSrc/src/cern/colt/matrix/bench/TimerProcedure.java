package cern.colt.matrix.bench;

import cern.colt.Timer;

abstract interface TimerProcedure
{
  public abstract void apply(Timer paramTimer);
  
  public abstract void init();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.bench.TimerProcedure
 * JD-Core Version:    0.7.0.1
 */