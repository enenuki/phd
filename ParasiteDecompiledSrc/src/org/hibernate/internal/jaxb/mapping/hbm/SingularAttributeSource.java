package org.hibernate.internal.jaxb.mapping.hbm;

public abstract interface SingularAttributeSource
  extends MetaAttributeContainer
{
  public abstract String getName();
  
  public abstract String getTypeAttribute();
  
  public abstract JaxbTypeElement getType();
  
  public abstract String getAccess();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.mapping.hbm.SingularAttributeSource
 * JD-Core Version:    0.7.0.1
 */