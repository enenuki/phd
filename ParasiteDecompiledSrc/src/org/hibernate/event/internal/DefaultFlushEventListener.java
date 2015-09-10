/*  1:   */ package org.hibernate.event.internal;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.engine.spi.PersistenceContext;
/*  6:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  7:   */ import org.hibernate.event.spi.EventSource;
/*  8:   */ import org.hibernate.event.spi.FlushEvent;
/*  9:   */ import org.hibernate.event.spi.FlushEventListener;
/* 10:   */ import org.hibernate.stat.Statistics;
/* 11:   */ import org.hibernate.stat.spi.StatisticsImplementor;
/* 12:   */ 
/* 13:   */ public class DefaultFlushEventListener
/* 14:   */   extends AbstractFlushingEventListener
/* 15:   */   implements FlushEventListener
/* 16:   */ {
/* 17:   */   public void onFlush(FlushEvent event)
/* 18:   */     throws HibernateException
/* 19:   */   {
/* 20:46 */     EventSource source = event.getSession();
/* 21:47 */     PersistenceContext persistenceContext = source.getPersistenceContext();
/* 22:48 */     if ((persistenceContext.getEntityEntries().size() > 0) || (persistenceContext.getCollectionEntries().size() > 0))
/* 23:   */     {
/* 24:51 */       flushEverythingToExecutions(event);
/* 25:52 */       performExecutions(source);
/* 26:53 */       postFlush(source);
/* 27:55 */       if (source.getFactory().getStatistics().isStatisticsEnabled()) {
/* 28:56 */         source.getFactory().getStatisticsImplementor().flush();
/* 29:   */       }
/* 30:   */     }
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.DefaultFlushEventListener
 * JD-Core Version:    0.7.0.1
 */