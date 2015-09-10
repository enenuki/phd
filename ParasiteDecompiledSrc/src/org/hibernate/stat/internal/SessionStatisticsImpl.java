/*  1:   */ package org.hibernate.stat.internal;
/*  2:   */ 
/*  3:   */ import java.util.Collections;
/*  4:   */ import java.util.Map;
/*  5:   */ import java.util.Set;
/*  6:   */ import org.hibernate.engine.spi.PersistenceContext;
/*  7:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  8:   */ import org.hibernate.stat.SessionStatistics;
/*  9:   */ 
/* 10:   */ public class SessionStatisticsImpl
/* 11:   */   implements SessionStatistics
/* 12:   */ {
/* 13:   */   private final SessionImplementor session;
/* 14:   */   
/* 15:   */   public SessionStatisticsImpl(SessionImplementor session)
/* 16:   */   {
/* 17:40 */     this.session = session;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public int getEntityCount()
/* 21:   */   {
/* 22:44 */     return this.session.getPersistenceContext().getEntityEntries().size();
/* 23:   */   }
/* 24:   */   
/* 25:   */   public int getCollectionCount()
/* 26:   */   {
/* 27:48 */     return this.session.getPersistenceContext().getCollectionEntries().size();
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Set getEntityKeys()
/* 31:   */   {
/* 32:52 */     return Collections.unmodifiableSet(this.session.getPersistenceContext().getEntitiesByKey().keySet());
/* 33:   */   }
/* 34:   */   
/* 35:   */   public Set getCollectionKeys()
/* 36:   */   {
/* 37:56 */     return Collections.unmodifiableSet(this.session.getPersistenceContext().getCollectionsByKey().keySet());
/* 38:   */   }
/* 39:   */   
/* 40:   */   public String toString()
/* 41:   */   {
/* 42:60 */     return "SessionStatistics[" + "entity count=" + getEntityCount() + "collection count=" + getCollectionCount() + ']';
/* 43:   */   }
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.stat.internal.SessionStatisticsImpl
 * JD-Core Version:    0.7.0.1
 */