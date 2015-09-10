/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.Comparator;
/*  5:   */ import java.util.SortedSet;
/*  6:   */ import java.util.TreeSet;
/*  7:   */ import org.hibernate.collection.internal.PersistentSortedSet;
/*  8:   */ import org.hibernate.collection.spi.PersistentCollection;
/*  9:   */ import org.hibernate.engine.spi.SessionImplementor;
/* 10:   */ import org.hibernate.persister.collection.CollectionPersister;
/* 11:   */ 
/* 12:   */ public class SortedSetType
/* 13:   */   extends SetType
/* 14:   */ {
/* 15:   */   private final Comparator comparator;
/* 16:   */   
/* 17:   */   public SortedSetType(TypeFactory.TypeScope typeScope, String role, String propertyRef, Comparator comparator, boolean isEmbeddedInXML)
/* 18:   */   {
/* 19:39 */     super(typeScope, role, propertyRef, isEmbeddedInXML);
/* 20:40 */     this.comparator = comparator;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public PersistentCollection instantiate(SessionImplementor session, CollectionPersister persister, Serializable key)
/* 24:   */   {
/* 25:44 */     PersistentSortedSet set = new PersistentSortedSet(session);
/* 26:45 */     set.setComparator(this.comparator);
/* 27:46 */     return set;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Class getReturnedClass()
/* 31:   */   {
/* 32:50 */     return SortedSet.class;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public Object instantiate(int anticipatedSize)
/* 36:   */   {
/* 37:55 */     return new TreeSet(this.comparator);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public PersistentCollection wrap(SessionImplementor session, Object collection)
/* 41:   */   {
/* 42:59 */     return new PersistentSortedSet(session, (SortedSet)collection);
/* 43:   */   }
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.SortedSetType
 * JD-Core Version:    0.7.0.1
 */