/*  1:   */ package org.hibernate.dialect;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.LockMode;
/*  5:   */ import org.hibernate.LockOptions;
/*  6:   */ import org.hibernate.dialect.function.SQLFunctionTemplate;
/*  7:   */ import org.hibernate.sql.ForUpdateFragment;
/*  8:   */ import org.hibernate.type.StandardBasicTypes;
/*  9:   */ 
/* 10:   */ public class SybaseASE157Dialect
/* 11:   */   extends SybaseASE15Dialect
/* 12:   */ {
/* 13:   */   public SybaseASE157Dialect()
/* 14:   */   {
/* 15:45 */     registerFunction("create_locator", new SQLFunctionTemplate(StandardBasicTypes.BINARY, "create_locator(?1, ?2)"));
/* 16:46 */     registerFunction("locator_literal", new SQLFunctionTemplate(StandardBasicTypes.BINARY, "locator_literal(?1, ?2)"));
/* 17:47 */     registerFunction("locator_valid", new SQLFunctionTemplate(StandardBasicTypes.BOOLEAN, "locator_valid(?1)"));
/* 18:48 */     registerFunction("return_lob", new SQLFunctionTemplate(StandardBasicTypes.BINARY, "return_lob(?1, ?2)"));
/* 19:49 */     registerFunction("setdata", new SQLFunctionTemplate(StandardBasicTypes.BOOLEAN, "setdata(?1, ?2, ?3)"));
/* 20:50 */     registerFunction("charindex", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "charindex(?1, ?2, ?3)"));
/* 21:   */   }
/* 22:   */   
/* 23:   */   public boolean supportsExpectedLobUsagePattern()
/* 24:   */   {
/* 25:55 */     return true;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public boolean supportsLobValueChangePropogation()
/* 29:   */   {
/* 30:59 */     return false;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public boolean forUpdateOfColumns()
/* 34:   */   {
/* 35:64 */     return true;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public String getForUpdateString()
/* 39:   */   {
/* 40:68 */     return " for update";
/* 41:   */   }
/* 42:   */   
/* 43:   */   public String getForUpdateString(String aliases)
/* 44:   */   {
/* 45:72 */     return getForUpdateString() + " of " + aliases;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public String appendLockHint(LockMode mode, String tableName)
/* 49:   */   {
/* 50:76 */     return tableName;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public String applyLocksToSql(String sql, LockOptions aliasedLockOptions, Map keyColumnNames)
/* 54:   */   {
/* 55:80 */     return sql + new ForUpdateFragment(this, aliasedLockOptions, keyColumnNames).toFragmentString();
/* 56:   */   }
/* 57:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.SybaseASE157Dialect
 * JD-Core Version:    0.7.0.1
 */