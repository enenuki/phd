/*  1:   */ package org.hibernate.jdbc;
/*  2:   */ 
/*  3:   */ import org.hibernate.HibernateException;
/*  4:   */ 
/*  5:   */ public class TooManyRowsAffectedException
/*  6:   */   extends HibernateException
/*  7:   */ {
/*  8:   */   private final int expectedRowCount;
/*  9:   */   private final int actualRowCount;
/* 10:   */   
/* 11:   */   public TooManyRowsAffectedException(String message, int expectedRowCount, int actualRowCount)
/* 12:   */   {
/* 13:40 */     super(message);
/* 14:41 */     this.expectedRowCount = expectedRowCount;
/* 15:42 */     this.actualRowCount = actualRowCount;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public int getExpectedRowCount()
/* 19:   */   {
/* 20:46 */     return this.expectedRowCount;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int getActualRowCount()
/* 24:   */   {
/* 25:50 */     return this.actualRowCount;
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.jdbc.TooManyRowsAffectedException
 * JD-Core Version:    0.7.0.1
 */