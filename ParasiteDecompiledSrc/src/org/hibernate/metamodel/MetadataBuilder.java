package org.hibernate.metamodel;

import javax.persistence.SharedCacheMode;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cfg.NamingStrategy;

public abstract interface MetadataBuilder
{
  public abstract MetadataBuilder with(NamingStrategy paramNamingStrategy);
  
  public abstract MetadataBuilder with(MetadataSourceProcessingOrder paramMetadataSourceProcessingOrder);
  
  public abstract MetadataBuilder with(SharedCacheMode paramSharedCacheMode);
  
  public abstract MetadataBuilder with(AccessType paramAccessType);
  
  public abstract MetadataBuilder withNewIdentifierGeneratorsEnabled(boolean paramBoolean);
  
  public abstract Metadata buildMetadata();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.MetadataBuilder
 * JD-Core Version:    0.7.0.1
 */