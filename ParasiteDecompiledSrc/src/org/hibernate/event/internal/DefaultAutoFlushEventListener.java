/*  1:   */ package org.hibernate.event.internal;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.FlushMode;
/*  5:   */ import org.hibernate.HibernateException;
/*  6:   */ import org.hibernate.engine.spi.ActionQueue;
/*  7:   */ import org.hibernate.engine.spi.PersistenceContext;
/*  8:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  9:   */ import org.hibernate.event.spi.AutoFlushEvent;
/* 10:   */ import org.hibernate.event.spi.AutoFlushEventListener;
/* 11:   */ import org.hibernate.event.spi.EventSource;
/* 12:   */ import org.hibernate.internal.CoreMessageLogger;
/* 13:   */ import org.hibernate.stat.Statistics;
/* 14:   */ import org.hibernate.stat.spi.StatisticsImplementor;
/* 15:   */ import org.jboss.logging.Logger;
/* 16:   */ 
/* 17:   */ public class DefaultAutoFlushEventListener
/* 18:   */   extends AbstractFlushingEventListener
/* 19:   */   implements AutoFlushEventListener
/* 20:   */ {
/* 21:43 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, DefaultAutoFlushEventListener.class.getName());
/* 22:   */   
/* 23:   */   public void onAutoFlush(AutoFlushEvent event)
/* 24:   */     throws HibernateException
/* 25:   */   {
/* 26:53 */     EventSource source = event.getSession();
/* 27:54 */     if (flushMightBeNeeded(source))
/* 28:   */     {
/* 29:55 */       flushEverythingToExecutions(event);
/* 30:56 */       if (flushIsReallyNeeded(event, source))
/* 31:   */       {
/* 32:57 */         LOG.trace("Need to execute flush");
/* 33:   */         
/* 34:59 */         performExecutions(source);
/* 35:60 */         postFlush(source);
/* 36:64 */         if (source.getFactory().getStatistics().isStatisticsEnabled()) {
/* 37:65 */           source.getFactory().getStatisticsImplementor().flush();
/* 38:   */         }
/* 39:   */       }
/* 40:   */       else
/* 41:   */       {
/* 42:69 */         LOG.trace("Don't need to execute flush");
/* 43:70 */         int oldSize = source.getActionQueue().numberOfCollectionRemovals();
/* 44:71 */         source.getActionQueue().clearFromFlushNeededCheck(oldSize);
/* 45:   */       }
/* 46:74 */       event.setFlushRequired(flushIsReallyNeeded(event, source));
/* 47:   */     }
/* 48:   */   }
/* 49:   */   
/* 50:   */   private boolean flushIsReallyNeeded(AutoFlushEvent event, EventSource source)
/* 51:   */   {
/* 52:79 */     return (source.getActionQueue().areTablesToBeUpdated(event.getQuerySpaces())) || (source.getFlushMode() == FlushMode.ALWAYS);
/* 53:   */   }
/* 54:   */   
/* 55:   */   private boolean flushMightBeNeeded(EventSource source)
/* 56:   */   {
/* 57:85 */     return (!source.getFlushMode().lessThan(FlushMode.AUTO)) && (source.getDontFlushFromFind() == 0) && ((source.getPersistenceContext().getEntityEntries().size() > 0) || (source.getPersistenceContext().getCollectionEntries().size() > 0));
/* 58:   */   }
/* 59:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.DefaultAutoFlushEventListener
 * JD-Core Version:    0.7.0.1
 */