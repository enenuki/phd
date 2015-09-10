/*  1:   */ package org.hamcrest.core;
/*  2:   */ 
/*  3:   */ import java.util.Arrays;
/*  4:   */ import org.hamcrest.BaseMatcher;
/*  5:   */ import org.hamcrest.Description;
/*  6:   */ import org.hamcrest.Factory;
/*  7:   */ import org.hamcrest.Matcher;
/*  8:   */ 
/*  9:   */ public class AnyOf<T>
/* 10:   */   extends BaseMatcher<T>
/* 11:   */ {
/* 12:   */   private final Iterable<Matcher<? extends T>> matchers;
/* 13:   */   
/* 14:   */   public AnyOf(Iterable<Matcher<? extends T>> matchers)
/* 15:   */   {
/* 16:20 */     this.matchers = matchers;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public boolean matches(Object o)
/* 20:   */   {
/* 21:24 */     for (Matcher<? extends T> matcher : this.matchers) {
/* 22:25 */       if (matcher.matches(o)) {
/* 23:26 */         return true;
/* 24:   */       }
/* 25:   */     }
/* 26:29 */     return false;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void describeTo(Description description)
/* 30:   */   {
/* 31:33 */     description.appendList("(", " or ", ")", this.matchers);
/* 32:   */   }
/* 33:   */   
/* 34:   */   @Factory
/* 35:   */   public static <T> Matcher<T> anyOf(Matcher<? extends T>... matchers)
/* 36:   */   {
/* 37:41 */     return anyOf(Arrays.asList(matchers));
/* 38:   */   }
/* 39:   */   
/* 40:   */   @Factory
/* 41:   */   public static <T> Matcher<T> anyOf(Iterable<Matcher<? extends T>> matchers)
/* 42:   */   {
/* 43:49 */     return new AnyOf(matchers);
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hamcrest.core.AnyOf
 * JD-Core Version:    0.7.0.1
 */