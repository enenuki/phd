package org.apache.http;

public abstract interface HttpConnectionMetrics
{
  public abstract long getRequestCount();
  
  public abstract long getResponseCount();
  
  public abstract long getSentBytesCount();
  
  public abstract long getReceivedBytesCount();
  
  public abstract Object getMetric(String paramString);
  
  public abstract void reset();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.HttpConnectionMetrics
 * JD-Core Version:    0.7.0.1
 */