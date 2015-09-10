package org.hibernate.engine.spi;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.MappingException;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.engine.loading.internal.LoadContexts;
import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.persister.entity.EntityPersister;

public abstract interface PersistenceContext
{
  public abstract boolean isStateless();
  
  public abstract SessionImplementor getSession();
  
  public abstract LoadContexts getLoadContexts();
  
  public abstract void addUnownedCollection(CollectionKey paramCollectionKey, PersistentCollection paramPersistentCollection);
  
  public abstract PersistentCollection useUnownedCollection(CollectionKey paramCollectionKey);
  
  public abstract BatchFetchQueue getBatchFetchQueue();
  
  public abstract void clear();
  
  public abstract boolean hasNonReadOnlyEntities();
  
  public abstract void setEntryStatus(EntityEntry paramEntityEntry, Status paramStatus);
  
  public abstract void afterTransactionCompletion();
  
  public abstract Object[] getDatabaseSnapshot(Serializable paramSerializable, EntityPersister paramEntityPersister)
    throws HibernateException;
  
  public abstract Object[] getCachedDatabaseSnapshot(EntityKey paramEntityKey);
  
  public abstract Object[] getNaturalIdSnapshot(Serializable paramSerializable, EntityPersister paramEntityPersister)
    throws HibernateException;
  
  public abstract void addEntity(EntityKey paramEntityKey, Object paramObject);
  
  public abstract Object getEntity(EntityKey paramEntityKey);
  
  public abstract boolean containsEntity(EntityKey paramEntityKey);
  
  public abstract Object removeEntity(EntityKey paramEntityKey);
  
  public abstract Object getEntity(EntityUniqueKey paramEntityUniqueKey);
  
  public abstract void addEntity(EntityUniqueKey paramEntityUniqueKey, Object paramObject);
  
  public abstract EntityEntry getEntry(Object paramObject);
  
  public abstract EntityEntry removeEntry(Object paramObject);
  
  public abstract boolean isEntryFor(Object paramObject);
  
  public abstract CollectionEntry getCollectionEntry(PersistentCollection paramPersistentCollection);
  
  public abstract EntityEntry addEntity(Object paramObject1, Status paramStatus, Object[] paramArrayOfObject, EntityKey paramEntityKey, Object paramObject2, LockMode paramLockMode, boolean paramBoolean1, EntityPersister paramEntityPersister, boolean paramBoolean2, boolean paramBoolean3);
  
  public abstract EntityEntry addEntry(Object paramObject1, Status paramStatus, Object[] paramArrayOfObject, Object paramObject2, Serializable paramSerializable, Object paramObject3, LockMode paramLockMode, boolean paramBoolean1, EntityPersister paramEntityPersister, boolean paramBoolean2, boolean paramBoolean3);
  
  public abstract boolean containsCollection(PersistentCollection paramPersistentCollection);
  
  public abstract boolean containsProxy(Object paramObject);
  
  public abstract boolean reassociateIfUninitializedProxy(Object paramObject)
    throws MappingException;
  
  public abstract void reassociateProxy(Object paramObject, Serializable paramSerializable)
    throws MappingException;
  
  public abstract Object unproxy(Object paramObject)
    throws HibernateException;
  
  public abstract Object unproxyAndReassociate(Object paramObject)
    throws HibernateException;
  
  public abstract void checkUniqueness(EntityKey paramEntityKey, Object paramObject)
    throws HibernateException;
  
  public abstract Object narrowProxy(Object paramObject1, EntityPersister paramEntityPersister, EntityKey paramEntityKey, Object paramObject2)
    throws HibernateException;
  
  public abstract Object proxyFor(EntityPersister paramEntityPersister, EntityKey paramEntityKey, Object paramObject)
    throws HibernateException;
  
  public abstract Object proxyFor(Object paramObject)
    throws HibernateException;
  
  public abstract Object getCollectionOwner(Serializable paramSerializable, CollectionPersister paramCollectionPersister)
    throws MappingException;
  
