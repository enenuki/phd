/*  1:   */ package org.junit;
/*  2:   */ 
/*  3:   */ import java.util.Arrays;
/*  4:   */ import org.hamcrest.CoreMatchers;
/*  5:   */ import org.hamcrest.Matcher;
/*  6:   */ import org.junit.internal.AssumptionViolatedException;
/*  7:   */ import org.junit.internal.matchers.Each;
/*  8:   */ 
/*  9:   */ public class Assume
/* 10:   */ {
/* 11:   */   public static void assumeTrue(boolean b)
/* 12:   */   {
/* 13:39 */     assumeThat(Boolean.valueOf(b), CoreMatchers.is(Boolean.valueOf(true)));
/* 14:   */   }
/* 15:   */   
/* 16:   */   public static void assumeNotNull(Object... objects)
/* 17:   */   {
/* 18:47 */     assumeThat(Arrays.asList(objects), Each.each(CoreMatchers.notNullValue()));
/* 19:   */   }
/* 20:   */   
/* 21:   */   public static <T> void assumeThat(T actual, Matcher<T> matcher)
/* 22:   */   {
/* 23:69 */     if (!matcher.matches(actual)) {
/* 24:70 */       throw new AssumptionViolatedException(actual, matcher);
/* 25:   */     }
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static void assumeNoException(Throwable t)
/* 29:   */   {
/* 30:92 */     assumeThat(t, CoreMatchers.nullValue());
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.Assume
 * JD-Core Version:    0.7.0.1
 */