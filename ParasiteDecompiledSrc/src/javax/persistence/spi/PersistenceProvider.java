package javax.persistence.spi;

import java.util.Map;
import javax.persistence.EntityManagerFactory;

public abstract interface PersistenceProvider
{
  public abstract EntityManagerFactory createEntityManagerFactory(String paramString, Map paramMap);
  
  public abstract EntityManagerFactory createContainerEntityManagerFactory(PersistenceUnitInfo paramPersistenceUnitInfo, Map paramMap);
  
  public abstract ProviderUtil getProviderUtil();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.spi.PersistenceProvider
 * JD-Core Version:    0.7.0.1
 */