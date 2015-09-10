/*  1:   */ package org.hibernate.action.internal;
/*  2:   */ 
/*  3:   */ import org.hibernate.OptimisticLockException;
/*  4:   */ import org.hibernate.action.spi.BeforeTransactionCompletionProcess;
/*  5:   */ import org.hibernate.engine.spi.EntityEntry;
/*  6:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  7:   */ import org.hibernate.persister.entity.EntityPersister;
/*  8:   */ import org.hibernate.pretty.MessageHelper;
/*  9:   */ 
/* 10:   */ public class EntityVerifyVersionProcess
/* 11:   */   implements BeforeTransactionCompletionProcess
/* 12:   */ {
/* 13:   */   private final Object object;
/* 14:   */   private final EntityEntry entry;
/* 15:   */   
/* 16:   */   public EntityVerifyVersionProcess(Object object, EntityEntry entry)
/* 17:   */   {
/* 18:44 */     this.object = object;
/* 19:45 */     this.entry = entry;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void doBeforeTransactionCompletion(SessionImplementor session)
/* 23:   */   {
/* 24:50 */     EntityPersister persister = this.entry.getPersister();
/* 25:   */     
/* 26:52 */     Object latestVersion = persister.getCurrentVersion(this.entry.getId(), session);
/* 27:53 */     if (!this.entry.getVersion().equals(latestVersion)) {
/* 28:54 */       throw new OptimisticLockException(this.object, "Newer version [" + latestVersion + "] of entity [" + MessageHelper.infoString(this.entry.getEntityName(), this.entry.getId()) + "] found in database");
/* 29:   */     }
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.action.internal.EntityVerifyVersionProcess
 * JD-Core Version:    0.7.0.1
 */