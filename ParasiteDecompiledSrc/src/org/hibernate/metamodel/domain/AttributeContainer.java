package org.hibernate.metamodel.domain;

import java.util.Set;

public abstract interface AttributeContainer
  extends Type
{
  public abstract String getRoleBaseName();
  
  public abstract Attribute locateAttribute(String paramString);
  
  public abstract Set<Attribute> attributes();
  
  public abstract SingularAttribute locateSingularAttribute(String paramString);
  
  public abstract SingularAttribute createSingularAttribute(String paramString);
  
  public abstract SingularAttribute createVirtualSingularAttribute(String paramString);
  
  public abstract SingularAttribute locateComponentAttribute(String paramString);
  
  public abstract SingularAttribute createComponentAttribute(String paramString, Component paramComponent);
  
  public abstract PluralAttribute locatePluralAttribute(String paramString);
  
  public abstract PluralAttribute locateBag(String paramString);
  
  public abstract PluralAttribute createBag(String paramString);
  
  public abstract PluralAttribute locateSet(String paramString);
  
  public abstract PluralAttribute createSet(String paramString);
  
  public abstract IndexedPluralAttribute locateList(String paramString);
  
  public abstract IndexedPluralAttribute createList(String paramString);
  
  public abstract IndexedPluralAttribute locateMap(String paramString);
  
  public abstract IndexedPluralAttribute createMap(String paramString);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.domain.AttributeContainer
 * JD-Core Version:    0.7.0.1
 */