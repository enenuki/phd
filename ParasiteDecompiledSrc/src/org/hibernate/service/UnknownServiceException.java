/*  1:   */ package org.hibernate.service;
/*  2:   */ 
/*  3:   */ import org.hibernate.HibernateException;
/*  4:   */ 
/*  5:   */ public class UnknownServiceException
/*  6:   */   extends HibernateException
/*  7:   */ {
/*  8:   */   public final Class serviceRole;
/*  9:   */   
/* 10:   */   public UnknownServiceException(Class serviceRole)
/* 11:   */   {
/* 12:36 */     super("Unknown service requested [" + serviceRole.getName() + "]");
/* 13:37 */     this.serviceRole = serviceRole;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public Class getServiceRole()
/* 17:   */   {
/* 18:41 */     return this.serviceRole;
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.UnknownServiceException
 * JD-Core Version:    0.7.0.1
 */