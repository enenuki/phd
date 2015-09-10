/*  1:   */ package org.hibernate.sql;
/*  2:   */ 
/*  3:   */ import java.util.LinkedHashMap;
/*  4:   */ import java.util.Map;
/*  5:   */ import org.hibernate.internal.util.StringHelper;
/*  6:   */ 
/*  7:   */ public abstract class CaseFragment
/*  8:   */ {
/*  9:   */   protected String returnColumnName;
/* 10:42 */   protected Map cases = new LinkedHashMap();
/* 11:   */   
/* 12:   */   public abstract String toFragmentString();
/* 13:   */   
/* 14:   */   public CaseFragment setReturnColumnName(String returnColumnName)
/* 15:   */   {
/* 16:45 */     this.returnColumnName = returnColumnName;
/* 17:46 */     return this;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public CaseFragment setReturnColumnName(String returnColumnName, String suffix)
/* 21:   */   {
/* 22:50 */     return setReturnColumnName(new Alias(suffix).toAliasString(returnColumnName));
/* 23:   */   }
/* 24:   */   
/* 25:   */   public CaseFragment addWhenColumnNotNull(String alias, String columnName, String value)
/* 26:   */   {
/* 27:54 */     this.cases.put(StringHelper.qualify(alias, columnName), value);
/* 28:55 */     return this;
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.CaseFragment
 * JD-Core Version:    0.7.0.1
 */