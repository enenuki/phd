package org.hibernate.engine.transaction.spi;

public enum LocalStatus
{
  NOT_ACTIVE,  ACTIVE,  COMMITTED,  ROLLED_BACK,  FAILED_COMMIT;
  
  private LocalStatus() {}
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.transaction.spi.LocalStatus
 * JD-Core Version:    0.7.0.1
 */