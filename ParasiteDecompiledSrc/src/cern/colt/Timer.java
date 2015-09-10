package cern.colt;

import java.io.PrintStream;

public class Timer
  extends PersistentObject
{
  private long baseTime;
  private long elapsedTime;
  private static final long UNIT = 1000L;
  
  public Timer()
  {
    reset();
  }
  
  public Timer display()
  {
    System.out.println(this);
    return this;
  }
  
  public float elapsedTime()
  {
    return seconds();
  }
  
  public long millis()
  {
    long l = this.elapsedTime;
    if (this.baseTime != 0L) {
      l += System.currentTimeMillis() - this.baseTime;
    }
    return l;
  }
  
  public Timer minus(Timer paramTimer)
  {
    Timer localTimer = new Timer();
    localTimer.elapsedTime = (millis() - paramTimer.millis());
    return localTimer;
  }
  
  public float minutes()
  {
    return seconds() / 60.0F;
  }
  
  public Timer plus(Timer paramTimer)
  {
    Timer localTimer = new Timer();
    localTimer.elapsedTime = (millis() + paramTimer.millis());
    return localTimer;
  }
  
  public Timer reset()
  {
    this.elapsedTime = 0L;
    this.baseTime = 0L;
    return this;
  }
  
  public float seconds()
  {
    return (float)millis() / 1000.0F;
  }
  
  public Timer start()
  {
    this.baseTime = System.currentTimeMillis();
    return this;
  }
  
  public Timer stop()
  {
    if (this.baseTime != 0L) {
      this.elapsedTime += System.currentTimeMillis() - this.baseTime;
    }
    this.baseTime = 0L;
    return this;
  }
  
  public static void test(int paramInt)
  {
    Timer localTimer = new Timer().start();
    int i = 0;
    for (int j = 0; j < paramInt; j++) {
      i++;
    }
    localTimer.stop();
    localTimer.display();
    System.out.println("I finished the test using " + localTimer);
    i = 0;
    for (j = 0; j < paramInt; j++) {
      i++;
    }
    localTimer.start();
    i = 0;
    for (j = 0; j < paramInt; j++) {
      i++;
    }
    localTimer.stop().display();
    localTimer.reset();
    localTimer.start();
    i = 0;
    for (j = 0; j < paramInt; j++) {
      i++;
    }
    localTimer.stop().display();
  }
  
  public String toString()
  {
    return "Time=" + Float.toString(elapsedTime()) + " secs";
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.Timer
 * JD-Core Version:    0.7.0.1
 */