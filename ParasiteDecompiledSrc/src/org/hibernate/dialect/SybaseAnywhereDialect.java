/*  1:   */ package org.hibernate.dialect;
/*  2:   */ 
/*  3:   */ public class SybaseAnywhereDialect
/*  4:   */   extends AbstractTransactSQLDialect
/*  5:   */ {
/*  6:   */   public String getNoColumnsInsertString()
/*  7:   */   {
/*  8:39 */     return "values (default)";
/*  9:   */   }
/* 10:   */   
/* 11:   */   public boolean dropConstraints()
/* 12:   */   {
/* 13:49 */     return false;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public boolean supportsInsertSelectIdentity()
/* 17:   */   {
/* 18:53 */     return false;
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.SybaseAnywhereDialect
 * JD-Core Version:    0.7.0.1
 */