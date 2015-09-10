package org.junit.runner.notification;

import org.junit.runner.Description;
import org.junit.runner.Result;

public class RunListener
{
  public void testRunStarted(Description description)
    throws Exception
  {}
  
  public void testRunFinished(Result result)
    throws Exception
  {}
  
  public void testStarted(Description description)
    throws Exception
  {}
  
  public void testFinished(Description description)
    throws Exception
  {}
  
  public void testFailure(Failure failure)
    throws Exception
  {}
  
  public void testAssumptionFailure(Failure failure) {}
  
  public void testIgnored(Description description)
    throws Exception
  {}
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.runner.notification.RunListener
 * JD-Core Version:    0.7.0.1
 */