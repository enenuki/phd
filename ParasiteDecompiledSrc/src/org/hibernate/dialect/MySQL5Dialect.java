/*  1:   */ package org.hibernate.dialect;
/*  2:   */ 
/*  3:   */ public class MySQL5Dialect
/*  4:   */   extends MySQLDialect
/*  5:   */ {
/*  6:   */   protected void registerVarcharTypes()
/*  7:   */   {
/*  8:34 */     registerColumnType(12, "longtext");
/*  9:   */     
/* 10:36 */     registerColumnType(12, 65535L, "varchar($l)");
/* 11:37 */     registerColumnType(-1, "longtext");
/* 12:   */   }
/* 13:   */   
/* 14:   */   public boolean supportsColumnCheck()
/* 15:   */   {
/* 16:47 */     return false;
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.MySQL5Dialect
 * JD-Core Version:    0.7.0.1
 */