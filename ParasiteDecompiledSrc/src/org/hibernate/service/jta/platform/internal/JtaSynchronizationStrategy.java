package org.hibernate.service.jta.platform.internal;

import java.io.Serializable;
import javax.transaction.Synchronization;

public abstract interface JtaSynchronizationStrategy
  extends Serializable
{
  public abstract void registerSynchronization(Synchronization paramSynchronization);
  
  public abstract boolean canRegisterSynchronization();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jta.platform.internal.JtaSynchronizationStrategy
 * JD-Core Version:    0.7.0.1
 */