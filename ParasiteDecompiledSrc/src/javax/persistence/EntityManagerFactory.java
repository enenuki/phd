package javax.persistence;

import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;

public abstract interface EntityManagerFactory
{
  public abstract EntityManager createEntityManager();
  
  public abstract EntityManager createEntityManager(Map paramMap);
  
  public abstract CriteriaBuilder getCriteriaBuilder();
  
  public abstract Metamodel getMetamodel();
  
  public abstract boolean isOpen();
  
  public abstract void close();
  
  public abstract Map<String, Object> getProperties();
  
  public abstract Cache getCache();
  
  public abstract PersistenceUnitUtil getPersistenceUnitUtil();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.EntityManagerFactory
 * JD-Core Version:    0.7.0.1
 */