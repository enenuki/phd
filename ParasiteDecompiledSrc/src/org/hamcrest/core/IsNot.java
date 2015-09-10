/*  1:   */ package org.hamcrest.core;
/*  2:   */ 
/*  3:   */ import org.hamcrest.BaseMatcher;
/*  4:   */ import org.hamcrest.Description;
/*  5:   */ import org.hamcrest.Factory;
/*  6:   */ import org.hamcrest.Matcher;
/*  7:   */ 
/*  8:   */ public class IsNot<T>
/*  9:   */   extends BaseMatcher<T>
/* 10:   */ {
/* 11:   */   private final Matcher<T> matcher;
/* 12:   */   
/* 13:   */   public IsNot(Matcher<T> matcher)
/* 14:   */   {
/* 15:19 */     this.matcher = matcher;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public boolean matches(Object arg)
/* 19:   */   {
/* 20:23 */     return !this.matcher.matches(arg);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void describeTo(Description description)
/* 24:   */   {
/* 25:27 */     description.appendText("not ").appendDescriptionOf(this.matcher);
/* 26:   */   }
/* 27:   */   
/* 28:   */   @Factory
/* 29:   */   public static <T> Matcher<T> not(Matcher<T> matcher)
/* 30:   */   {
/* 31:35 */     return new IsNot(matcher);
/* 32:   */   }
/* 33:   */   
/* 34:   */   @Factory
/* 35:   */   public static <T> Matcher<T> not(T value)
/* 36:   */   {
/* 37:46 */     return not(IsEqual.equalTo(value));
/* 38:   */   }
/* 39:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hamcrest.core.IsNot
 * JD-Core Version:    0.7.0.1
 */