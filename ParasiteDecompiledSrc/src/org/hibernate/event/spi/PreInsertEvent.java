/*  1:   */ package org.hibernate.event.spi;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.persister.entity.EntityPersister;
/*  5:   */ 
/*  6:   */ public class PreInsertEvent
/*  7:   */   extends AbstractPreDatabaseOperationEvent
/*  8:   */ {
/*  9:   */   private Object[] state;
/* 10:   */   
/* 11:   */   public PreInsertEvent(Object entity, Serializable id, Object[] state, EntityPersister persister, EventSource source)
/* 12:   */   {
/* 13:55 */     super(source, entity, id, persister);
/* 14:56 */     this.state = state;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public Object[] getState()
/* 18:   */   {
/* 19:65 */     return this.state;
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.PreInsertEvent
 * JD-Core Version:    0.7.0.1
 */