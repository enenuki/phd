package javax.persistence.spi;

import java.net.URL;
import java.util.List;
import java.util.Properties;
import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.sql.DataSource;

public abstract interface PersistenceUnitInfo
{
  public abstract String getPersistenceUnitName();
  
  public abstract String getPersistenceProviderClassName();
  
  public abstract PersistenceUnitTransactionType getTransactionType();
  
  public abstract DataSource getJtaDataSource();
  
  public abstract DataSource getNonJtaDataSource();
  
  public abstract List<String> getMappingFileNames();
  
  public abstract List<URL> getJarFileUrls();
  
  public abstract URL getPersistenceUnitRootUrl();
  
  public abstract List<String> getManagedClassNames();
  
  public abstract boolean excludeUnlistedClasses();
  
  public abstract SharedCacheMode getSharedCacheMode();
  
  public abstract ValidationMode getValidationMode();
  
  public abstract Properties getProperties();
  
  public abstract String getPersistenceXMLSchemaVersion();
  
  public abstract ClassLoader getClassLoader();
  
  public abstract void addTransformer(ClassTransformer paramClassTransformer);
  
  public abstract ClassLoader getNewTempClassLoader();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.spi.PersistenceUnitInfo
 * JD-Core Version:    0.7.0.1
 */