/*  1:   */ package org.hibernate.event.spi;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.persister.entity.EntityPersister;
/*  5:   */ 
/*  6:   */ public class PostDeleteEvent
/*  7:   */   extends AbstractEvent
/*  8:   */ {
/*  9:   */   private Object entity;
/* 10:   */   private EntityPersister persister;
/* 11:   */   private Serializable id;
/* 12:   */   private Object[] deletedState;
/* 13:   */   
/* 14:   */   public PostDeleteEvent(Object entity, Serializable id, Object[] deletedState, EntityPersister persister, EventSource source)
/* 15:   */   {
/* 16:48 */     super(source);
/* 17:49 */     this.entity = entity;
/* 18:50 */     this.id = id;
/* 19:51 */     this.persister = persister;
/* 20:52 */     this.deletedState = deletedState;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Serializable getId()
/* 24:   */   {
/* 25:56 */     return this.id;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public EntityPersister getPersister()
/* 29:   */   {
/* 30:59 */     return this.persister;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public Object getEntity()
/* 34:   */   {
/* 35:62 */     return this.entity;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Object[] getDeletedState()
/* 39:   */   {
/* 40:65 */     return this.deletedState;
/* 41:   */   }
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.PostDeleteEvent
 * JD-Core Version:    0.7.0.1
 */