/*  1:   */ package org.hibernate.event.spi;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.persister.entity.EntityPersister;
/*  5:   */ 
/*  6:   */ public class PreUpdateEvent
/*  7:   */   extends AbstractPreDatabaseOperationEvent
/*  8:   */ {
/*  9:   */   private Object[] state;
/* 10:   */   private Object[] oldState;
/* 11:   */   
/* 12:   */   public PreUpdateEvent(Object entity, Serializable id, Object[] state, Object[] oldState, EntityPersister persister, EventSource source)
/* 13:   */   {
/* 14:59 */     super(source, entity, id, persister);
/* 15:60 */     this.state = state;
/* 16:61 */     this.oldState = oldState;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public Object[] getState()
/* 20:   */   {
/* 21:70 */     return this.state;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Object[] getOldState()
/* 25:   */   {
/* 26:80 */     return this.oldState;
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.PreUpdateEvent
 * JD-Core Version:    0.7.0.1
 */