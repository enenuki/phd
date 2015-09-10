package hr.nukic.parasite.simulator;

import hr.nukic.parasite.core.DataCollector;
import hr.nukic.parasite.core.StockMarketData;

public abstract interface MarketDataSubscriber
{
  public abstract void onMarketDataChange(StockMarketData paramStockMarketData);
  
  public abstract void setDataCollector(DataCollector paramDataCollector);
  
  public abstract long getSubscriberId();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.simulator.MarketDataSubscriber
 * JD-Core Version:    0.7.0.1
 */