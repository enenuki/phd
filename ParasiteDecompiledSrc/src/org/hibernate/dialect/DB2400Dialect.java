/*  1:   */ package org.hibernate.dialect;
/*  2:   */ 
/*  3:   */ public class DB2400Dialect
/*  4:   */   extends DB2Dialect
/*  5:   */ {
/*  6:   */   public boolean supportsSequences()
/*  7:   */   {
/*  8:36 */     return false;
/*  9:   */   }
/* 10:   */   
/* 11:   */   public String getIdentitySelectString()
/* 12:   */   {
/* 13:40 */     return "select identity_val_local() from sysibm.sysdummy1";
/* 14:   */   }
/* 15:   */   
/* 16:   */   public boolean supportsLimit()
/* 17:   */   {
/* 18:44 */     return true;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean supportsLimitOffset()
/* 22:   */   {
/* 23:48 */     return false;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public boolean useMaxForLimit()
/* 27:   */   {
/* 28:52 */     return true;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public boolean supportsVariableLimit()
/* 32:   */   {
/* 33:56 */     return false;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public String getLimitString(String sql, int offset, int limit)
/* 37:   */   {
/* 38:60 */     if (offset > 0) {
/* 39:61 */       throw new UnsupportedOperationException("query result offset is not supported");
/* 40:   */     }
/* 41:63 */     if (limit == 0) {
/* 42:64 */       return sql;
/* 43:   */     }
/* 44:66 */     return sql.length() + 40 + sql + " fetch first " + limit + " rows only ";
/* 45:   */   }
/* 46:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.DB2400Dialect
 * JD-Core Version:    0.7.0.1
 */