/*  1:   */ package org.hibernate.jdbc;
/*  2:   */ 
/*  3:   */ public class BatchedTooManyRowsAffectedException
/*  4:   */   extends TooManyRowsAffectedException
/*  5:   */ {
/*  6:   */   private final int batchPosition;
/*  7:   */   
/*  8:   */   public BatchedTooManyRowsAffectedException(String message, int expectedRowCount, int actualRowCount, int batchPosition)
/*  9:   */   {
/* 10:39 */     super(message, expectedRowCount, actualRowCount);
/* 11:40 */     this.batchPosition = batchPosition;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public int getBatchPosition()
/* 15:   */   {
/* 16:44 */     return this.batchPosition;
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.jdbc.BatchedTooManyRowsAffectedException
 * JD-Core Version:    0.7.0.1
 */