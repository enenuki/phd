package org.hibernate.metamodel.source.hbm;

import java.util.List;
import org.hibernate.internal.jaxb.mapping.hbm.EntityElement;
import org.hibernate.internal.jaxb.mapping.hbm.JaxbFetchProfileElement;
import org.hibernate.metamodel.source.LocalBindingContext;
import org.hibernate.metamodel.source.MetaAttributeContext;

public abstract interface HbmBindingContext
  extends LocalBindingContext
{
  public abstract boolean isAutoImport();
  
  public abstract MetaAttributeContext getMetaAttributeContext();
  
  public abstract String determineEntityName(EntityElement paramEntityElement);
  
  public abstract void processFetchProfiles(List<JaxbFetchProfileElement> paramList, String paramString);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.hbm.HbmBindingContext
 * JD-Core Version:    0.7.0.1
 */