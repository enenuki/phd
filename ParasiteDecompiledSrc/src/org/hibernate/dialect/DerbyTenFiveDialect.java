/*  1:   */ package org.hibernate.dialect;
/*  2:   */ 
/*  3:   */ import org.hibernate.dialect.function.AnsiTrimFunction;
/*  4:   */ import org.hibernate.dialect.function.DerbyConcatFunction;
/*  5:   */ 
/*  6:   */ public class DerbyTenFiveDialect
/*  7:   */   extends DerbyDialect
/*  8:   */ {
/*  9:   */   public DerbyTenFiveDialect()
/* 10:   */   {
/* 11:42 */     registerFunction("concat", new DerbyConcatFunction());
/* 12:43 */     registerFunction("trim", new AnsiTrimFunction());
/* 13:   */   }
/* 14:   */   
/* 15:   */   public boolean supportsSequences()
/* 16:   */   {
/* 17:48 */     return false;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean supportsLimit()
/* 21:   */   {
/* 22:53 */     return true;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public boolean supportsLimitOffset()
/* 26:   */   {
/* 27:58 */     return true;
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.DerbyTenFiveDialect
 * JD-Core Version:    0.7.0.1
 */