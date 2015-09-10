package hr.nukic.parasite.accounts.templates;

import hr.nukic.parasite.core.DataCollector;
import hr.nukic.parasite.core.StockDaySummary;
import hr.nukic.parasite.core.StockHistoryData;
import java.util.List;

public abstract interface HistoryDataProvider
{
  public abstract List<StockHistoryData> getHistoryDataForTickers(List<String> paramList);
  
  public abstract List<StockDaySummary> readDaySummariesFromWeb(String paramString);
  
  public abstract void setDataCollector(DataCollector paramDataCollector);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.accounts.templates.HistoryDataProvider
 * JD-Core Version:    0.7.0.1
 */