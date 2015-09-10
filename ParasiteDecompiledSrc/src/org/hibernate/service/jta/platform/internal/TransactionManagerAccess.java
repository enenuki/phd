package org.hibernate.service.jta.platform.internal;

import java.io.Serializable;
import javax.transaction.TransactionManager;

public abstract interface TransactionManagerAccess
  extends Serializable
{
  public abstract TransactionManager getTransactionManager();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jta.platform.internal.TransactionManagerAccess
 * JD-Core Version:    0.7.0.1
 */