/*  1:   */ package org.hibernate.event.spi;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.persister.entity.EntityPersister;
/*  5:   */ 
/*  6:   */ public class PreLoadEvent
/*  7:   */   extends AbstractEvent
/*  8:   */ {
/*  9:   */   private Object entity;
/* 10:   */   private Object[] state;
/* 11:   */   private Serializable id;
/* 12:   */   private EntityPersister persister;
/* 13:   */   
/* 14:   */   public PreLoadEvent(EventSource session)
/* 15:   */   {
/* 16:43 */     super(session);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public Object getEntity()
/* 20:   */   {
/* 21:47 */     return this.entity;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Serializable getId()
/* 25:   */   {
/* 26:51 */     return this.id;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public EntityPersister getPersister()
/* 30:   */   {
/* 31:55 */     return this.persister;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Object[] getState()
/* 35:   */   {
/* 36:59 */     return this.state;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public PreLoadEvent setEntity(Object entity)
/* 40:   */   {
/* 41:63 */     this.entity = entity;
/* 42:64 */     return this;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public PreLoadEvent setId(Serializable id)
/* 46:   */   {
/* 47:68 */     this.id = id;
/* 48:69 */     return this;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public PreLoadEvent setPersister(EntityPersister persister)
/* 52:   */   {
/* 53:73 */     this.persister = persister;
/* 54:74 */     return this;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public PreLoadEvent setState(Object[] state)
/* 58:   */   {
/* 59:78 */     this.state = state;
/* 60:79 */     return this;
/* 61:   */   }
/* 62:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.PreLoadEvent
 * JD-Core Version:    0.7.0.1
 */