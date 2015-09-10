/*  1:   */ package org.hibernate.event.internal;
/*  2:   */ 
/*  3:   */ import org.hibernate.HibernateException;
/*  4:   */ import org.hibernate.engine.spi.ActionQueue;
/*  5:   */ import org.hibernate.event.spi.DirtyCheckEvent;
/*  6:   */ import org.hibernate.event.spi.DirtyCheckEventListener;
/*  7:   */ import org.hibernate.event.spi.EventSource;
/*  8:   */ import org.hibernate.internal.CoreMessageLogger;
/*  9:   */ import org.jboss.logging.Logger;
/* 10:   */ 
/* 11:   */ public class DefaultDirtyCheckEventListener
/* 12:   */   extends AbstractFlushingEventListener
/* 13:   */   implements DirtyCheckEventListener
/* 14:   */ {
/* 15:42 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, DefaultDirtyCheckEventListener.class.getName());
/* 16:   */   
/* 17:   */   public void onDirtyCheck(DirtyCheckEvent event)
/* 18:   */     throws HibernateException
/* 19:   */   {
/* 20:52 */     int oldSize = event.getSession().getActionQueue().numberOfCollectionRemovals();
/* 21:   */     try
/* 22:   */     {
/* 23:55 */       flushEverythingToExecutions(event);
/* 24:56 */       boolean wasNeeded = event.getSession().getActionQueue().hasAnyQueuedActions();
/* 25:57 */       if (wasNeeded) {
/* 26:58 */         LOG.debug("Session dirty");
/* 27:   */       } else {
/* 28:60 */         LOG.debug("Session not dirty");
/* 29:   */       }
/* 30:61 */       event.setDirty(wasNeeded);
/* 31:   */     }
/* 32:   */     finally
/* 33:   */     {
/* 34:64 */       event.getSession().getActionQueue().clearFromFlushNeededCheck(oldSize);
/* 35:   */     }
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.DefaultDirtyCheckEventListener
 * JD-Core Version:    0.7.0.1
 */