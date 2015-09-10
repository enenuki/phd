/*  1:   */ package org.hibernate.internal;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.sql.Connection;
/*  5:   */ import org.hibernate.engine.jdbc.spi.ConnectionObserver;
/*  6:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  7:   */ import org.hibernate.stat.spi.StatisticsImplementor;
/*  8:   */ 
/*  9:   */ public class ConnectionObserverStatsBridge
/* 10:   */   implements ConnectionObserver, Serializable
/* 11:   */ {
/* 12:   */   private final SessionFactoryImplementor sessionFactory;
/* 13:   */   
/* 14:   */   public ConnectionObserverStatsBridge(SessionFactoryImplementor sessionFactory)
/* 15:   */   {
/* 16:39 */     this.sessionFactory = sessionFactory;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void physicalConnectionObtained(Connection connection)
/* 20:   */   {
/* 21:44 */     this.sessionFactory.getStatisticsImplementor().connect();
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void physicalConnectionReleased() {}
/* 25:   */   
/* 26:   */   public void logicalConnectionClosed() {}
/* 27:   */   
/* 28:   */   public void statementPrepared()
/* 29:   */   {
/* 30:57 */     this.sessionFactory.getStatisticsImplementor().prepareStatement();
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.ConnectionObserverStatsBridge
 * JD-Core Version:    0.7.0.1
 */