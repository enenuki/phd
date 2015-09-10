package org.hibernate.service.jta.platform.internal;

import java.io.Serializable;
import javax.transaction.TransactionSynchronizationRegistry;

public abstract interface SynchronizationRegistryAccess
  extends Serializable
{
  public abstract TransactionSynchronizationRegistry getSynchronizationRegistry();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jta.platform.internal.SynchronizationRegistryAccess
 * JD-Core Version:    0.7.0.1
 */