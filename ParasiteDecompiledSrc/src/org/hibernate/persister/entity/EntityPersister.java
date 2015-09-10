package org.hibernate.persister.entity;

import java.io.Serializable;
import java.util.Map;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.MappingException;
import org.hibernate.cache.spi.OptimisticCacheSource;
import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
import org.hibernate.cache.spi.entry.CacheEntryStructure;
import org.hibernate.engine.spi.CascadeStyle;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.ValueInclusion;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.tuple.entity.EntityMetamodel;
import org.hibernate.tuple.entity.EntityTuplizer;
import org.hibernate.type.Type;
import org.hibernate.type.VersionType;

public abstract interface EntityPersister
  extends OptimisticCacheSource
{
  public static final String ENTITY_ID = "id";
  
  public abstract void postInstantiate()
    throws MappingException;
  
  public abstract SessionFactoryImplementor getFactory();
  
  public abstract String getRootEntityName();
  
  public abstract String getEntityName();
  
  public abstract EntityMetamodel getEntityMetamodel();
  
  public abstract boolean isSubclassEntityName(String paramString);
  
  public abstract Serializable[] getPropertySpaces();
  
  public abstract Serializable[] getQuerySpaces();
  
  public abstract boolean hasProxy();
  
  public abstract boolean hasCollections();
  
  public abstract boolean hasMutableProperties();
  
  public abstract boolean hasSubselectLoadableCollections();
  
  public abstract boolean hasCascades();
  
  public abstract boolean isMutable();
  
  public abstract boolean isInherited();
  
  public abstract boolean isIdentifierAssignedByInsert();
  
  public abstract Type getPropertyType(String paramString)
    throws MappingException;
  
  public abstract int[] findDirty(Object[] paramArrayOfObject1, Object[] paramArrayOfObject2, Object paramObject, SessionImplementor paramSessionImplementor);
  
  public abstract int[] findModified(Object[] paramArrayOfObject1, Object[] paramArrayOfObject2, Object paramObject, SessionImplementor paramSessionImplementor);
  
  public abstract boolean hasIdentifierProperty();
  
  public abstract boolean canExtractIdOutOfEntity();
  
  public abstract boolean isVersioned();
  
  public abstract VersionType getVersionType();
  
  public abstract int getVersionProperty();
  
  public abstract boolean hasNaturalIdentifier();
  
  public abstract int[] getNaturalIdentifierProperties();
  
  public abstract Object[] getNaturalIdentifierSnapshot(Serializable paramSerializable, SessionImplementor paramSessionImplementor);
  
  public abstract IdentifierGenerator getIdentifierGenerator();
  
  public abstract boolean hasLazyProperties();
  
  public abstract Object load(Serializable paramSerializable, Object paramObject, LockMode paramLockMode, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract Object load(Serializable paramSerializable, Object paramObject, LockOptions paramLockOptions, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract void lock(Serializable paramSerializable, Object paramObject1, Object paramObject2, LockMode paramLockMode, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract void lock(Serializable paramSerializable, Object paramObject1, Object paramObject2, LockOptions paramLockOptions, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract void insert(Serializable paramSerializable, Object[] paramArrayOfObject, Object paramObject, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract Serializable insert(Object[] paramArrayOfObject, Object paramObject, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract void delete(Serializable paramSerializable, Object paramObject1, Object paramObject2, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract void update(Serializable paramSerializable, Object[] paramArrayOfObject1, int[] paramArrayOfInt, boolean paramBoolean, Object[] paramArrayOfObject2, Object paramObject1, Object paramObject2, Object paramObject3, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract Type[] getPropertyTypes();
  
  public abstract String[] getPropertyNames();
  
  public abstract boolean[] getPropertyInsertability();
  
  public abstract ValueInclusion[] getPropertyInsertGenerationInclusions();
  
  public abstract ValueInclusion[] getPropertyUpdateGenerationInclusions();
  
  public abstract boolean[] getPropertyUpdateability();
  
  public abstract boolean[] getPropertyCheckability();
  
  public abstract boolean[] getPropertyNullability();
  
  public abstract boolean[] getPropertyVersionability();
  
  public abstract boolean[] getPropertyLaziness();
  
  public abstract CascadeStyle[] getPropertyCascadeStyles();
  
  public abstract Type getIdentifierType();
  
  public abstract String getIdentifierPropertyName();
  
  public abstract boolean isCacheInvalidationRequired();
  
  public abstract boolean isLazyPropertiesCacheable();
  
  public abstract boolean hasCache();
  
  public abstract EntityRegionAccessStrategy getCacheAccessStrategy();
  
  public abstract CacheEntryStructure getCacheEntryStructure();
  
  public abstract ClassMetadata getClassMetadata();
  
  public abstract boolean isBatchLoadable();
  
  public abstract boolean isSelectBeforeUpdateRequired();
  
  public abstract Object[] getDatabaseSnapshot(Serializable paramSerializable, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract Object getCurrentVersion(Serializable paramSerializable, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract Object forceVersionIncrement(Serializable paramSerializable, Object paramObject, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract boolean isInstrumented();
  
  public abstract boolean hasInsertGeneratedProperties();
  
  public abstract boolean hasUpdateGeneratedProperties();
  
  public abstract boolean isVersionPropertyGenerated();
  
  public abstract void afterInitialize(Object paramObject, boolean paramBoolean, SessionImplementor paramSessionImplementor);
  
  public abstract void afterReassociate(Object paramObject, SessionImplementor paramSessionImplementor);
  
  public abstract Object createProxy(Serializable paramSerializable, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract Boolean isTransient(Object paramObject, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract Object[] getPropertyValuesToInsert(Object paramObject, Map paramMap, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract void processInsertGeneratedProperties(Serializable paramSerializable, Object paramObject, Object[] paramArrayOfObject, SessionImplementor paramSessionImplementor);
  
  public abstract void processUpdateGeneratedProperties(Serializable paramSerializable, Object paramObject, Object[] paramArrayOfObject, SessionImplementor paramSessionImplementor);
  
  public abstract Class getMappedClass();
  
  public abstract boolean implementsLifecycle();
  
  public abstract Class getConcreteProxyClass();
  
  public abstract void setPropertyValues(Object paramObject, Object[] paramArrayOfObject);
  
  public abstract void setPropertyValue(Object paramObject1, int paramInt, Object paramObject2);
  
  public abstract Object[] getPropertyValues(Object paramObject);
  
  public abstract Object getPropertyValue(Object paramObject, int paramInt)
    throws HibernateException;
  
  public abstract Object getPropertyValue(Object paramObject, String paramString);
  
  /**
   * @deprecated
   */
  public abstract Serializable getIdentifier(Object paramObject)
    throws HibernateException;
  
  public abstract Serializable getIdentifier(Object paramObject, SessionImplementor paramSessionImplementor);
  
  public abstract void setIdentifier(Object paramObject, Serializable paramSerializable, SessionImplementor paramSessionImplementor);
  
  public abstract Object getVersion(Object paramObject)
    throws HibernateException;
  
  public abstract Object instantiate(Serializable paramSerializable, SessionImplementor paramSessionImplementor);
  
  public abstract boolean isInstance(Object paramObject);
  
  public abstract boolean hasUninitializedLazyProperties(Object paramObject);
  
  public abstract void resetIdentifier(Object paramObject1, Serializable paramSerializable, Object paramObject2, SessionImplementor paramSessionImplementor);
  
  public abstract EntityPersister getSubclassEntityPersister(Object paramObject, SessionFactoryImplementor paramSessionFactoryImplementor);
  
  public abstract EntityMode getEntityMode();
  
  public abstract EntityTuplizer getEntityTuplizer();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.entity.EntityPersister
 * JD-Core Version:    0.7.0.1
 */