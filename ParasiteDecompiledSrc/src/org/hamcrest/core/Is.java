/*  1:   */ package org.hamcrest.core;
/*  2:   */ 
/*  3:   */ import org.hamcrest.BaseMatcher;
/*  4:   */ import org.hamcrest.Description;
/*  5:   */ import org.hamcrest.Factory;
/*  6:   */ import org.hamcrest.Matcher;
/*  7:   */ 
/*  8:   */ public class Is<T>
/*  9:   */   extends BaseMatcher<T>
/* 10:   */ {
/* 11:   */   private final Matcher<T> matcher;
/* 12:   */   
/* 13:   */   public Is(Matcher<T> matcher)
/* 14:   */   {
/* 15:22 */     this.matcher = matcher;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public boolean matches(Object arg)
/* 19:   */   {
/* 20:26 */     return this.matcher.matches(arg);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void describeTo(Description description)
/* 24:   */   {
/* 25:30 */     description.appendText("is ").appendDescriptionOf(this.matcher);
/* 26:   */   }
/* 27:   */   
/* 28:   */   @Factory
/* 29:   */   public static <T> Matcher<T> is(Matcher<T> matcher)
/* 30:   */   {
/* 31:42 */     return new Is(matcher);
/* 32:   */   }
/* 33:   */   
/* 34:   */   @Factory
/* 35:   */   public static <T> Matcher<T> is(T value)
/* 36:   */   {
/* 37:53 */     return is(IsEqual.equalTo(value));
/* 38:   */   }
/* 39:   */   
/* 40:   */   @Factory
/* 41:   */   public static Matcher<Object> is(Class<?> type)
/* 42:   */   {
/* 43:64 */     return is(IsInstanceOf.instanceOf(type));
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hamcrest.core.Is
 * JD-Core Version:    0.7.0.1
 */