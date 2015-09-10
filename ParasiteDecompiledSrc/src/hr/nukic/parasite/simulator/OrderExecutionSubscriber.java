package hr.nukic.parasite.simulator;

import hr.nukic.parasite.core.Order;
import java.util.Date;

public abstract interface OrderExecutionSubscriber
{
  public abstract void onOrderExecution(Order paramOrder, Date paramDate);
  
  public abstract void onPartialOrderExecution(Order paramOrder, int paramInt, Date paramDate);
  
  public abstract long getSubscriberId();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.simulator.OrderExecutionSubscriber
 * JD-Core Version:    0.7.0.1
 */