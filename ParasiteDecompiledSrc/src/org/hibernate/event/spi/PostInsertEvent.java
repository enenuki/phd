/*  1:   */ package org.hibernate.event.spi;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.persister.entity.EntityPersister;
/*  5:   */ 
/*  6:   */ public class PostInsertEvent
/*  7:   */   extends AbstractEvent
/*  8:   */ {
/*  9:   */   private Object entity;
/* 10:   */   private EntityPersister persister;
/* 11:   */   private Object[] state;
/* 12:   */   private Serializable id;
/* 13:   */   
/* 14:   */   public PostInsertEvent(Object entity, Serializable id, Object[] state, EntityPersister persister, EventSource source)
/* 15:   */   {
/* 16:48 */     super(source);
/* 17:49 */     this.entity = entity;
/* 18:50 */     this.id = id;
/* 19:51 */     this.state = state;
/* 20:52 */     this.persister = persister;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Object getEntity()
/* 24:   */   {
/* 25:56 */     return this.entity;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Serializable getId()
/* 29:   */   {
/* 30:59 */     return this.id;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public EntityPersister getPersister()
/* 34:   */   {
/* 35:62 */     return this.persister;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Object[] getState()
/* 39:   */   {
/* 40:65 */     return this.state;
/* 41:   */   }
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.PostInsertEvent
 * JD-Core Version:    0.7.0.1
 */