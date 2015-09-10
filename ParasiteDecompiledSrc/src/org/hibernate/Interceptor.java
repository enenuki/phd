package org.hibernate;

import java.io.Serializable;
import java.util.Iterator;
import org.hibernate.type.Type;

public abstract interface Interceptor
{
  public abstract boolean onLoad(Object paramObject, Serializable paramSerializable, Object[] paramArrayOfObject, String[] paramArrayOfString, Type[] paramArrayOfType)
    throws CallbackException;
  
  public abstract boolean onFlushDirty(Object paramObject, Serializable paramSerializable, Object[] paramArrayOfObject1, Object[] paramArrayOfObject2, String[] paramArrayOfString, Type[] paramArrayOfType)
    throws CallbackException;
  
  public abstract boolean onSave(Object paramObject, Serializable paramSerializable, Object[] paramArrayOfObject, String[] paramArrayOfString, Type[] paramArrayOfType)
    throws CallbackException;
  
  public abstract void onDelete(Object paramObject, Serializable paramSerializable, Object[] paramArrayOfObject, String[] paramArrayOfString, Type[] paramArrayOfType)
    throws CallbackException;
  
  public abstract void onCollectionRecreate(Object paramObject, Serializable paramSerializable)
    throws CallbackException;
  
  public abstract void onCollectionRemove(Object paramObject, Serializable paramSerializable)
    throws CallbackException;
  
  public abstract void onCollectionUpdate(Object paramObject, Serializable paramSerializable)
    throws CallbackException;
  
  public abstract void preFlush(Iterator paramIterator)
    throws CallbackException;
  
  public abstract void postFlush(Iterator paramIterator)
    throws CallbackException;
  
  public abstract Boolean isTransient(Object paramObject);
  
  public abstract int[] findDirty(Object paramObject, Serializable paramSerializable, Object[] paramArrayOfObject1, Object[] paramArrayOfObject2, String[] paramArrayOfString, Type[] paramArrayOfType);
  
  public abstract Object instantiate(String paramString, EntityMode paramEntityMode, Serializable paramSerializable)
    throws CallbackException;
  
  public abstract String getEntityName(Object paramObject)
    throws CallbackException;
  
  public abstract Object getEntity(String paramString, Serializable paramSerializable)
    throws CallbackException;
  
  public abstract void afterTransactionBegin(Transaction paramTransaction);
  
  public abstract void beforeTransactionCompletion(Transaction paramTransaction);
  
  public abstract void afterTransactionCompletion(Transaction paramTransaction);
  
  public abstract String onPrepareStatement(String paramString);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.Interceptor
 * JD-Core Version:    0.7.0.1
 */