/*  1:   */ package org.hibernate.engine.jdbc.batch.internal;
/*  2:   */ 
/*  3:   */ import org.hibernate.engine.jdbc.batch.spi.BatchKey;
/*  4:   */ import org.hibernate.jdbc.Expectation;
/*  5:   */ 
/*  6:   */ public class BasicBatchKey
/*  7:   */   implements BatchKey
/*  8:   */ {
/*  9:   */   private final String comparison;
/* 10:   */   private final int statementCount;
/* 11:   */   private final Expectation expectation;
/* 12:   */   
/* 13:   */   public BasicBatchKey(String comparison, Expectation expectation)
/* 14:   */   {
/* 15:51 */     this.comparison = comparison;
/* 16:52 */     this.statementCount = 1;
/* 17:53 */     this.expectation = expectation;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public Expectation getExpectation()
/* 21:   */   {
/* 22:58 */     return this.expectation;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public int getBatchedStatementCount()
/* 26:   */   {
/* 27:63 */     return this.statementCount;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public boolean equals(Object o)
/* 31:   */   {
/* 32:68 */     if (this == o) {
/* 33:69 */       return true;
/* 34:   */     }
/* 35:71 */     if ((o == null) || (getClass() != o.getClass())) {
/* 36:72 */       return false;
/* 37:   */     }
/* 38:75 */     BasicBatchKey that = (BasicBatchKey)o;
/* 39:77 */     if (!this.comparison.equals(that.comparison)) {
/* 40:78 */       return false;
/* 41:   */     }
/* 42:81 */     return true;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public int hashCode()
/* 46:   */   {
/* 47:86 */     return this.comparison.hashCode();
/* 48:   */   }
/* 49:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.batch.internal.BasicBatchKey
 * JD-Core Version:    0.7.0.1
 */