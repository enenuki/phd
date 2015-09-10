/*  1:   */ package org.hibernate.event.spi;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.persister.entity.EntityPersister;
/*  5:   */ 
/*  6:   */ public class PreDeleteEvent
/*  7:   */   extends AbstractPreDatabaseOperationEvent
/*  8:   */ {
/*  9:   */   private Object[] deletedState;
/* 10:   */   
/* 11:   */   public PreDeleteEvent(Object entity, Serializable id, Object[] deletedState, EntityPersister persister, EventSource source)
/* 12:   */   {
/* 13:56 */     super(source, entity, id, persister);
/* 14:57 */     this.deletedState = deletedState;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public Object[] getDeletedState()
/* 18:   */   {
/* 19:67 */     return this.deletedState;
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.PreDeleteEvent
 * JD-Core Version:    0.7.0.1
 */