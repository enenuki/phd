/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.Comparator;
/*  5:   */ import java.util.SortedMap;
/*  6:   */ import java.util.TreeMap;
/*  7:   */ import org.hibernate.collection.internal.PersistentSortedMap;
/*  8:   */ import org.hibernate.collection.spi.PersistentCollection;
/*  9:   */ import org.hibernate.engine.spi.SessionImplementor;
/* 10:   */ import org.hibernate.persister.collection.CollectionPersister;
/* 11:   */ 
/* 12:   */ public class SortedMapType
/* 13:   */   extends MapType
/* 14:   */ {
/* 15:   */   private final Comparator comparator;
/* 16:   */   
/* 17:   */   public SortedMapType(TypeFactory.TypeScope typeScope, String role, String propertyRef, Comparator comparator, boolean isEmbeddedInXML)
/* 18:   */   {
/* 19:41 */     super(typeScope, role, propertyRef, isEmbeddedInXML);
/* 20:42 */     this.comparator = comparator;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public PersistentCollection instantiate(SessionImplementor session, CollectionPersister persister, Serializable key)
/* 24:   */   {
/* 25:46 */     PersistentSortedMap map = new PersistentSortedMap(session);
/* 26:47 */     map.setComparator(this.comparator);
/* 27:48 */     return map;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Class getReturnedClass()
/* 31:   */   {
/* 32:52 */     return SortedMap.class;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public Object instantiate(int anticipatedSize)
/* 36:   */   {
/* 37:57 */     return new TreeMap(this.comparator);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public PersistentCollection wrap(SessionImplementor session, Object collection)
/* 41:   */   {
/* 42:61 */     return new PersistentSortedMap(session, (SortedMap)collection);
/* 43:   */   }
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.SortedMapType
 * JD-Core Version:    0.7.0.1
 */