/*  1:   */ package org.hibernate.cfg.annotations;
/*  2:   */ 
/*  3:   */ import org.hibernate.annotations.OrderBy;
/*  4:   */ import org.hibernate.internal.CoreMessageLogger;
/*  5:   */ import org.hibernate.mapping.Collection;
/*  6:   */ import org.hibernate.mapping.PersistentClass;
/*  7:   */ import org.hibernate.mapping.Set;
/*  8:   */ import org.jboss.logging.Logger;
/*  9:   */ 
/* 10:   */ public class SetBinder
/* 11:   */   extends CollectionBinder
/* 12:   */ {
/* 13:39 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, SetBinder.class.getName());
/* 14:   */   
/* 15:   */   public SetBinder() {}
/* 16:   */   
/* 17:   */   public SetBinder(boolean sorted)
/* 18:   */   {
/* 19:45 */     super(sorted);
/* 20:   */   }
/* 21:   */   
/* 22:   */   protected Collection createCollection(PersistentClass persistentClass)
/* 23:   */   {
/* 24:50 */     return new Set(getMappings(), persistentClass);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void setSqlOrderBy(OrderBy orderByAnn)
/* 28:   */   {
/* 29:55 */     if (orderByAnn != null) {
/* 30:56 */       super.setSqlOrderBy(orderByAnn);
/* 31:   */     }
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.annotations.SetBinder
 * JD-Core Version:    0.7.0.1
 */