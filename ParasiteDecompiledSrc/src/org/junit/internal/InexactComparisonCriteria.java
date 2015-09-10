/*  1:   */ package org.junit.internal;
/*  2:   */ 
/*  3:   */ import org.junit.Assert;
/*  4:   */ 
/*  5:   */ public class InexactComparisonCriteria
/*  6:   */   extends ComparisonCriteria
/*  7:   */ {
/*  8:   */   public double fDelta;
/*  9:   */   
/* 10:   */   public InexactComparisonCriteria(double delta)
/* 11:   */   {
/* 12: 9 */     this.fDelta = delta;
/* 13:   */   }
/* 14:   */   
/* 15:   */   protected void assertElementsEqual(Object expected, Object actual)
/* 16:   */   {
/* 17:14 */     if ((expected instanceof Double)) {
/* 18:15 */       Assert.assertEquals(((Double)expected).doubleValue(), ((Double)actual).doubleValue(), this.fDelta);
/* 19:   */     } else {
/* 20:17 */       Assert.assertEquals(((Float)expected).floatValue(), ((Float)actual).floatValue(), this.fDelta);
/* 21:   */     }
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.InexactComparisonCriteria
 * JD-Core Version:    0.7.0.1
 */