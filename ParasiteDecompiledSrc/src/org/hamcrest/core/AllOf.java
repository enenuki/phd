/*  1:   */ package org.hamcrest.core;
/*  2:   */ 
/*  3:   */ import java.util.Arrays;
/*  4:   */ import org.hamcrest.BaseMatcher;
/*  5:   */ import org.hamcrest.Description;
/*  6:   */ import org.hamcrest.Factory;
/*  7:   */ import org.hamcrest.Matcher;
/*  8:   */ 
/*  9:   */ public class AllOf<T>
/* 10:   */   extends BaseMatcher<T>
/* 11:   */ {
/* 12:   */   private final Iterable<Matcher<? extends T>> matchers;
/* 13:   */   
/* 14:   */   public AllOf(Iterable<Matcher<? extends T>> matchers)
/* 15:   */   {
/* 16:19 */     this.matchers = matchers;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public boolean matches(Object o)
/* 20:   */   {
/* 21:23 */     for (Matcher<? extends T> matcher : this.matchers) {
/* 22:24 */       if (!matcher.matches(o)) {
/* 23:25 */         return false;
/* 24:   */       }
/* 25:   */     }
/* 26:28 */     return true;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void describeTo(Description description)
/* 30:   */   {
/* 31:32 */     description.appendList("(", " and ", ")", this.matchers);
/* 32:   */   }
/* 33:   */   
/* 34:   */   @Factory
/* 35:   */   public static <T> Matcher<T> allOf(Matcher<? extends T>... matchers)
/* 36:   */   {
/* 37:40 */     return allOf(Arrays.asList(matchers));
/* 38:   */   }
/* 39:   */   
/* 40:   */   @Factory
/* 41:   */   public static <T> Matcher<T> allOf(Iterable<Matcher<? extends T>> matchers)
/* 42:   */   {
/* 43:48 */     return new AllOf(matchers);
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hamcrest.core.AllOf
 * JD-Core Version:    0.7.0.1
 */