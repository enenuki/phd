/*  1:   */ package org.hibernate.action.internal;
/*  2:   */ 
/*  3:   */ import org.hibernate.action.spi.BeforeTransactionCompletionProcess;
/*  4:   */ import org.hibernate.engine.spi.EntityEntry;
/*  5:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  6:   */ import org.hibernate.persister.entity.EntityPersister;
/*  7:   */ 
/*  8:   */ public class EntityIncrementVersionProcess
/*  9:   */   implements BeforeTransactionCompletionProcess
/* 10:   */ {
/* 11:   */   private final Object object;
/* 12:   */   private final EntityEntry entry;
/* 13:   */   
/* 14:   */   public EntityIncrementVersionProcess(Object object, EntityEntry entry)
/* 15:   */   {
/* 16:41 */     this.object = object;
/* 17:42 */     this.entry = entry;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void doBeforeTransactionCompletion(SessionImplementor session)
/* 21:   */   {
/* 22:52 */     EntityPersister persister = this.entry.getPersister();
/* 23:53 */     Object nextVersion = persister.forceVersionIncrement(this.entry.getId(), this.entry.getVersion(), session);
/* 24:   */     
/* 25:   */ 
/* 26:56 */     this.entry.forceLocked(this.object, nextVersion);
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.action.internal.EntityIncrementVersionProcess
 * JD-Core Version:    0.7.0.1
 */