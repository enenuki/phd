package org.hibernate.collection.spi;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.loader.CollectionAliases;
import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.type.Type;

public abstract interface PersistentCollection
{
  public abstract Object getOwner();
  
  public abstract void setOwner(Object paramObject);
  
  public abstract boolean empty();
  
  public abstract void setSnapshot(Serializable paramSerializable1, String paramString, Serializable paramSerializable2);
  
  public abstract void postAction();
  
  public abstract Object getValue();
  
  public abstract void beginRead();
  
  public abstract boolean endRead();
  
  public abstract boolean afterInitialize();
  
  public abstract boolean isDirectlyAccessible();
  
  public abstract boolean unsetSession(SessionImplementor paramSessionImplementor);
  
  public abstract boolean setCurrentSession(SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract void initializeFromCache(CollectionPersister paramCollectionPersister, Serializable paramSerializable, Object paramObject)
    throws HibernateException;
  
  public abstract Iterator entries(CollectionPersister paramCollectionPersister);
  
  public abstract Object readFrom(ResultSet paramResultSet, CollectionPersister paramCollectionPersister, CollectionAliases paramCollectionAliases, Object paramObject)
    throws HibernateException, SQLException;
  
  public abstract Object getIdentifier(Object paramObject, int paramInt);
  
  public abstract Object getIndex(Object paramObject, int paramInt, CollectionPersister paramCollectionPersister);
  
  public abstract Object getElement(Object paramObject);
  
  public abstract Object getSnapshotElement(Object paramObject, int paramInt);
  
  public abstract void beforeInitialize(CollectionPersister paramCollectionPersister, int paramInt);
  
  public abstract boolean equalsSnapshot(CollectionPersister paramCollectionPersister)
    throws HibernateException;
  
  public abstract boolean isSnapshotEmpty(Serializable paramSerializable);
  
  public abstract Serializable disassemble(CollectionPersister paramCollectionPersister)
    throws HibernateException;
  
  public abstract boolean needsRecreate(CollectionPersister paramCollectionPersister);
  
  public abstract Serializable getSnapshot(CollectionPersister paramCollectionPersister)
    throws HibernateException;
  
  public abstract void forceInitialization()
    throws HibernateException;
  
  public abstract boolean entryExists(Object paramObject, int paramInt);
  
  public abstract boolean needsInserting(Object paramObject, int paramInt, Type paramType)
    throws HibernateException;
  
  public abstract boolean needsUpdating(Object paramObject, int paramInt, Type paramType)
    throws HibernateException;
  
  public abstract boolean isRowUpdatePossible();
  
  public abstract Iterator getDeletes(CollectionPersister paramCollectionPersister, boolean paramBoolean)
    throws HibernateException;
  
  public abstract boolean isWrapper(Object paramObject);
  
  public abstract boolean wasInitialized();
  
  public abstract boolean hasQueuedOperations();
  
  public abstract Iterator queuedAdditionIterator();
  
  public abstract Collection getQueuedOrphans(String paramString);
  
  public abstract Serializable getKey();
  
  public abstract String getRole();
  
  public abstract boolean isUnreferenced();
  
  public abstract boolean isDirty();
  
  public abstract void clearDirty();
  
  public abstract Serializable getStoredSnapshot();
  
  public abstract void dirty();
  
  public abstract void preInsert(CollectionPersister paramCollectionPersister)
    throws HibernateException;
  
  public abstract void afterRowInsert(CollectionPersister paramCollectionPersister, Object paramObject, int paramInt)
    throws HibernateException;
  
  public abstract Collection getOrphans(Serializable paramSerializable, String paramString)
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.collection.spi.PersistentCollection
 * JD-Core Version:    0.7.0.1
 */