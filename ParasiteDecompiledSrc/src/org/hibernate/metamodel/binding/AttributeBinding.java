package org.hibernate.metamodel.binding;

import java.util.Set;
import org.hibernate.metamodel.domain.Attribute;
import org.hibernate.metamodel.source.MetaAttributeContext;

public abstract interface AttributeBinding
{
  public abstract AttributeBindingContainer getContainer();
  
  public abstract Attribute getAttribute();
  
  public abstract HibernateTypeDescriptor getHibernateTypeDescriptor();
  
  public abstract boolean isAssociation();
  
  public abstract boolean isBasicPropertyAccessor();
  
  public abstract String getPropertyAccessorName();
  
  public abstract void setPropertyAccessorName(String paramString);
  
  public abstract boolean isIncludedInOptimisticLocking();
  
  public abstract void setIncludedInOptimisticLocking(boolean paramBoolean);
  
  public abstract MetaAttributeContext getMetaAttributeContext();
  
  public abstract boolean isAlternateUniqueKey();
  
  public abstract boolean isLazy();
  
  public abstract void addEntityReferencingAttributeBinding(SingularAssociationAttributeBinding paramSingularAssociationAttributeBinding);
  
  public abstract Set<SingularAssociationAttributeBinding> getEntityReferencingAttributeBindings();
  
  public abstract void validate();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.AttributeBinding
 * JD-Core Version:    0.7.0.1
 */