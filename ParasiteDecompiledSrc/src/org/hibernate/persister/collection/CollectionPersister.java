package org.hibernate.persister.collection;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
import org.hibernate.cache.spi.entry.CacheEntryStructure;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.type.CollectionType;
import org.hibernate.type.Type;

public abstract interface CollectionPersister
{
  public abstract void initialize(Serializable paramSerializable, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract boolean hasCache();
  
  public abstract CollectionRegionAccessStrategy getCacheAccessStrategy();
  
  public abstract CacheEntryStructure getCacheEntryStructure();
  
  public abstract CollectionType getCollectionType();
  
  public abstract Type getKeyType();
  
  public abstract Type getIndexType();
  
  public abstract Type getElementType();
  
  public abstract Class getElementClass();
  
  public abstract Object readKey(ResultSet paramResultSet, String[] paramArrayOfString, SessionImplementor paramSessionImplementor)
    throws HibernateException, SQLException;
  
  public abstract Object readElement(ResultSet paramResultSet, Object paramObject, String[] paramArrayOfString, SessionImplementor paramSessionImplementor)
    throws HibernateException, SQLException;
  
  public abstract Object readIndex(ResultSet paramResultSet, String[] paramArrayOfString, SessionImplementor paramSessionImplementor)
    throws HibernateException, SQLException;
  
  public abstract Object readIdentifier(ResultSet paramResultSet, String paramString, SessionImplementor paramSessionImplementor)
    throws HibernateException, SQLException;
  
  public abstract boolean isPrimitiveArray();
  
  public abstract boolean isArray();
  
  public abstract boolean isOneToMany();
  
  public abstract boolean isManyToMany();
  
  public abstract String getManyToManyFilterFragment(String paramString, Map paramMap);
  
  public abstract boolean hasIndex();
  
  public abstract boolean isLazy();
  
  public abstract boolean isInverse();
  
  public abstract void remove(Serializable paramSerializable, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract void recreate(PersistentCollection paramPersistentCollection, Serializable paramSerializable, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract void deleteRows(PersistentCollection paramPersistentCollection, Serializable paramSerializable, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract void updateRows(PersistentCollection paramPersistentCollection, Serializable paramSerializable, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract void insertRows(PersistentCollection paramPersistentCollection, Serializable paramSerializable, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract String getRole();
  
  public abstract EntityPersister getOwnerEntityPersister();
  
  public abstract IdentifierGenerator getIdentifierGenerator();
  
  public abstract Type getIdentifierType();
  
  public abstract boolean hasOrphanDelete();
  
  public abstract boolean hasOrdering();
  
  public abstract boolean hasManyToManyOrdering();
  
  public abstract Serializable[] getCollectionSpaces();
  
  public abstract CollectionMetadata getCollectionMetadata();
  
  public abstract boolean isCascadeDeleteEnabled();
  
  public abstract boolean isVersioned();
  
  public abstract boolean isMutable();
  
  public abstract String getNodeName();
  
  public abstract String getElementNodeName();
  
  public abstract String getIndexNodeName();
  
  public abstract void postInstantiate()
    throws MappingException;
  
  public abstract SessionFactoryImplementor getFactory();
  
  public abstract boolean isAffectedByEnabledFilters(SessionImplementor paramSessionImplementor);
  
  public abstract String[] getKeyColumnAliases(String paramString);
  
  public abstract String[] getIndexColumnAliases(String paramString);
  
  public abstract String[] getElementColumnAliases(String paramString);
  
  public abstract String getIdentifierColumnAlias(String paramString);
  
  public abstract boolean isExtraLazy();
  
  public abstract int getSize(Serializable paramSerializable, SessionImplementor paramSessionImplementor);
  
  public abstract boolean indexExists(Serializable paramSerializable, Object paramObject, SessionImplementor paramSessionImplementor);
  
  public abstract boolean elementExists(Serializable paramSerializable, Object paramObject, SessionImplementor paramSessionImplementor);
  
  public abstract Object getElementByIndex(Serializable paramSerializable, Object paramObject1, SessionImplementor paramSessionImplementor, Object paramObject2);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.collection.CollectionPersister
 * JD-Core Version:    0.7.0.1
 */