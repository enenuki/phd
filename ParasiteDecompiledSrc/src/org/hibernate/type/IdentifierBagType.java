/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.Collection;
/*  6:   */ import org.hibernate.HibernateException;
/*  7:   */ import org.hibernate.collection.internal.PersistentIdentifierBag;
/*  8:   */ import org.hibernate.collection.spi.PersistentCollection;
/*  9:   */ import org.hibernate.engine.spi.SessionImplementor;
/* 10:   */ import org.hibernate.persister.collection.CollectionPersister;
/* 11:   */ 
/* 12:   */ public class IdentifierBagType
/* 13:   */   extends CollectionType
/* 14:   */ {
/* 15:   */   public IdentifierBagType(TypeFactory.TypeScope typeScope, String role, String propertyRef, boolean isEmbeddedInXML)
/* 16:   */   {
/* 17:38 */     super(typeScope, role, propertyRef, isEmbeddedInXML);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public PersistentCollection instantiate(SessionImplementor session, CollectionPersister persister, Serializable key)
/* 21:   */     throws HibernateException
/* 22:   */   {
/* 23:46 */     return new PersistentIdentifierBag(session);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Object instantiate(int anticipatedSize)
/* 27:   */   {
/* 28:50 */     return anticipatedSize <= 0 ? new ArrayList() : new ArrayList(anticipatedSize + 1);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Class getReturnedClass()
/* 32:   */   {
/* 33:54 */     return Collection.class;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public PersistentCollection wrap(SessionImplementor session, Object collection)
/* 37:   */   {
/* 38:58 */     return new PersistentIdentifierBag(session, (Collection)collection);
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.IdentifierBagType
 * JD-Core Version:    0.7.0.1
 */