/*  1:   */ package org.hibernate.loader.custom;
/*  2:   */ 
/*  3:   */ import org.hibernate.HibernateException;
/*  4:   */ import org.hibernate.LockMode;
/*  5:   */ 
/*  6:   */ public abstract class NonScalarReturn
/*  7:   */   implements Return
/*  8:   */ {
/*  9:   */   private final String alias;
/* 10:   */   private final LockMode lockMode;
/* 11:   */   
/* 12:   */   public NonScalarReturn(String alias, LockMode lockMode)
/* 13:   */   {
/* 14:39 */     this.alias = alias;
/* 15:40 */     if (alias == null) {
/* 16:41 */       throw new HibernateException("alias must be specified");
/* 17:   */     }
/* 18:43 */     this.lockMode = lockMode;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String getAlias()
/* 22:   */   {
/* 23:47 */     return this.alias;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public LockMode getLockMode()
/* 27:   */   {
/* 28:51 */     return this.lockMode;
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.custom.NonScalarReturn
 * JD-Core Version:    0.7.0.1
 */