/*  1:   */ package org.hibernate.internal.util;
/*  2:   */ 
/*  3:   */ import java.sql.SQLException;
/*  4:   */ 
/*  5:   */ public class JdbcExceptionHelper
/*  6:   */ {
/*  7:   */   public static int extractErrorCode(SQLException sqlException)
/*  8:   */   {
/*  9:16 */     int errorCode = sqlException.getErrorCode();
/* 10:17 */     SQLException nested = sqlException.getNextException();
/* 11:18 */     while ((errorCode == 0) && (nested != null))
/* 12:   */     {
/* 13:19 */       errorCode = nested.getErrorCode();
/* 14:20 */       nested = nested.getNextException();
/* 15:   */     }
/* 16:22 */     return errorCode;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static String extractSqlState(SQLException sqlException)
/* 20:   */   {
/* 21:32 */     String sqlState = sqlException.getSQLState();
/* 22:33 */     SQLException nested = sqlException.getNextException();
/* 23:34 */     while ((sqlState == null) && (nested != null))
/* 24:   */     {
/* 25:35 */       sqlState = nested.getSQLState();
/* 26:36 */       nested = nested.getNextException();
/* 27:   */     }
/* 28:38 */     return sqlState;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public static String extractSqlStateClassCode(SQLException sqlException)
/* 32:   */   {
/* 33:48 */     return determineSqlStateClassCode(extractSqlState(sqlException));
/* 34:   */   }
/* 35:   */   
/* 36:   */   public static String determineSqlStateClassCode(String sqlState)
/* 37:   */   {
/* 38:52 */     if ((sqlState == null) || (sqlState.length() < 2)) {
/* 39:53 */       return sqlState;
/* 40:   */     }
/* 41:55 */     return sqlState.substring(0, 2);
/* 42:   */   }
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.JdbcExceptionHelper
 * JD-Core Version:    0.7.0.1
 */