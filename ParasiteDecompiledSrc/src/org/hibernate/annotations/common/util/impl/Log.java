package org.hibernate.annotations.common.util.impl;

import org.jboss.logging.BasicLogger;
import org.jboss.logging.Cause;
import org.jboss.logging.LogMessage;
import org.jboss.logging.Logger.Level;
import org.jboss.logging.Message;
import org.jboss.logging.MessageLogger;

@MessageLogger(projectCode="HCANN")
public abstract interface Log
  extends BasicLogger
{
  @LogMessage(level=Logger.Level.INFO)
  @Message(id=1, value="Hibernate Commons Annotations {%1$s}")
  public abstract void version(String paramString);
  
  @LogMessage(level=Logger.Level.ERROR)
  @Message(id=2, value="An assertion failure occurred (this may indicate a bug in Hibernate)")
  public abstract void assertionFailure(@Cause Throwable paramThrowable);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.util.impl.Log
 * JD-Core Version:    0.7.0.1
 */