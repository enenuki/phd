package org.hibernate.internal.jaxb.mapping.hbm;

public abstract interface CustomSqlElement
{
  public abstract String getValue();
  
  public abstract boolean isCallable();
  
  public abstract JaxbCheckAttribute getCheck();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.CustomSqlElement
 * JD-Core Version:    0.7.0.1
 */