/*  1:   */ package org.hibernate.dialect;
/*  2:   */ 
/*  3:   */ public class ProgressDialect
/*  4:   */   extends Dialect
/*  5:   */ {
/*  6:   */   public ProgressDialect()
/*  7:   */   {
/*  8:46 */     registerColumnType(-7, "bit");
/*  9:47 */     registerColumnType(-5, "numeric");
/* 10:48 */     registerColumnType(5, "smallint");
/* 11:49 */     registerColumnType(-6, "tinyint");
/* 12:50 */     registerColumnType(4, "integer");
/* 13:51 */     registerColumnType(1, "character(1)");
/* 14:52 */     registerColumnType(12, "varchar($l)");
/* 15:53 */     registerColumnType(6, "real");
/* 16:54 */     registerColumnType(8, "double precision");
/* 17:55 */     registerColumnType(91, "date");
/* 18:56 */     registerColumnType(92, "time");
/* 19:57 */     registerColumnType(93, "timestamp");
/* 20:58 */     registerColumnType(-3, "varbinary($l)");
/* 21:59 */     registerColumnType(2, "numeric($p,$s)");
/* 22:   */   }
/* 23:   */   
/* 24:   */   public boolean hasAlterTable()
/* 25:   */   {
/* 26:63 */     return false;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String getAddColumnString()
/* 30:   */   {
/* 31:67 */     return "add column";
/* 32:   */   }
/* 33:   */   
/* 34:   */   public boolean qualifyIndexName()
/* 35:   */   {
/* 36:71 */     return false;
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.ProgressDialect
 * JD-Core Version:    0.7.0.1
 */