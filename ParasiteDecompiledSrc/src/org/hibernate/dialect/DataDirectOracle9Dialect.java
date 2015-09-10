/*  1:   */ package org.hibernate.dialect;
/*  2:   */ 
/*  3:   */ import java.sql.CallableStatement;
/*  4:   */ import java.sql.ResultSet;
/*  5:   */ import java.sql.SQLException;
/*  6:   */ 
/*  7:   */ public class DataDirectOracle9Dialect
/*  8:   */   extends Oracle9Dialect
/*  9:   */ {
/* 10:   */   public int registerResultSetOutParameter(CallableStatement statement, int col)
/* 11:   */     throws SQLException
/* 12:   */   {
/* 13:32 */     return col;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public ResultSet getResultSet(CallableStatement ps)
/* 17:   */     throws SQLException
/* 18:   */   {
/* 19:36 */     boolean isResultSet = ps.execute();
/* 20:38 */     while ((!isResultSet) && (ps.getUpdateCount() != -1)) {
/* 21:39 */       isResultSet = ps.getMoreResults();
/* 22:   */     }
/* 23:41 */     ResultSet rs = ps.getResultSet();
/* 24:   */     
/* 25:   */ 
/* 26:44 */     return rs;
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.DataDirectOracle9Dialect
 * JD-Core Version:    0.7.0.1
 */