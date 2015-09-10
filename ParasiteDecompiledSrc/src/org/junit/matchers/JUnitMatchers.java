/*  1:   */ package org.junit.matchers;
/*  2:   */ 
/*  3:   */ import org.hamcrest.Matcher;
/*  4:   */ import org.junit.internal.matchers.CombinableMatcher;
/*  5:   */ import org.junit.internal.matchers.Each;
/*  6:   */ import org.junit.internal.matchers.IsCollectionContaining;
/*  7:   */ import org.junit.internal.matchers.StringContains;
/*  8:   */ 
/*  9:   */ public class JUnitMatchers
/* 10:   */ {
/* 11:   */   public static <T> Matcher<Iterable<T>> hasItem(T element)
/* 12:   */   {
/* 13:19 */     return IsCollectionContaining.hasItem(element);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public static <T> Matcher<Iterable<T>> hasItem(Matcher<? extends T> elementMatcher)
/* 17:   */   {
/* 18:27 */     return IsCollectionContaining.hasItem(elementMatcher);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public static <T> Matcher<Iterable<T>> hasItems(T... elements)
/* 22:   */   {
/* 23:35 */     return IsCollectionContaining.hasItems(elements);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public static <T> Matcher<Iterable<T>> hasItems(Matcher<? extends T>... elementMatchers)
/* 27:   */   {
/* 28:45 */     return IsCollectionContaining.hasItems(elementMatchers);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public static <T> Matcher<Iterable<T>> everyItem(Matcher<T> elementMatcher)
/* 32:   */   {
/* 33:53 */     return Each.each(elementMatcher);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public static Matcher<String> containsString(String substring)
/* 37:   */   {
/* 38:61 */     return StringContains.containsString(substring);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public static <T> CombinableMatcher<T> both(Matcher<T> matcher)
/* 42:   */   {
/* 43:71 */     return new CombinableMatcher(matcher);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public static <T> CombinableMatcher<T> either(Matcher<T> matcher)
/* 47:   */   {
/* 48:81 */     return new CombinableMatcher(matcher);
/* 49:   */   }
/* 50:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.matchers.JUnitMatchers
 * JD-Core Version:    0.7.0.1
 */