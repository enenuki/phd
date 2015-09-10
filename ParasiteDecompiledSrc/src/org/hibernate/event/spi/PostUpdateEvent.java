/*  1:   */ package org.hibernate.event.spi;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.persister.entity.EntityPersister;
/*  5:   */ 
/*  6:   */ public class PostUpdateEvent
/*  7:   */   extends AbstractEvent
/*  8:   */ {
/*  9:   */   private Object entity;
/* 10:   */   private EntityPersister persister;
/* 11:   */   private Object[] state;
/* 12:   */   private Object[] oldState;
/* 13:   */   private Serializable id;
/* 14:   */   private final int[] dirtyProperties;
/* 15:   */   
/* 16:   */   public PostUpdateEvent(Object entity, Serializable id, Object[] state, Object[] oldState, int[] dirtyProperties, EntityPersister persister, EventSource source)
/* 17:   */   {
/* 18:53 */     super(source);
/* 19:54 */     this.entity = entity;
/* 20:55 */     this.id = id;
/* 21:56 */     this.state = state;
/* 22:57 */     this.oldState = oldState;
/* 23:58 */     this.dirtyProperties = dirtyProperties;
/* 24:59 */     this.persister = persister;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public Object getEntity()
/* 28:   */   {
/* 29:63 */     return this.entity;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public Serializable getId()
/* 33:   */   {
/* 34:66 */     return this.id;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public Object[] getOldState()
/* 38:   */   {
/* 39:69 */     return this.oldState;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public EntityPersister getPersister()
/* 43:   */   {
/* 44:72 */     return this.persister;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public Object[] getState()
/* 48:   */   {
/* 49:75 */     return this.state;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public int[] getDirtyProperties()
/* 53:   */   {
/* 54:79 */     return this.dirtyProperties;
/* 55:   */   }
/* 56:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.PostUpdateEvent
 * JD-Core Version:    0.7.0.1
 */