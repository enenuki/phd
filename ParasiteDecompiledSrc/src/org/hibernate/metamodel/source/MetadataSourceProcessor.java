package org.hibernate.metamodel.source;

import java.util.List;
import org.hibernate.metamodel.MetadataSources;

public abstract interface MetadataSourceProcessor
{
  public abstract void prepare(MetadataSources paramMetadataSources);
  
  public abstract void processIndependentMetadata(MetadataSources paramMetadataSources);
  
  public abstract void processTypeDependentMetadata(MetadataSources paramMetadataSources);
  
  public abstract void processMappingMetadata(MetadataSources paramMetadataSources, List<String> paramList);
  
  public abstract void processMappingDependentMetadata(MetadataSources paramMetadataSources);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.MetadataSourceProcessor
 * JD-Core Version:    0.7.0.1
 */