/*   1:    */ package org.hibernate.engine.jdbc.spi;
/*   2:    */ 
/*   3:    */ import java.sql.Connection;
/*   4:    */ import java.sql.SQLException;
/*   5:    */ import java.sql.SQLWarning;
/*   6:    */ import java.sql.Statement;
/*   7:    */ import org.hibernate.JDBCException;
/*   8:    */ import org.hibernate.exception.internal.SQLStateConverter;
/*   9:    */ import org.hibernate.exception.spi.SQLExceptionConverter;
/*  10:    */ import org.hibernate.exception.spi.ViolatedConstraintNameExtracter;
/*  11:    */ import org.hibernate.internal.CoreMessageLogger;
/*  12:    */ import org.hibernate.internal.util.StringHelper;
/*  13:    */ import org.jboss.logging.Logger;
/*  14:    */ import org.jboss.logging.Logger.Level;
/*  15:    */ 
/*  16:    */ public class SqlExceptionHelper
/*  17:    */ {
/*  18: 48 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, SqlExceptionHelper.class.getName());
/*  19:    */   public static final String DEFAULT_EXCEPTION_MSG = "SQL Exception";
/*  20:    */   public static final String DEFAULT_WARNING_MSG = "SQL Warning";
/*  21: 53 */   public static final SQLExceptionConverter DEFAULT_CONVERTER = new SQLStateConverter(new ViolatedConstraintNameExtracter()
/*  22:    */   {
/*  23:    */     public String extractConstraintName(SQLException e)
/*  24:    */     {
/*  25: 56 */       return null;
/*  26:    */     }
/*  27: 53 */   });
/*  28:    */   private SQLExceptionConverter sqlExceptionConverter;
/*  29:    */   
/*  30:    */   public SqlExceptionHelper()
/*  31:    */   {
/*  32: 67 */     this.sqlExceptionConverter = DEFAULT_CONVERTER;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public SqlExceptionHelper(SQLExceptionConverter sqlExceptionConverter)
/*  36:    */   {
/*  37: 76 */     this.sqlExceptionConverter = sqlExceptionConverter;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public SQLExceptionConverter getSqlExceptionConverter()
/*  41:    */   {
/*  42: 85 */     return this.sqlExceptionConverter;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setSqlExceptionConverter(SQLExceptionConverter sqlExceptionConverter)
/*  46:    */   {
/*  47: 96 */     this.sqlExceptionConverter = (sqlExceptionConverter == null ? DEFAULT_CONVERTER : sqlExceptionConverter);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public JDBCException convert(SQLException sqlException, String message)
/*  51:    */   {
/*  52:110 */     return convert(sqlException, message, "n/a");
/*  53:    */   }
/*  54:    */   
/*  55:    */   public JDBCException convert(SQLException sqlException, String message, String sql)
/*  56:    */   {
/*  57:124 */     logExceptions(sqlException, message + " [" + sql + "]");
/*  58:125 */     return this.sqlExceptionConverter.convert(sqlException, message, sql);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void logExceptions(SQLException sqlException, String message)
/*  62:    */   {
/*  63:136 */     if (LOG.isEnabled(Logger.Level.ERROR))
/*  64:    */     {
/*  65:137 */       if (LOG.isDebugEnabled())
/*  66:    */       {
/*  67:138 */         message = StringHelper.isNotEmpty(message) ? message : "SQL Exception";
/*  68:139 */         LOG.debug(message, sqlException);
/*  69:    */       }
/*  70:141 */       while (sqlException != null)
/*  71:    */       {
/*  72:142 */         StringBuilder buf = new StringBuilder(30).append("SQL Error: ").append(sqlException.getErrorCode()).append(", SQLState: ").append(sqlException.getSQLState());
/*  73:143 */         LOG.warn(buf.toString());
/*  74:144 */         LOG.error(sqlException.getMessage());
/*  75:145 */         sqlException = sqlException.getNextException();
/*  76:    */       }
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static abstract interface WarningHandler
/*  81:    */   {
/*  82:    */     public abstract boolean doProcess();
/*  83:    */     
/*  84:    */     public abstract void prepare(SQLWarning paramSQLWarning);
/*  85:    */     
/*  86:    */     public abstract void handleWarning(SQLWarning paramSQLWarning);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static abstract class WarningHandlerLoggingSupport
/*  90:    */     implements SqlExceptionHelper.WarningHandler
/*  91:    */   {
/*  92:    */     public final void handleWarning(SQLWarning warning)
/*  93:    */     {
/*  94:185 */       StringBuffer buf = new StringBuffer(30).append("SQL Warning Code: ").append(warning.getErrorCode()).append(", SQLState: ").append(warning.getSQLState());
/*  95:186 */       logWarning(buf.toString(), warning.getMessage());
/*  96:    */     }
/*  97:    */     
/*  98:    */     protected abstract void logWarning(String paramString1, String paramString2);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static class StandardWarningHandler
/* 102:    */     extends SqlExceptionHelper.WarningHandlerLoggingSupport
/* 103:    */   {
/* 104:    */     private final String introMessage;
/* 105:    */     
/* 106:    */     public StandardWarningHandler(String introMessage)
/* 107:    */     {
/* 108:203 */       this.introMessage = introMessage;
/* 109:    */     }
/* 110:    */     
/* 111:    */     public boolean doProcess()
/* 112:    */     {
/* 113:207 */       return SqlExceptionHelper.LOG.isEnabled(Logger.Level.WARN);
/* 114:    */     }
/* 115:    */     
/* 116:    */     public void prepare(SQLWarning warning)
/* 117:    */     {
/* 118:211 */       SqlExceptionHelper.LOG.debug(this.introMessage, warning);
/* 119:    */     }
/* 120:    */     
/* 121:    */     protected void logWarning(String description, String message)
/* 122:    */     {
/* 123:217 */       SqlExceptionHelper.LOG.warn(description);
/* 124:218 */       SqlExceptionHelper.LOG.warn(message);
/* 125:    */     }
/* 126:    */   }
/* 127:    */   
/* 128:222 */   public static StandardWarningHandler STANDARD_WARNING_HANDLER = new StandardWarningHandler("SQL Warning");
/* 129:    */   
/* 130:    */   public void walkWarnings(SQLWarning warning, WarningHandler handler)
/* 131:    */   {
/* 132:226 */     if ((warning == null) || (handler.doProcess())) {
/* 133:227 */       return;
/* 134:    */     }
/* 135:229 */     handler.prepare(warning);
/* 136:230 */     while (warning != null)
/* 137:    */     {
/* 138:231 */       handler.handleWarning(warning);
/* 139:232 */       warning = warning.getNextWarning();
/* 140:    */     }
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void logAndClearWarnings(Connection connection)
/* 144:    */   {
/* 145:244 */     handleAndClearWarnings(connection, STANDARD_WARNING_HANDLER);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void handleAndClearWarnings(Connection connection, WarningHandler handler)
/* 149:    */   {
/* 150:    */     try
/* 151:    */     {
/* 152:258 */       walkWarnings(connection.getWarnings(), handler);
/* 153:    */     }
/* 154:    */     catch (SQLException sqle)
/* 155:    */     {
/* 156:261 */       LOG.debug("could not log warnings", sqle);
/* 157:    */     }
/* 158:    */     try
/* 159:    */     {
/* 160:265 */       connection.clearWarnings();
/* 161:    */     }
/* 162:    */     catch (SQLException sqle)
/* 163:    */     {
/* 164:267 */       LOG.debug("could not clear warnings", sqle);
/* 165:    */     }
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void handleAndClearWarnings(Statement statement, WarningHandler handler)
/* 169:    */   {
/* 170:    */     try
/* 171:    */     {
/* 172:282 */       walkWarnings(statement.getWarnings(), handler);
/* 173:    */     }
/* 174:    */     catch (SQLException sqlException)
/* 175:    */     {
/* 176:285 */       LOG.debug("could not log warnings", sqlException);
/* 177:    */     }
/* 178:    */     try
/* 179:    */     {
/* 180:289 */       statement.clearWarnings();
/* 181:    */     }
/* 182:    */     catch (SQLException sqle)
/* 183:    */     {
/* 184:291 */       LOG.debug("could not clear warnings", sqle);
/* 185:    */     }
/* 186:    */   }
/* 187:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.spi.SqlExceptionHelper
 * JD-Core Version:    0.7.0.1
 */