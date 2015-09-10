/*  1:   */ package org.hibernate.service.spi;
/*  2:   */ 
/*  3:   */ import org.hibernate.HibernateException;
/*  4:   */ 
/*  5:   */ public class ServiceException
/*  6:   */   extends HibernateException
/*  7:   */ {
/*  8:   */   public ServiceException(String message, Throwable root)
/*  9:   */   {
/* 10:34 */     super(message, root);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public ServiceException(String message)
/* 14:   */   {
/* 15:38 */     super(message);
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.spi.ServiceException
 * JD-Core Version:    0.7.0.1
 */