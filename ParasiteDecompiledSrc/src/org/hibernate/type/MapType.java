/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.Collection;
/*  5:   */ import java.util.HashMap;
/*  6:   */ import java.util.Iterator;
/*  7:   */ import java.util.Map;
/*  8:   */ import java.util.Map.Entry;
/*  9:   */ import java.util.Set;
/* 10:   */ import org.hibernate.HibernateException;
/* 11:   */ import org.hibernate.collection.internal.PersistentMap;
/* 12:   */ import org.hibernate.collection.spi.PersistentCollection;
/* 13:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/* 14:   */ import org.hibernate.engine.spi.SessionImplementor;
/* 15:   */ import org.hibernate.persister.collection.CollectionPersister;
/* 16:   */ 
/* 17:   */ public class MapType
/* 18:   */   extends CollectionType
/* 19:   */ {
/* 20:   */   public MapType(TypeFactory.TypeScope typeScope, String role, String propertyRef, boolean isEmbeddedInXML)
/* 21:   */   {
/* 22:41 */     super(typeScope, role, propertyRef, isEmbeddedInXML);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public PersistentCollection instantiate(SessionImplementor session, CollectionPersister persister, Serializable key)
/* 26:   */   {
/* 27:45 */     return new PersistentMap(session);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Class getReturnedClass()
/* 31:   */   {
/* 32:49 */     return Map.class;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public Iterator getElementsIterator(Object collection)
/* 36:   */   {
/* 37:53 */     return ((Map)collection).values().iterator();
/* 38:   */   }
/* 39:   */   
/* 40:   */   public PersistentCollection wrap(SessionImplementor session, Object collection)
/* 41:   */   {
/* 42:57 */     return new PersistentMap(session, (Map)collection);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public Object instantiate(int anticipatedSize)
/* 46:   */   {
/* 47:61 */     return anticipatedSize <= 0 ? new HashMap() : new HashMap(anticipatedSize + (int)(anticipatedSize * 0.75F), 0.75F);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public Object replaceElements(Object original, Object target, Object owner, Map copyCache, SessionImplementor session)
/* 51:   */     throws HibernateException
/* 52:   */   {
/* 53:74 */     CollectionPersister cp = session.getFactory().getCollectionPersister(getRole());
/* 54:   */     
/* 55:76 */     Map result = (Map)target;
/* 56:77 */     result.clear();
/* 57:   */     
/* 58:79 */     Iterator iter = ((Map)original).entrySet().iterator();
/* 59:80 */     while (iter.hasNext())
/* 60:   */     {
/* 61:81 */       Map.Entry me = (Map.Entry)iter.next();
/* 62:82 */       Object key = cp.getIndexType().replace(me.getKey(), null, session, owner, copyCache);
/* 63:83 */       Object value = cp.getElementType().replace(me.getValue(), null, session, owner, copyCache);
/* 64:84 */       result.put(key, value);
/* 65:   */     }
/* 66:87 */     return result;
/* 67:   */   }
/* 68:   */   
/* 69:   */   public Object indexOf(Object collection, Object element)
/* 70:   */   {
/* 71:92 */     Iterator iter = ((Map)collection).entrySet().iterator();
/* 72:93 */     while (iter.hasNext())
/* 73:   */     {
/* 74:94 */       Map.Entry me = (Map.Entry)iter.next();
/* 75:96 */       if (me.getValue() == element) {
/* 76:96 */         return me.getKey();
/* 77:   */       }
/* 78:   */     }
/* 79:98 */     return null;
/* 80:   */   }
/* 81:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.MapType
 * JD-Core Version:    0.7.0.1
 */