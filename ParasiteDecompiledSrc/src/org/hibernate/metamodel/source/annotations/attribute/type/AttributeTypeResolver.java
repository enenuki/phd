package org.hibernate.metamodel.source.annotations.attribute.type;

import java.util.Map;

public abstract interface AttributeTypeResolver
{
  public abstract String getExplicitHibernateTypeName();
  
  public abstract Map<String, String> getExplicitHibernateTypeParameters();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.attribute.type.AttributeTypeResolver
 * JD-Core Version:    0.7.0.1
 */