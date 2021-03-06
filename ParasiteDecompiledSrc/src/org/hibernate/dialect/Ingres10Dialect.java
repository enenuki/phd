/*  1:   */ package org.hibernate.dialect;
/*  2:   */ 
/*  3:   */ import java.util.Properties;
/*  4:   */ 
/*  5:   */ public class Ingres10Dialect
/*  6:   */   extends Ingres9Dialect
/*  7:   */ {
/*  8:   */   public Ingres10Dialect()
/*  9:   */   {
/* 10:44 */     registerBooleanSupport();
/* 11:   */   }
/* 12:   */   
/* 13:   */   public String toBooleanValueString(boolean bool)
/* 14:   */   {
/* 15:56 */     return bool ? "true" : "false";
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected void registerBooleanSupport()
/* 19:   */   {
/* 20:65 */     registerColumnType(-7, "boolean");
/* 21:66 */     registerColumnType(16, "boolean");
/* 22:   */     
/* 23:   */ 
/* 24:   */ 
/* 25:   */ 
/* 26:   */ 
/* 27:72 */     Properties properties = getDefaultProperties();
/* 28:73 */     String querySubst = properties.getProperty("hibernate.query.substitutions");
/* 29:74 */     if (querySubst != null)
/* 30:   */     {
/* 31:75 */       String newQuerySubst = querySubst.replace("true=1,false=0", "");
/* 32:76 */       properties.setProperty("hibernate.query.substitutions", newQuerySubst);
/* 33:   */     }
/* 34:   */   }
/* 35:   */   
/* 36:   */   public boolean supportsIdentityColumns()
/* 37:   */   {
/* 38:83 */     return true;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public boolean hasDataTypeInIdentityColumn()
/* 42:   */   {
/* 43:87 */     return true;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public String getIdentitySelectString()
/* 47:   */   {
/* 48:91 */     return "select last_identity()";
/* 49:   */   }
/* 50:   */   
/* 51:   */   public String getIdentityColumnString()
/* 52:   */   {
/* 53:95 */     return "not null generated by default as identity";
/* 54:   */   }
/* 55:   */   
/* 56:   */   public String getIdentityInsertString()
/* 57:   */   {
/* 58:99 */     return "default";
/* 59:   */   }
/* 60:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.Ingres10Dialect
 * JD-Core Version:    0.7.0.1
 */