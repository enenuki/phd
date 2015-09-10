/*  1:   */ package org.hamcrest.core;
/*  2:   */ 
/*  3:   */ import org.hamcrest.BaseMatcher;
/*  4:   */ import org.hamcrest.Description;
/*  5:   */ import org.hamcrest.Factory;
/*  6:   */ import org.hamcrest.Matcher;
/*  7:   */ 
/*  8:   */ public class IsNull<T>
/*  9:   */   extends BaseMatcher<T>
/* 10:   */ {
/* 11:   */   public boolean matches(Object o)
/* 12:   */   {
/* 13:16 */     return o == null;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void describeTo(Description description)
/* 17:   */   {
/* 18:20 */     description.appendText("null");
/* 19:   */   }
/* 20:   */   
/* 21:   */   @Factory
/* 22:   */   public static <T> Matcher<T> nullValue()
/* 23:   */   {
/* 24:28 */     return new IsNull();
/* 25:   */   }
/* 26:   */   
/* 27:   */   @Factory
/* 28:   */   public static <T> Matcher<T> notNullValue()
/* 29:   */   {
/* 30:36 */     return IsNot.not(nullValue());
/* 31:   */   }
/* 32:   */   
/* 33:   */   @Factory
/* 34:   */   public static <T> Matcher<T> nullValue(Class<T> type)
/* 35:   */   {
/* 36:44 */     return nullValue();
/* 37:   */   }
/* 38:   */   
/* 39:   */   @Factory
/* 40:   */   public static <T> Matcher<T> notNullValue(Class<T> type)
/* 41:   */   {
/* 42:52 */     return notNullValue();
/* 43:   */   }
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hamcrest.core.IsNull
 * JD-Core Version:    0.7.0.1
 */