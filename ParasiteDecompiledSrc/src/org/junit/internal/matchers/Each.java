/*  1:   */ package org.junit.internal.matchers;
/*  2:   */ 
/*  3:   */ import org.hamcrest.BaseMatcher;
/*  4:   */ import org.hamcrest.CoreMatchers;
/*  5:   */ import org.hamcrest.Description;
/*  6:   */ import org.hamcrest.Matcher;
/*  7:   */ 
/*  8:   */ public class Each
/*  9:   */ {
/* 10:   */   public static <T> Matcher<Iterable<T>> each(final Matcher<T> individual)
/* 11:   */   {
/* 12:11 */     Matcher<Iterable<T>> allItemsAre = CoreMatchers.not(IsCollectionContaining.hasItem(CoreMatchers.not(individual)));
/* 13:   */     
/* 14:13 */     new BaseMatcher()
/* 15:   */     {
/* 16:   */       public boolean matches(Object item)
/* 17:   */       {
/* 18:15 */         return this.val$allItemsAre.matches(item);
/* 19:   */       }
/* 20:   */       
/* 21:   */       public void describeTo(Description description)
/* 22:   */       {
/* 23:19 */         description.appendText("each ");
/* 24:20 */         individual.describeTo(description);
/* 25:   */       }
/* 26:   */     };
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.matchers.Each
 * JD-Core Version:    0.7.0.1
 */