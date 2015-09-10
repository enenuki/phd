/*  1:   */ package org.hibernate.secure.internal;
/*  2:   */ 
/*  3:   */ import javax.security.jacc.EJBMethodPermission;
/*  4:   */ import org.hibernate.event.spi.PreLoadEvent;
/*  5:   */ import org.hibernate.event.spi.PreLoadEventListener;
/*  6:   */ import org.hibernate.persister.entity.EntityPersister;
/*  7:   */ 
/*  8:   */ public class JACCPreLoadEventListener
/*  9:   */   implements PreLoadEventListener, JACCSecurityListener
/* 10:   */ {
/* 11:   */   private final String contextId;
/* 12:   */   
/* 13:   */   public JACCPreLoadEventListener(String contextId)
/* 14:   */   {
/* 15:40 */     this.contextId = contextId;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void onPreLoad(PreLoadEvent event)
/* 19:   */   {
/* 20:44 */     EJBMethodPermission loadPermission = new EJBMethodPermission(event.getPersister().getEntityName(), "read", null, null);
/* 21:   */     
/* 22:   */ 
/* 23:   */ 
/* 24:   */ 
/* 25:   */ 
/* 26:50 */     JACCPermissions.checkPermission(event.getEntity().getClass(), this.contextId, loadPermission);
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.secure.internal.JACCPreLoadEventListener
 * JD-Core Version:    0.7.0.1
 */