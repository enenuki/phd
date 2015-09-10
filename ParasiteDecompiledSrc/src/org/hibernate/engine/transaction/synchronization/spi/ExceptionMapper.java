package org.hibernate.engine.transaction.synchronization.spi;

import java.io.Serializable;
import javax.transaction.SystemException;

public abstract interface ExceptionMapper
  extends Serializable
{
  public abstract RuntimeException mapStatusCheckFailure(String paramString, SystemException paramSystemException);
  
  public abstract RuntimeException mapManagedFlushFailure(String paramString, RuntimeException paramRuntimeException);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.transaction.synchronization.spi.ExceptionMapper
 * JD-Core Version:    0.7.0.1
 */