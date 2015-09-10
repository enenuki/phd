/*  1:   */ package org.hibernate.event.spi;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.persister.entity.EntityPersister;
/*  5:   */ 
/*  6:   */ public class PostLoadEvent
/*  7:   */   extends AbstractEvent
/*  8:   */ {
/*  9:   */   private Object entity;
/* 10:   */   private Serializable id;
/* 11:   */   private EntityPersister persister;
/* 12:   */   
/* 13:   */   public PostLoadEvent(EventSource session)
/* 14:   */   {
/* 15:41 */     super(session);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Object getEntity()
/* 19:   */   {
/* 20:45 */     return this.entity;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public EntityPersister getPersister()
/* 24:   */   {
/* 25:49 */     return this.persister;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Serializable getId()
/* 29:   */   {
/* 30:53 */     return this.id;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public PostLoadEvent setEntity(Object entity)
/* 34:   */   {
/* 35:57 */     this.entity = entity;
/* 36:58 */     return this;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public PostLoadEvent setId(Serializable id)
/* 40:   */   {
/* 41:62 */     this.id = id;
/* 42:63 */     return this;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public PostLoadEvent setPersister(EntityPersister persister)
/* 46:   */   {
/* 47:67 */     this.persister = persister;
/* 48:68 */     return this;
/* 49:   */   }
/* 50:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.PostLoadEvent
 * JD-Core Version:    0.7.0.1
 */