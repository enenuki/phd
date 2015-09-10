/*  1:   */ package org.junit.internal.matchers;
/*  2:   */ 
/*  3:   */ import org.hamcrest.BaseMatcher;
/*  4:   */ import org.hamcrest.CoreMatchers;
/*  5:   */ import org.hamcrest.Description;
/*  6:   */ import org.hamcrest.Matcher;
/*  7:   */ 
/*  8:   */ public class CombinableMatcher<T>
/*  9:   */   extends BaseMatcher<T>
/* 10:   */ {
/* 11:   */   private final Matcher<? extends T> fMatcher;
/* 12:   */   
/* 13:   */   public CombinableMatcher(Matcher<? extends T> matcher)
/* 14:   */   {
/* 15:14 */     this.fMatcher = matcher;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public boolean matches(Object item)
/* 19:   */   {
/* 20:18 */     return this.fMatcher.matches(item);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void describeTo(Description description)
/* 24:   */   {
/* 25:22 */     description.appendDescriptionOf(this.fMatcher);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public CombinableMatcher<T> and(Matcher<? extends T> matcher)
/* 29:   */   {
/* 30:27 */     return new CombinableMatcher(CoreMatchers.allOf(new Matcher[] { matcher, this.fMatcher }));
/* 31:   */   }
/* 32:   */   
/* 33:   */   public CombinableMatcher<T> or(Matcher<? extends T> matcher)
/* 34:   */   {
/* 35:32 */     return new CombinableMatcher(CoreMatchers.anyOf(new Matcher[] { matcher, this.fMatcher }));
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.matchers.CombinableMatcher
 * JD-Core Version:    0.7.0.1
 */