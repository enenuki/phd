/*  1:   */ package org.hibernate.dialect;
/*  2:   */ 
/*  3:   */ import java.util.Properties;
/*  4:   */ 
/*  5:   */ public class JDataStoreDialect
/*  6:   */   extends Dialect
/*  7:   */ {
/*  8:   */   public JDataStoreDialect()
/*  9:   */   {
/* 10:42 */     registerColumnType(-7, "tinyint");
/* 11:43 */     registerColumnType(-5, "bigint");
/* 12:44 */     registerColumnType(5, "smallint");
/* 13:45 */     registerColumnType(-6, "tinyint");
/* 14:46 */     registerColumnType(4, "integer");
/* 15:47 */     registerColumnType(1, "char(1)");
/* 16:48 */     registerColumnType(12, "varchar($l)");
/* 17:49 */     registerColumnType(6, "float");
/* 18:50 */     registerColumnType(8, "double");
/* 19:51 */     registerColumnType(91, "date");
/* 20:52 */     registerColumnType(92, "time");
/* 21:53 */     registerColumnType(93, "timestamp");
/* 22:54 */     registerColumnType(-3, "varbinary($l)");
/* 23:55 */     registerColumnType(2, "numeric($p, $s)");
/* 24:   */     
/* 25:57 */     registerColumnType(2004, "varbinary");
/* 26:58 */     registerColumnType(2005, "varchar");
/* 27:   */     
/* 28:60 */     getDefaultProperties().setProperty("hibernate.jdbc.batch_size", "15");
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String getAddColumnString()
/* 32:   */   {
/* 33:64 */     return "add";
/* 34:   */   }
/* 35:   */   
/* 36:   */   public boolean dropConstraints()
/* 37:   */   {
/* 38:68 */     return false;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public String getCascadeConstraintsString()
/* 42:   */   {
/* 43:72 */     return " cascade";
/* 44:   */   }
/* 45:   */   
/* 46:   */   public boolean supportsIdentityColumns()
/* 47:   */   {
/* 48:76 */     return true;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public String getIdentitySelectString()
/* 52:   */   {
/* 53:80 */     return null;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public String getIdentityColumnString()
/* 57:   */   {
/* 58:84 */     return "autoincrement";
/* 59:   */   }
/* 60:   */   
/* 61:   */   public String getNoColumnsInsertString()
/* 62:   */   {
/* 63:88 */     return "default values";
/* 64:   */   }
/* 65:   */   
/* 66:   */   public boolean supportsColumnCheck()
/* 67:   */   {
/* 68:92 */     return false;
/* 69:   */   }
/* 70:   */   
/* 71:   */   public boolean supportsTableCheck()
/* 72:   */   {
/* 73:96 */     return false;
/* 74:   */   }
/* 75:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.JDataStoreDialect
 * JD-Core Version:    0.7.0.1
 */