/* 1:  */ package org.junit.internal;
/* 2:  */ 
/* 3:  */ import org.junit.Assert;
/* 4:  */ 
/* 5:  */ public class ExactComparisonCriteria
/* 6:  */   extends ComparisonCriteria
/* 7:  */ {
/* 8:  */   protected void assertElementsEqual(Object expected, Object actual)
/* 9:  */   {
/* ::8 */     Assert.assertEquals(expected, actual);
/* ;:  */   }
/* <:  */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.ExactComparisonCriteria
 * JD-Core Version:    0.7.0.1
 */