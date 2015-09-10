package hr.nukic.parasite.simulator;

import java.util.Date;

public abstract interface PriceChangeSubscriber
{
  public abstract void onPriceChange(float paramFloat, Date paramDate);
  
  public abstract long getSubscriberId();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.simulator.PriceChangeSubscriber
 * JD-Core Version:    0.7.0.1
 */