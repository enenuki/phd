/*   1:    */ package org.hibernate.type;
/*   2:    */ 
/*   3:    */ import java.sql.CallableStatement;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import java.sql.Timestamp;
/*   8:    */ import java.util.Date;
/*   9:    */ import org.hibernate.dialect.Dialect;
/*  10:    */ import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
/*  11:    */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*  12:    */ import org.hibernate.engine.jdbc.spi.StatementPreparer;
/*  13:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  14:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  15:    */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*  16:    */ import org.hibernate.internal.CoreMessageLogger;
/*  17:    */ import org.jboss.logging.Logger;
/*  18:    */ 
/*  19:    */ public class DbTimestampType
/*  20:    */   extends TimestampType
/*  21:    */ {
/*  22: 51 */   public static final DbTimestampType INSTANCE = new DbTimestampType();
/*  23: 53 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, DbTimestampType.class.getName());
/*  24:    */   
/*  25:    */   public String getName()
/*  26:    */   {
/*  27: 57 */     return "dbtimestamp";
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String[] getRegistrationKeys()
/*  31:    */   {
/*  32: 62 */     return new String[] { getName() };
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Date seed(SessionImplementor session)
/*  36:    */   {
/*  37: 67 */     if (session == null)
/*  38:    */     {
/*  39: 68 */       LOG.trace("Incoming session was null; using current jvm time");
/*  40: 69 */       return super.seed(session);
/*  41:    */     }
/*  42: 71 */     if (!session.getFactory().getDialect().supportsCurrentTimestampSelection())
/*  43:    */     {
/*  44: 72 */       LOG.debug("Falling back to vm-based timestamp, as dialect does not support current timestamp selection");
/*  45: 73 */       return super.seed(session);
/*  46:    */     }
/*  47: 76 */     return getCurrentTimestamp(session);
/*  48:    */   }
/*  49:    */   
/*  50:    */   private Date getCurrentTimestamp(SessionImplementor session)
/*  51:    */   {
/*  52: 81 */     Dialect dialect = session.getFactory().getDialect();
/*  53: 82 */     String timestampSelectString = dialect.getCurrentTimestampSelectString();
/*  54: 83 */     if (dialect.isCurrentTimestampSelectStringCallable()) {
/*  55: 83 */       return useCallableStatement(timestampSelectString, session);
/*  56:    */     }
/*  57: 84 */     return usePreparedStatement(timestampSelectString, session);
/*  58:    */   }
/*  59:    */   
/*  60:    */   private Timestamp usePreparedStatement(String timestampSelectString, SessionImplementor session)
/*  61:    */   {
/*  62: 88 */     PreparedStatement ps = null;
/*  63:    */     try
/*  64:    */     {
/*  65: 90 */       ps = session.getTransactionCoordinator().getJdbcCoordinator().getStatementPreparer().prepareStatement(timestampSelectString, false);
/*  66:    */       
/*  67:    */ 
/*  68:    */ 
/*  69: 94 */       ResultSet rs = ps.executeQuery();
/*  70: 95 */       rs.next();
/*  71: 96 */       Timestamp ts = rs.getTimestamp(1);
/*  72: 97 */       if (LOG.isTraceEnabled()) {
/*  73: 98 */         LOG.tracev("Current timestamp retreived from db : {0} (nanos={1}, time={2})", ts, Integer.valueOf(ts.getNanos()), Long.valueOf(ts.getTime()));
/*  74:    */       }
/*  75:100 */       return ts;
/*  76:    */     }
/*  77:    */     catch (SQLException e)
/*  78:    */     {
/*  79:103 */       throw session.getFactory().getSQLExceptionHelper().convert(e, "could not select current db timestamp", timestampSelectString);
/*  80:    */     }
/*  81:    */     finally
/*  82:    */     {
/*  83:110 */       if (ps != null) {
/*  84:    */         try
/*  85:    */         {
/*  86:112 */           ps.close();
/*  87:    */         }
/*  88:    */         catch (SQLException sqle)
/*  89:    */         {
/*  90:115 */           LOG.unableToCleanUpPreparedStatement(sqle);
/*  91:    */         }
/*  92:    */       }
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   private Timestamp useCallableStatement(String callString, SessionImplementor session)
/*  97:    */   {
/*  98:122 */     CallableStatement cs = null;
/*  99:    */     try
/* 100:    */     {
/* 101:124 */       cs = (CallableStatement)session.getTransactionCoordinator().getJdbcCoordinator().getStatementPreparer().prepareStatement(callString, true);
/* 102:    */       
/* 103:    */ 
/* 104:    */ 
/* 105:128 */       cs.registerOutParameter(1, 93);
/* 106:129 */       cs.execute();
/* 107:130 */       Timestamp ts = cs.getTimestamp(1);
/* 108:131 */       if (LOG.isTraceEnabled()) {
/* 109:132 */         LOG.tracev("Current timestamp retreived from db : {0} (nanos={1}, time={2})", ts, Integer.valueOf(ts.getNanos()), Long.valueOf(ts.getTime()));
/* 110:    */       }
/* 111:134 */       return ts;
/* 112:    */     }
/* 113:    */     catch (SQLException e)
/* 114:    */     {
/* 115:137 */       throw session.getFactory().getSQLExceptionHelper().convert(e, "could not call current db timestamp function", callString);
/* 116:    */     }
/* 117:    */     finally
/* 118:    */     {
/* 119:144 */       if (cs != null) {
/* 120:    */         try
/* 121:    */         {
/* 122:146 */           cs.close();
/* 123:    */         }
/* 124:    */         catch (SQLException sqle)
/* 125:    */         {
/* 126:149 */           LOG.unableToCleanUpCallableStatement(sqle);
/* 127:    */         }
/* 128:    */       }
/* 129:    */     }
/* 130:    */   }
/* 131:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.DbTimestampType
 * JD-Core Version:    0.7.0.1
 */