  public abstract Object getLoadedCollectionOwnerOrNull(PersistentCollection paramPersistentCollection);
  
  public abstract Serializable getLoadedCollectionOwnerIdOrNull(PersistentCollection paramPersistentCollection);
  
  public abstract void addUninitializedCollection(CollectionPersister paramCollectionPersister, PersistentCollection paramPersistentCollection, Serializable paramSerializable);
  
  public abstract void addUninitializedDetachedCollection(CollectionPersister paramCollectionPersister, PersistentCollection paramPersistentCollection);
  
  public abstract void addNewCollection(CollectionPersister paramCollectionPersister, PersistentCollection paramPersistentCollection)
    throws HibernateException;
  
  public abstract void addInitializedDetachedCollection(CollectionPersister paramCollectionPersister, PersistentCollection paramPersistentCollection)
    throws HibernateException;
  
  public abstract CollectionEntry addInitializedCollection(CollectionPersister paramCollectionPersister, PersistentCollection paramPersistentCollection, Serializable paramSerializable)
    throws HibernateException;
  
  public abstract PersistentCollection getCollection(CollectionKey paramCollectionKey);
  
  public abstract void addNonLazyCollection(PersistentCollection paramPersistentCollection);
  
  public abstract void initializeNonLazyCollections()
    throws HibernateException;
  
  public abstract PersistentCollection getCollectionHolder(Object paramObject);
  
  public abstract void addCollectionHolder(PersistentCollection paramPersistentCollection);
  
  public abstract PersistentCollection removeCollectionHolder(Object paramObject);
  
  public abstract Serializable getSnapshot(PersistentCollection paramPersistentCollection);
  
  public abstract CollectionEntry getCollectionEntryOrNull(Object paramObject);
  
  public abstract Object getProxy(EntityKey paramEntityKey);
  
  public abstract void addProxy(EntityKey paramEntityKey, Object paramObject);
  
  public abstract Object removeProxy(EntityKey paramEntityKey);
  
  public abstract HashSet getNullifiableEntityKeys();
  
  public abstract Map getEntitiesByKey();
  
  public abstract Map getEntityEntries();
  
  public abstract Map getCollectionEntries();
  
  public abstract Map getCollectionsByKey();
  
  public abstract int getCascadeLevel();
  
  public abstract int incrementCascadeLevel();
  
  public abstract int decrementCascadeLevel();
  
  public abstract boolean isFlushing();
  
  public abstract void setFlushing(boolean paramBoolean);
  
  public abstract void beforeLoad();
  
  public abstract void afterLoad();
  
  public abstract boolean isLoadFinished();
  
  public abstract String toString();
  
  public abstract Serializable getOwnerId(String paramString1, String paramString2, Object paramObject, Map paramMap);
  
  public abstract Object getIndexInOwner(String paramString1, String paramString2, Object paramObject, Map paramMap);
  
  public abstract void addNullProperty(EntityKey paramEntityKey, String paramString);
  
  public abstract boolean isPropertyNull(EntityKey paramEntityKey, String paramString);
  
  public abstract boolean isDefaultReadOnly();
  
  public abstract void setDefaultReadOnly(boolean paramBoolean);
  
  public abstract boolean isReadOnly(Object paramObject);
  
  public abstract void setReadOnly(Object paramObject, boolean paramBoolean);
  
  public abstract void replaceDelayedEntityIdentityInsertKeys(EntityKey paramEntityKey, Serializable paramSerializable);
  
  public abstract void addChildParent(Object paramObject1, Object paramObject2);
  
  public abstract void removeChildParent(Object paramObject);
  
  public abstract void registerInsertedKey(EntityPersister paramEntityPersister, Serializable paramSerializable);
  
  public abstract boolean wasInsertedDuringTransaction(EntityPersister paramEntityPersister, Serializable paramSerializable);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.spi.PersistenceContext
 * JD-Core Version:    0.7.0.1
 */