package org.hibernate.metamodel.binding;

import org.hibernate.metamodel.domain.AttributeContainer;
import org.hibernate.metamodel.domain.PluralAttribute;
import org.hibernate.metamodel.domain.SingularAttribute;
import org.hibernate.metamodel.source.MetaAttributeContext;

public abstract interface AttributeBindingContainer
{
  public abstract String getPathBase();
  
  public abstract AttributeContainer getAttributeContainer();
  
  public abstract Iterable<AttributeBinding> attributeBindings();
  
  public abstract AttributeBinding locateAttributeBinding(String paramString);
  
  public abstract BasicAttributeBinding makeBasicAttributeBinding(SingularAttribute paramSingularAttribute);
  
  public abstract ComponentAttributeBinding makeComponentAttributeBinding(SingularAttribute paramSingularAttribute);
  
  public abstract ManyToOneAttributeBinding makeManyToOneAttributeBinding(SingularAttribute paramSingularAttribute);
  
  public abstract BagBinding makeBagAttributeBinding(PluralAttribute paramPluralAttribute, CollectionElementNature paramCollectionElementNature);
  
  public abstract SetBinding makeSetAttributeBinding(PluralAttribute paramPluralAttribute, CollectionElementNature paramCollectionElementNature);
  
  public abstract EntityBinding seekEntityBinding();
  
  public abstract Class<?> getClassReference();
  
  public abstract MetaAttributeContext getMetaAttributeContext();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.AttributeBindingContainer
 * JD-Core Version:    0.7.0.1
 */