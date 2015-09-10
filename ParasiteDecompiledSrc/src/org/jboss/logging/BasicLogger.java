package org.jboss.logging;

public abstract interface BasicLogger
{
  public abstract boolean isEnabled(Logger.Level paramLevel);
  
  public abstract boolean isTraceEnabled();
  
  public abstract void trace(Object paramObject);
  
  public abstract void trace(Object paramObject, Throwable paramThrowable);
  
  public abstract void trace(String paramString, Object paramObject, Throwable paramThrowable);
  
  public abstract void trace(String paramString, Object paramObject, Object[] paramArrayOfObject, Throwable paramThrowable);
  
  public abstract void tracev(String paramString, Object... paramVarArgs);
  
  public abstract void tracev(String paramString, Object paramObject);
  
  public abstract void tracev(String paramString, Object paramObject1, Object paramObject2);
  
  public abstract void tracev(String paramString, Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract void tracev(Throwable paramThrowable, String paramString, Object... paramVarArgs);
  
  public abstract void tracev(Throwable paramThrowable, String paramString, Object paramObject);
  
  public abstract void tracev(Throwable paramThrowable, String paramString, Object paramObject1, Object paramObject2);
  
  public abstract void tracev(Throwable paramThrowable, String paramString, Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract void tracef(String paramString, Object... paramVarArgs);
  
  public abstract void tracef(String paramString, Object paramObject);
  
  public abstract void tracef(String paramString, Object paramObject1, Object paramObject2);
  
  public abstract void tracef(String paramString, Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract void tracef(Throwable paramThrowable, String paramString, Object... paramVarArgs);
  
  public abstract void tracef(Throwable paramThrowable, String paramString, Object paramObject);
  
  public abstract void tracef(Throwable paramThrowable, String paramString, Object paramObject1, Object paramObject2);
  
  public abstract void tracef(Throwable paramThrowable, String paramString, Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract boolean isDebugEnabled();
  
  public abstract void debug(Object paramObject);
  
  public abstract void debug(Object paramObject, Throwable paramThrowable);
  
  public abstract void debug(String paramString, Object paramObject, Throwable paramThrowable);
  
  public abstract void debug(String paramString, Object paramObject, Object[] paramArrayOfObject, Throwable paramThrowable);
  
  public abstract void debugv(String paramString, Object... paramVarArgs);
  
  public abstract void debugv(String paramString, Object paramObject);
  
  public abstract void debugv(String paramString, Object paramObject1, Object paramObject2);
  
  public abstract void debugv(String paramString, Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract void debugv(Throwable paramThrowable, String paramString, Object... paramVarArgs);
  
  public abstract void debugv(Throwable paramThrowable, String paramString, Object paramObject);
  
  public abstract void debugv(Throwable paramThrowable, String paramString, Object paramObject1, Object paramObject2);
  
  public abstract void debugv(Throwable paramThrowable, String paramString, Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract void debugf(String paramString, Object... paramVarArgs);
  
  public abstract void debugf(String paramString, Object paramObject);
  
  public abstract void debugf(String paramString, Object paramObject1, Object paramObject2);
  
  public abstract void debugf(String paramString, Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract void debugf(Throwable paramThrowable, String paramString, Object... paramVarArgs);
  
  public abstract void debugf(Throwable paramThrowable, String paramString, Object paramObject);
  
  public abstract void debugf(Throwable paramThrowable, String paramString, Object paramObject1, Object paramObject2);
  
  public abstract void debugf(Throwable paramThrowable, String paramString, Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract boolean isInfoEnabled();
  
  public abstract void info(Object paramObject);
  
  public abstract void info(Object paramObject, Throwable paramThrowable);
  
  public abstract void info(String paramString, Object paramObject, Throwable paramThrowable);
  
  public abstract void info(String paramString, Object paramObject, Object[] paramArrayOfObject, Throwable paramThrowable);
  
  public abstract void infov(String paramString, Object... paramVarArgs);
  
  public abstract void infov(String paramString, Object paramObject);
  
  public abstract void infov(String paramString, Object paramObject1, Object paramObject2);
  
  public abstract void infov(String paramString, Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract void infov(Throwable paramThrowable, String paramString, Object... paramVarArgs);
  
  public abstract void infov(Throwable paramThrowable, String paramString, Object paramObject);
  
  public abstract void infov(Throwable paramThrowable, String paramString, Object paramObject1, Object paramObject2);
  
  public abstract void infov(Throwable paramThrowable, String paramString, Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract void infof(String paramString, Object... paramVarArgs);
  
  public abstract void infof(String paramString, Object paramObject);
  
  public abstract void infof(String paramString, Object paramObject1, Object paramObject2);
  
  public abstract void infof(String paramString, Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract void infof(Throwable paramThrowable, String paramString, Object... paramVarArgs);
  
  public abstract void infof(Throwable paramThrowable, String paramString, Object paramObject);
  
  public abstract void infof(Throwable paramThrowable, String paramString, Object paramObject1, Object paramObject2);
  
  public abstract void infof(Throwable paramThrowable, String paramString, Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract void warn(Object paramObject);
  
  public abstract void warn(Object paramObject, Throwable paramThrowable);
  
  public abstract void warn(String paramString, Object paramObject, Throwable paramThrowable);
  
  public abstract void warn(String paramString, Object paramObject, Object[] paramArrayOfObject, Throwable paramThrowable);
  
  public abstract void warnv(String paramString, Object... paramVarArgs);
  
  public abstract void warnv(String paramString, Object paramObject);
  
  public abstract void warnv(String paramString, Object paramObject1, Object paramObject2);
  
  public abstract void warnv(String paramString, Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract void warnv(Throwable paramThrowable, String paramString, Object... paramVarArgs);
  
  public abstract void warnv(Throwable paramThrowable, String paramString, Object paramObject);
  
  public abstract void warnv(Throwable paramThrowable, String paramString, Object paramObject1, Object paramObject2);
  
  public abstract void warnv(Throwable paramThrowable, String paramString, Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract void warnf(String paramString, Object... paramVarArgs);
  
  public abstract void warnf(String paramString, Object paramObject);
  
  public abstract void warnf(String paramString, Object paramObject1, Object paramObject2);
  
  public abstract void warnf(String paramString, Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract void warnf(Throwable paramThrowable, String paramString, Object... paramVarArgs);
  
  public abstract void warnf(Throwable paramThrowable, String paramString, Object paramObject);
  
  public abstract void warnf(Throwable paramThrowable, String paramString, Object paramObject1, Object paramObject2);
  
  public abstract void warnf(Throwable paramThrowable, String paramString, Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract void error(Object paramObject);
  
  public abstract void error(Object paramObject, Throwable paramThrowable);
  
  public abstract void error(String paramString, Object paramObject, Throwable paramThrowable);
  
  public abstract void error(String paramString, Object paramObject, Object[] paramArrayOfObject, Throwable paramThrowable);
  
  public abstract void errorv(String paramString, Object... paramVarArgs);
  
  public abstract void errorv(String paramString, Object paramObject);
  
  public abstract void errorv(String paramString, Object paramObject1, Object paramObject2);
  
  public abstract void errorv(String paramString, Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract void errorv(Throwable paramThrowable, String paramString, Object... paramVarArgs);
  
  public abstract void errorv(Throwable paramThrowable, String paramString, Object paramObject);
  
  public abstract void errorv(Throwable paramThrowable, String paramString, Object paramObject1, Object paramObject2);
  
  public abstract void errorv(Throwable paramThrowable, String paramString, Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract void errorf(String paramString, Object... paramVarArgs);
  
  public abstract void errorf(String paramString, Object paramObject);
  
  public abstract void errorf(String paramString, Object paramObject1, Object paramObject2);
  
  public abstract void errorf(String paramString, Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract void errorf(Throwable paramThrowable, String paramString, Object... paramVarArgs);
  
  public abstract void errorf(Throwable paramThrowable, String paramString, Object paramObject);
  
  public abstract void errorf(Throwable paramThrowable, String paramString, Object paramObject1, Object paramObject2);
  
  public abstract void errorf(Throwable paramThrowable, String paramString, Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract void fatal(Object paramObject);
  
  public abstract void fatal(Object paramObject, Throwable paramThrowable);
  
  public abstract void fatal(String paramString, Object paramObject, Throwable paramThrowable);
  
  public abstract void fatal(String paramString, Object paramObject, Object[] paramArrayOfObject, Throwable paramThrowable);
  
  public abstract void fatalv(String paramString, Object... paramVarArgs);
  
  public abstract void fatalv(String paramString, Object paramObject);
  
  public abstract void fatalv(String paramString, Object paramObject1, Object paramObject2);
  
  public abstract void fatalv(String paramString, Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract void fatalv(Throwable paramThrowable, String paramString, Object... paramVarArgs);
  
  public abstract void fatalv(Throwable paramThrowable, String paramString, Object paramObject);
  
  public abstract void fatalv(Throwable paramThrowable, String paramString, Object paramObject1, Object paramObject2);
  
  public abstract void fatalv(Throwable paramThrowable, String paramString, Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract void fatalf(String paramString, Object... paramVarArgs);
  
  public abstract void fatalf(String paramString, Object paramObject);
  
  public abstract void fatalf(String paramString, Object paramObject1, Object paramObject2);
  
  public abstract void fatalf(String paramString, Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract void fatalf(Throwable paramThrowable, String paramString, Object... paramVarArgs);
  
  public abstract void fatalf(Throwable paramThrowable, String paramString, Object paramObject);
  
  public abstract void fatalf(Throwable paramThrowable, String paramString, Object paramObject1, Object paramObject2);
  
  public abstract void fatalf(Throwable paramThrowable, String paramString, Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract void log(Logger.Level paramLevel, Object paramObject);
  
  public abstract void log(Logger.Level paramLevel, Object paramObject, Throwable paramThrowable);
  
  public abstract void log(Logger.Level paramLevel, String paramString, Object paramObject, Throwable paramThrowable);
  
  public abstract void log(String paramString, Logger.Level paramLevel, Object paramObject, Object[] paramArrayOfObject, Throwable paramThrowable);
  
  public abstract void logv(Logger.Level paramLevel, String paramString, Object... paramVarArgs);
  
  public abstract void logv(Logger.Level paramLevel, String paramString, Object paramObject);
  
  public abstract void logv(Logger.Level paramLevel, String paramString, Object paramObject1, Object paramObject2);
  
  public abstract void logv(Logger.Level paramLevel, String paramString, Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract void logv(Logger.Level paramLevel, Throwable paramThrowable, String paramString, Object... paramVarArgs);
  
  public abstract void logv(Logger.Level paramLevel, Throwable paramThrowable, String paramString, Object paramObject);
  
  public abstract void logv(Logger.Level paramLevel, Throwable paramThrowable, String paramString, Object paramObject1, Object paramObject2);
  
  public abstract void logv(Logger.Level paramLevel, Throwable paramThrowable, String paramString, Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract void logv(String paramString1, Logger.Level paramLevel, Throwable paramThrowable, String paramString2, Object... paramVarArgs);
  
  public abstract void logv(String paramString1, Logger.Level paramLevel, Throwable paramThrowable, String paramString2, Object paramObject);
  
  public abstract void logv(String paramString1, Logger.Level paramLevel, Throwable paramThrowable, String paramString2, Object paramObject1, Object paramObject2);
  
  public abstract void logv(String paramString1, Logger.Level paramLevel, Throwable paramThrowable, String paramString2, Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract void logf(Logger.Level paramLevel, String paramString, Object... paramVarArgs);
  
  public abstract void logf(Logger.Level paramLevel, String paramString, Object paramObject);
  
  public abstract void logf(Logger.Level paramLevel, String paramString, Object paramObject1, Object paramObject2);
  
  public abstract void logf(Logger.Level paramLevel, String paramString, Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract void logf(Logger.Level paramLevel, Throwable paramThrowable, String paramString, Object... paramVarArgs);
  
  public abstract void logf(Logger.Level paramLevel, Throwable paramThrowable, String paramString, Object paramObject);
  
  public abstract void logf(Logger.Level paramLevel, Throwable paramThrowable, String paramString, Object paramObject1, Object paramObject2);
  
  public abstract void logf(Logger.Level paramLevel, Throwable paramThrowable, String paramString, Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract void logf(String paramString1, Logger.Level paramLevel, Throwable paramThrowable, String paramString2, Object paramObject);
  
  public abstract void logf(String paramString1, Logger.Level paramLevel, Throwable paramThrowable, String paramString2, Object paramObject1, Object paramObject2);
  
  public abstract void logf(String paramString1, Logger.Level paramLevel, Throwable paramThrowable, String paramString2, Object paramObject1, Object paramObject2, Object paramObject3);
  
  public abstract void logf(String paramString1, Logger.Level paramLevel, Throwable paramThrowable, String paramString2, Object... paramVarArgs);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.logging.BasicLogger
 * JD-Core Version:    0.7.0.1
 */