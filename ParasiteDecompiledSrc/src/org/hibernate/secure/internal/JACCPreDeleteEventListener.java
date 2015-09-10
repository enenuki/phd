/*  1:   */ package org.hibernate.secure.internal;
/*  2:   */ 
/*  3:   */ import javax.security.jacc.EJBMethodPermission;
/*  4:   */ import org.hibernate.event.spi.PreDeleteEvent;
/*  5:   */ import org.hibernate.event.spi.PreDeleteEventListener;
/*  6:   */ import org.hibernate.persister.entity.EntityPersister;
/*  7:   */ 
/*  8:   */ public class JACCPreDeleteEventListener
/*  9:   */   implements PreDeleteEventListener, JACCSecurityListener
/* 10:   */ {
/* 11:   */   private final String contextId;
/* 12:   */   
/* 13:   */   public JACCPreDeleteEventListener(String contextId)
/* 14:   */   {
/* 15:40 */     this.contextId = contextId;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public boolean onPreDelete(PreDeleteEvent event)
/* 19:   */   {
/* 20:44 */     EJBMethodPermission deletePermission = new EJBMethodPermission(event.getPersister().getEntityName(), "delete", null, null);
/* 21:   */     
/* 22:   */ 
/* 23:   */ 
/* 24:   */ 
/* 25:   */ 
/* 26:50 */     JACCPermissions.checkPermission(event.getEntity().getClass(), this.contextId, deletePermission);
/* 27:51 */     return false;
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.secure.internal.JACCPreDeleteEventListener
 * JD-Core Version:    0.7.0.1
 */