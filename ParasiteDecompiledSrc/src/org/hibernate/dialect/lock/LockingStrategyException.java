/*  1:   */ package org.hibernate.dialect.lock;
/*  2:   */ 
/*  3:   */ import org.hibernate.HibernateException;
/*  4:   */ 
/*  5:   */ public abstract class LockingStrategyException
/*  6:   */   extends HibernateException
/*  7:   */ {
/*  8:   */   private final Object entity;
/*  9:   */   
/* 10:   */   public LockingStrategyException(Object entity, String message)
/* 11:   */   {
/* 12:37 */     super(message);
/* 13:38 */     this.entity = entity;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public LockingStrategyException(Object entity, String message, Throwable root)
/* 17:   */   {
/* 18:42 */     super(message, root);
/* 19:43 */     this.entity = entity;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Object getEntity()
/* 23:   */   {
/* 24:47 */     return this.entity;
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.lock.LockingStrategyException
 * JD-Core Version:    0.7.0.1
 */