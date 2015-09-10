/*  1:   */ package org.hibernate.event.spi;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.persister.entity.EntityPersister;
/*  5:   */ 
/*  6:   */ public abstract class AbstractPreDatabaseOperationEvent
/*  7:   */   extends AbstractEvent
/*  8:   */ {
/*  9:   */   private final Object entity;
/* 10:   */   private final Serializable id;
/* 11:   */   private final EntityPersister persister;
/* 12:   */   
/* 13:   */   public AbstractPreDatabaseOperationEvent(EventSource source, Object entity, Serializable id, EntityPersister persister)
/* 14:   */   {
/* 15:53 */     super(source);
/* 16:54 */     this.entity = entity;
/* 17:55 */     this.id = id;
/* 18:56 */     this.persister = persister;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public Object getEntity()
/* 22:   */   {
/* 23:65 */     return this.entity;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Serializable getId()
/* 27:   */   {
/* 28:74 */     return this.id;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public EntityPersister getPersister()
/* 32:   */   {
/* 33:83 */     return this.persister;
/* 34:   */   }
/* 35:   */   
/* 36:   */   /**
/* 37:   */    * @deprecated
/* 38:   */    */
/* 39:   */   public EventSource getSource()
/* 40:   */   {
/* 41:98 */     return getSession();
/* 42:   */   }
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.AbstractPreDatabaseOperationEvent
 * JD-Core Version:    0.7.0.1
 */