/*  1:   */ package org.junit.internal.matchers;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Collection;
/*  5:   */ import org.hamcrest.Description;
/*  6:   */ import org.hamcrest.Factory;
/*  7:   */ import org.hamcrest.Matcher;
/*  8:   */ import org.hamcrest.core.AllOf;
/*  9:   */ import org.hamcrest.core.IsEqual;
/* 10:   */ 
/* 11:   */ public class IsCollectionContaining<T>
/* 12:   */   extends TypeSafeMatcher<Iterable<T>>
/* 13:   */ {
/* 14:   */   private final Matcher<? extends T> elementMatcher;
/* 15:   */   
/* 16:   */   public IsCollectionContaining(Matcher<? extends T> elementMatcher)
/* 17:   */   {
/* 18:18 */     this.elementMatcher = elementMatcher;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean matchesSafely(Iterable<T> collection)
/* 22:   */   {
/* 23:23 */     for (T item : collection) {
/* 24:24 */       if (this.elementMatcher.matches(item)) {
/* 25:25 */         return true;
/* 26:   */       }
/* 27:   */     }
/* 28:28 */     return false;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void describeTo(Description description)
/* 32:   */   {
/* 33:32 */     description.appendText("a collection containing ").appendDescriptionOf(this.elementMatcher);
/* 34:   */   }
/* 35:   */   
/* 36:   */   @Factory
/* 37:   */   public static <T> Matcher<Iterable<T>> hasItem(Matcher<? extends T> elementMatcher)
/* 38:   */   {
/* 39:39 */     return new IsCollectionContaining(elementMatcher);
/* 40:   */   }
/* 41:   */   
/* 42:   */   @Factory
/* 43:   */   public static <T> Matcher<Iterable<T>> hasItem(T element)
/* 44:   */   {
/* 45:44 */     return hasItem(IsEqual.equalTo(element));
/* 46:   */   }
/* 47:   */   
/* 48:   */   @Factory
/* 49:   */   public static <T> Matcher<Iterable<T>> hasItems(Matcher<? extends T>... elementMatchers)
/* 50:   */   {
/* 51:49 */     Collection<Matcher<? extends Iterable<T>>> all = new ArrayList(elementMatchers.length);
/* 52:51 */     for (Matcher<? extends T> elementMatcher : elementMatchers) {
/* 53:52 */       all.add(hasItem(elementMatcher));
/* 54:   */     }
/* 55:54 */     return AllOf.allOf(all);
/* 56:   */   }
/* 57:   */   
/* 58:   */   @Factory
/* 59:   */   public static <T> Matcher<Iterable<T>> hasItems(T... elements)
/* 60:   */   {
/* 61:59 */     Collection<Matcher<? extends Iterable<T>>> all = new ArrayList(elements.length);
/* 62:61 */     for (T element : elements) {
/* 63:62 */       all.add(hasItem(element));
/* 64:   */     }
/* 65:64 */     return AllOf.allOf(all);
/* 66:   */   }
/* 67:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.matchers.IsCollectionContaining
 * JD-Core Version:    0.7.0.1
 */