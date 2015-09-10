package hr.nukic.parasite.accounts.templates;

import hr.nukic.parasite.core.DataCollector;
import hr.nukic.parasite.core.Order;
import hr.nukic.parasite.core.StockMarketData;
import hr.nukic.parasite.core.Transaction;
import java.util.List;

public abstract interface MarketMonitorAccount
  extends GenericWebAccount
{
  public abstract List<Order> readAskListFromWeb(String paramString);
  
  public abstract List<Order> readBidListFromWeb(String paramString);
  
  public abstract List<Transaction> readTransactionListFromWeb(String paramString);
  
  public abstract StockMarketData readMarketDataFromWeb(String paramString);
  
  public abstract void refreshMarketDataFromWeb(List<String> paramList);
  
  public abstract void setDataCollector(DataCollector paramDataCollector);
  
  public abstract void onMarketDataChange(StockMarketData paramStockMarketData);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.accounts.templates.MarketMonitorAccount
 * JD-Core Version:    0.7.0.1
 */