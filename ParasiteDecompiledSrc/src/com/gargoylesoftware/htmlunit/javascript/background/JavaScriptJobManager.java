package com.gargoylesoftware.htmlunit.javascript.background;

import com.gargoylesoftware.htmlunit.Page;
import java.io.Serializable;

public abstract interface JavaScriptJobManager
  extends Serializable
{
  public abstract int getJobCount();
  
  public abstract int addJob(JavaScriptJob paramJavaScriptJob, Page paramPage);
  
  public abstract void removeJob(int paramInt);
  
  public abstract void removeAllJobs();
  
  public abstract void stopJob(int paramInt);
  
  public abstract int waitForJobs(long paramLong);
  
  public abstract int waitForJobsStartingBefore(long paramLong);
  
  public abstract void shutdown();
  
  public abstract JavaScriptJob getEarliestJob();
  
  public abstract boolean runSingleJob(JavaScriptJob paramJavaScriptJob);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.background.JavaScriptJobManager
 * JD-Core Version:    0.7.0.1
 */