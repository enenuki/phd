/*  1:   */ package org.hibernate.secure.internal;
/*  2:   */ 
/*  3:   */ import javax.security.jacc.EJBMethodPermission;
/*  4:   */ import org.hibernate.event.spi.PreInsertEvent;
/*  5:   */ import org.hibernate.event.spi.PreInsertEventListener;
/*  6:   */ import org.hibernate.persister.entity.EntityPersister;
/*  7:   */ 
/*  8:   */ public class JACCPreInsertEventListener
/*  9:   */   implements PreInsertEventListener, JACCSecurityListener
/* 10:   */ {
/* 11:   */   private final String contextId;
/* 12:   */   
/* 13:   */   public JACCPreInsertEventListener(String contextId)
/* 14:   */   {
/* 15:40 */     this.contextId = contextId;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public boolean onPreInsert(PreInsertEvent event)
/* 19:   */   {
/* 20:44 */     EJBMethodPermission insertPermission = new EJBMethodPermission(event.getPersister().getEntityName(), "insert", null, null);
/* 21:   */     
/* 22:   */ 
/* 23:   */ 
/* 24:   */ 
/* 25:   */ 
/* 26:50 */     JACCPermissions.checkPermission(event.getEntity().getClass(), this.contextId, insertPermission);
/* 27:51 */     return false;
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.secure.internal.JACCPreInsertEventListener
 * JD-Core Version:    0.7.0.1
 */