/*  1:   */ package org.hibernate.service.internal;
/*  2:   */ 
/*  3:   */ import org.hibernate.HibernateException;
/*  4:   */ 
/*  5:   */ public class ServiceProxyGenerationException
/*  6:   */   extends HibernateException
/*  7:   */ {
/*  8:   */   public ServiceProxyGenerationException(String string, Throwable root)
/*  9:   */   {
/* 10:35 */     super(string, root);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public ServiceProxyGenerationException(Throwable root)
/* 14:   */   {
/* 15:39 */     super(root);
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.internal.ServiceProxyGenerationException
 * JD-Core Version:    0.7.0.1
 */