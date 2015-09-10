/*  1:   */ package org.hibernate.service.internal;
/*  2:   */ 
/*  3:   */ public class ProvidedService<R>
/*  4:   */ {
/*  5:   */   private final Class<R> serviceRole;
/*  6:   */   private final R service;
/*  7:   */   
/*  8:   */   public ProvidedService(Class<R> serviceRole, R service)
/*  9:   */   {
/* 10:36 */     this.serviceRole = serviceRole;
/* 11:37 */     this.service = service;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public Class<R> getServiceRole()
/* 15:   */   {
/* 16:41 */     return this.serviceRole;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public R getService()
/* 20:   */   {
/* 21:45 */     return this.service;
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.internal.ProvidedService
 * JD-Core Version:    0.7.0.1
 */