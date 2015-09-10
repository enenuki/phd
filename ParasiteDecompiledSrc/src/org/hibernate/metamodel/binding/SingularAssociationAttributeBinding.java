package org.hibernate.metamodel.binding;

public abstract interface SingularAssociationAttributeBinding
  extends SingularAttributeBinding, AssociationAttributeBinding
{
  public abstract boolean isPropertyReference();
  
  public abstract String getReferencedEntityName();
  
  public abstract void setReferencedEntityName(String paramString);
  
  public abstract String getReferencedAttributeName();
  
  public abstract void setReferencedAttributeName(String paramString);
  
  public abstract void resolveReference(AttributeBinding paramAttributeBinding);
  
  public abstract boolean isReferenceResolved();
  
  public abstract EntityBinding getReferencedEntityBinding();
  
  public abstract AttributeBinding getReferencedAttributeBinding();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.SingularAssociationAttributeBinding
 * JD-Core Version:    0.7.0.1
 */