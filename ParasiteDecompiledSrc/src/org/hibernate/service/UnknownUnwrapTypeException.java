/*  1:   */ package org.hibernate.service;
/*  2:   */ 
/*  3:   */ import org.hibernate.HibernateException;
/*  4:   */ 
/*  5:   */ public class UnknownUnwrapTypeException
/*  6:   */   extends HibernateException
/*  7:   */ {
/*  8:   */   public UnknownUnwrapTypeException(Class unwrapType)
/*  9:   */   {
/* 10:33 */     super("Cannot unwrap to requested type [" + unwrapType.getName() + "]");
/* 11:   */   }
/* 12:   */   
/* 13:   */   public UnknownUnwrapTypeException(Class unwrapType, Throwable root)
/* 14:   */   {
/* 15:37 */     this(unwrapType);
/* 16:38 */     super.initCause(root);
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.UnknownUnwrapTypeException
 * JD-Core Version:    0.7.0.1
 */