/*  1:   */ package org.junit.internal;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Array;
/*  4:   */ import org.junit.Assert;
/*  5:   */ 
/*  6:   */ public abstract class ComparisonCriteria
/*  7:   */ {
/*  8:   */   public void arrayEquals(String message, Object expecteds, Object actuals)
/*  9:   */     throws ArrayComparisonFailure
/* 10:   */   {
/* 11:30 */     if (expecteds == actuals) {
/* 12:31 */       return;
/* 13:   */     }
/* 14:32 */     String header = message + ": ";
/* 15:   */     
/* 16:34 */     int expectedsLength = assertArraysAreSameLength(expecteds, actuals, header);
/* 17:37 */     for (int i = 0; i < expectedsLength; i++)
/* 18:   */     {
/* 19:38 */       Object expected = Array.get(expecteds, i);
/* 20:39 */       Object actual = Array.get(actuals, i);
/* 21:41 */       if ((isArray(expected)) && (isArray(actual))) {
/* 22:   */         try
/* 23:   */         {
/* 24:43 */           arrayEquals(message, expected, actual);
/* 25:   */         }
/* 26:   */         catch (ArrayComparisonFailure e)
/* 27:   */         {
/* 28:45 */           e.addDimension(i);
/* 29:46 */           throw e;
/* 30:   */         }
/* 31:   */       } else {
/* 32:   */         try
/* 33:   */         {
/* 34:50 */           assertElementsEqual(expected, actual);
/* 35:   */         }
/* 36:   */         catch (AssertionError e)
/* 37:   */         {
/* 38:52 */           throw new ArrayComparisonFailure(header, e, i);
/* 39:   */         }
/* 40:   */       }
/* 41:   */     }
/* 42:   */   }
/* 43:   */   
/* 44:   */   private boolean isArray(Object expected)
/* 45:   */   {
/* 46:58 */     return (expected != null) && (expected.getClass().isArray());
/* 47:   */   }
/* 48:   */   
/* 49:   */   private int assertArraysAreSameLength(Object expecteds, Object actuals, String header)
/* 50:   */   {
/* 51:63 */     if (expecteds == null) {
/* 52:64 */       Assert.fail(header + "expected array was null");
/* 53:   */     }
/* 54:65 */     if (actuals == null) {
/* 55:66 */       Assert.fail(header + "actual array was null");
/* 56:   */     }
/* 57:67 */     int actualsLength = Array.getLength(actuals);
/* 58:68 */     int expectedsLength = Array.getLength(expecteds);
/* 59:69 */     if (actualsLength != expectedsLength) {
/* 60:70 */       Assert.fail(header + "array lengths differed, expected.length=" + expectedsLength + " actual.length=" + actualsLength);
/* 61:   */     }
/* 62:72 */     return expectedsLength;
/* 63:   */   }
/* 64:   */   
/* 65:   */   protected abstract void assertElementsEqual(Object paramObject1, Object paramObject2);
/* 66:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.ComparisonCriteria
 * JD-Core Version:    0.7.0.1
 */