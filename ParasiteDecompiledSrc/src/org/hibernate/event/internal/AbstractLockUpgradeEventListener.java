/*   1:    */ package org.hibernate.event.internal;
/*   2:    */ 
/*   3:    */ import org.hibernate.LockMode;
/*   4:    */ import org.hibernate.LockOptions;
/*   5:    */ import org.hibernate.ObjectDeletedException;
/*   6:    */ import org.hibernate.cache.spi.CacheKey;
/*   7:    */ import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
/*   8:    */ import org.hibernate.cache.spi.access.SoftLock;
/*   9:    */ import org.hibernate.engine.spi.EntityEntry;
/*  10:    */ import org.hibernate.engine.spi.Status;
/*  11:    */ import org.hibernate.event.spi.EventSource;
/*  12:    */ import org.hibernate.internal.CoreMessageLogger;
/*  13:    */ import org.hibernate.persister.entity.EntityPersister;
/*  14:    */ import org.hibernate.pretty.MessageHelper;
/*  15:    */ import org.jboss.logging.Logger;
/*  16:    */ 
/*  17:    */ public class AbstractLockUpgradeEventListener
/*  18:    */   extends AbstractReassociateEventListener
/*  19:    */ {
/*  20: 48 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, AbstractLockUpgradeEventListener.class.getName());
/*  21:    */   
/*  22:    */   protected void upgradeLock(Object object, EntityEntry entry, LockOptions lockOptions, EventSource source)
/*  23:    */   {
/*  24: 60 */     LockMode requestedLockMode = lockOptions.getLockMode();
/*  25: 61 */     if (requestedLockMode.greaterThan(entry.getLockMode()))
/*  26:    */     {
/*  27: 65 */       if (entry.getStatus() != Status.MANAGED) {
/*  28: 66 */         throw new ObjectDeletedException("attempted to lock a deleted instance", entry.getId(), entry.getPersister().getEntityName());
/*  29:    */       }
/*  30: 73 */       EntityPersister persister = entry.getPersister();
/*  31: 75 */       if (LOG.isTraceEnabled()) {
/*  32: 76 */         LOG.tracev("Locking {0} in mode: {1}", MessageHelper.infoString(persister, entry.getId(), source.getFactory()), requestedLockMode);
/*  33:    */       }
/*  34:    */       SoftLock lock;
/*  35:    */       CacheKey ck;
/*  36:    */       SoftLock lock;
/*  37: 81 */       if (persister.hasCache())
/*  38:    */       {
/*  39: 82 */         CacheKey ck = source.generateCacheKey(entry.getId(), persister.getIdentifierType(), persister.getRootEntityName());
/*  40: 83 */         lock = persister.getCacheAccessStrategy().lockItem(ck, entry.getVersion());
/*  41:    */       }
/*  42:    */       else
/*  43:    */       {
/*  44: 86 */         ck = null;
/*  45: 87 */         lock = null;
/*  46:    */       }
/*  47:    */       try
/*  48:    */       {
/*  49: 91 */         if ((persister.isVersioned()) && (requestedLockMode == LockMode.FORCE))
/*  50:    */         {
/*  51: 93 */           Object nextVersion = persister.forceVersionIncrement(entry.getId(), entry.getVersion(), source);
/*  52:    */           
/*  53:    */ 
/*  54: 96 */           entry.forceLocked(object, nextVersion);
/*  55:    */         }
/*  56:    */         else
/*  57:    */         {
/*  58: 99 */           persister.lock(entry.getId(), entry.getVersion(), object, lockOptions, source);
/*  59:    */         }
/*  60:101 */         entry.setLockMode(requestedLockMode);
/*  61:    */       }
/*  62:    */       finally
/*  63:    */       {
/*  64:106 */         if (persister.hasCache()) {
/*  65:107 */           persister.getCacheAccessStrategy().unlockItem(ck, lock);
/*  66:    */         }
/*  67:    */       }
/*  68:    */     }
/*  69:    */   }
/*  70:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.AbstractLockUpgradeEventListener
 * JD-Core Version:    0.7.0.1
 */