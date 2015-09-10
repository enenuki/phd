/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.List;
/*  6:   */ import org.hibernate.collection.internal.PersistentList;
/*  7:   */ import org.hibernate.collection.spi.PersistentCollection;
/*  8:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  9:   */ import org.hibernate.persister.collection.CollectionPersister;
/* 10:   */ 
/* 11:   */ public class ListType
/* 12:   */   extends CollectionType
/* 13:   */ {
/* 14:   */   public ListType(TypeFactory.TypeScope typeScope, String role, String propertyRef, boolean isEmbeddedInXML)
/* 15:   */   {
/* 16:38 */     super(typeScope, role, propertyRef, isEmbeddedInXML);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public PersistentCollection instantiate(SessionImplementor session, CollectionPersister persister, Serializable key)
/* 20:   */   {
/* 21:42 */     return new PersistentList(session);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Class getReturnedClass()
/* 25:   */   {
/* 26:46 */     return List.class;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public PersistentCollection wrap(SessionImplementor session, Object collection)
/* 30:   */   {
/* 31:50 */     return new PersistentList(session, (List)collection);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Object instantiate(int anticipatedSize)
/* 35:   */   {
/* 36:54 */     return anticipatedSize <= 0 ? new ArrayList() : new ArrayList(anticipatedSize + 1);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public Object indexOf(Object collection, Object element)
/* 40:   */   {
/* 41:58 */     List list = (List)collection;
/* 42:59 */     for (int i = 0; i < list.size(); i++) {
/* 43:61 */       if (list.get(i) == element) {
/* 44:61 */         return Integer.valueOf(i);
/* 45:   */       }
/* 46:   */     }
/* 47:63 */     return null;
/* 48:   */   }
/* 49:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.ListType
 * JD-Core Version:    0.7.0.1
 */