/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.HashSet;
/*  5:   */ import java.util.Set;
/*  6:   */ import org.hibernate.collection.internal.PersistentSet;
/*  7:   */ import org.hibernate.collection.spi.PersistentCollection;
/*  8:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  9:   */ import org.hibernate.persister.collection.CollectionPersister;
/* 10:   */ 
/* 11:   */ public class SetType
/* 12:   */   extends CollectionType
/* 13:   */ {
/* 14:   */   public SetType(TypeFactory.TypeScope typeScope, String role, String propertyRef, boolean isEmbeddedInXML)
/* 15:   */   {
/* 16:37 */     super(typeScope, role, propertyRef, isEmbeddedInXML);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public PersistentCollection instantiate(SessionImplementor session, CollectionPersister persister, Serializable key)
/* 20:   */   {
/* 21:41 */     return new PersistentSet(session);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Class getReturnedClass()
/* 25:   */   {
/* 26:45 */     return Set.class;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public PersistentCollection wrap(SessionImplementor session, Object collection)
/* 30:   */   {
/* 31:49 */     return new PersistentSet(session, (Set)collection);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Object instantiate(int anticipatedSize)
/* 35:   */   {
/* 36:53 */     return anticipatedSize <= 0 ? new HashSet() : new HashSet(anticipatedSize + (int)(anticipatedSize * 0.75F), 0.75F);
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.SetType
 * JD-Core Version:    0.7.0.1
 */