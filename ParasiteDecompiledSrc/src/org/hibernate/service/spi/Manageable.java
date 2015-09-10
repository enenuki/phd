package org.hibernate.service.spi;

public abstract interface Manageable
{
  public abstract String getManagementDomain();
  
  public abstract String getManagementServiceType();
  
  public abstract Object getManagementBean();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.spi.Manageable
 * JD-Core Version:    0.7.0.1
 */