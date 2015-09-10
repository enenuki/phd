package org.hibernate.usertype;

import java.util.Iterator;
import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.persister.collection.CollectionPersister;

public abstract interface UserCollectionType
{
  public abstract PersistentCollection instantiate(SessionImplementor paramSessionImplementor, CollectionPersister paramCollectionPersister)
    throws HibernateException;
  
  public abstract PersistentCollection wrap(SessionImplementor paramSessionImplementor, Object paramObject);
  
  public abstract Iterator getElementsIterator(Object paramObject);
  
  public abstract boolean contains(Object paramObject1, Object paramObject2);
  
  public abstract Object indexOf(Object paramObject1, Object paramObject2);
  
  public abstract Object replaceElements(Object paramObject1, Object paramObject2, CollectionPersister paramCollectionPersister, Object paramObject3, Map paramMap, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract Object instantiate(int paramInt);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.usertype.UserCollectionType
 * JD-Core Version:    0.7.0.1
 */