/*  1:   */ package org.hibernate.engine.jdbc.batch.internal;
/*  2:   */ 
/*  3:   */ import java.sql.PreparedStatement;
/*  4:   */ import java.sql.SQLException;
/*  5:   */ import java.util.LinkedHashMap;
/*  6:   */ import java.util.Map.Entry;
/*  7:   */ import org.hibernate.engine.jdbc.batch.spi.BatchKey;
/*  8:   */ import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
/*  9:   */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/* 10:   */ import org.hibernate.internal.CoreMessageLogger;
/* 11:   */ import org.hibernate.jdbc.Expectation;
/* 12:   */ import org.jboss.logging.Logger;
/* 13:   */ 
/* 14:   */ public class NonBatchingBatch
/* 15:   */   extends AbstractBatchImpl
/* 16:   */ {
/* 17:44 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, NonBatchingBatch.class.getName());
/* 18:   */   
/* 19:   */   protected NonBatchingBatch(BatchKey key, JdbcCoordinator jdbcCoordinator)
/* 20:   */   {
/* 21:47 */     super(key, jdbcCoordinator);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void addToBatch()
/* 25:   */   {
/* 26:52 */     notifyObserversImplicitExecution();
/* 27:53 */     for (Map.Entry<String, PreparedStatement> entry : getStatements().entrySet()) {
/* 28:   */       try
/* 29:   */       {
/* 30:55 */         PreparedStatement statement = (PreparedStatement)entry.getValue();
/* 31:56 */         int rowCount = statement.executeUpdate();
/* 32:57 */         getKey().getExpectation().verifyOutcome(rowCount, statement, 0);
/* 33:   */         try
/* 34:   */         {
/* 35:59 */           statement.close();
/* 36:   */         }
/* 37:   */         catch (SQLException e)
/* 38:   */         {
/* 39:62 */           LOG.debug("Unable to close non-batched batch statement", e);
/* 40:   */         }
/* 41:   */       }
/* 42:   */       catch (SQLException e)
/* 43:   */       {
/* 44:66 */         LOG.debug("SQLException escaped proxy", e);
/* 45:67 */         throw sqlExceptionHelper().convert(e, "could not execute batch statement", (String)entry.getKey());
/* 46:   */       }
/* 47:   */     }
/* 48:70 */     getStatements().clear();
/* 49:   */   }
/* 50:   */   
/* 51:   */   protected void doExecuteBatch() {}
/* 52:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.batch.internal.NonBatchingBatch
 * JD-Core Version:    0.7.0.1
 */